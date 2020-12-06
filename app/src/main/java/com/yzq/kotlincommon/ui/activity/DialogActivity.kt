package com.yzq.kotlincommon.ui.activity

import android.annotation.SuppressLint
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.ActivityDialogBinding
import com.yzq.lib_base.ui.BaseViewBindingActivity
import com.yzq.lib_materialdialog.*
import com.yzq.lib_rx.NextObserver
import com.yzq.lib_rx.transform
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit


/**
 * @description: 弹窗
 * @author : yzq
 * @date   : 2019/4/30
 * @time   : 13:38
 *
 */

@Route(path = RoutePath.Main.DIALOG)
class DialogActivity : BaseViewBindingActivity<ActivityDialogBinding>() {

    override fun getViewBinding() = ActivityDialogBinding.inflate(layoutInflater)


    @SuppressLint("AutoDispose")
    override fun initWidget() {
        super.initWidget()


        initToolbar(binding.layoutToolbar.toolbar, "弹窗", true)



        binding.layoutScrollContent.btnBase.setOnClickListener {
            showBaseDialog(message = "基础弹窗，没有任何回调，只有确定按钮且没有回调，一般用于信息提示")
        }



        binding.layoutScrollContent.btnOnlyPositiveCallback.setOnClickListener {

            showOnlyPostiveCallBackDialog(message = "只有确定选项和回调的弹窗，一般用于强制性的操作") {
                ToastUtils.showShort("点击了确定")
            }
        }
        binding.layoutScrollContent.btnPositiveCallback.setOnClickListener {
            showPositiveCallbackDialog(message = "双选项，但只有确定按钮回调的弹窗，一般用于选择性的操作") {
                ToastUtils.showShort("点击了确定")
            }

        }


        binding.layoutScrollContent.btnCallback.setOnClickListener {

            showCallbackDialog(message = "双选项双回调",
                positiveCallback = {
                    ToastUtils.showShort("点击了确定")
                },
                negativeCallback = {
                    ToastUtils.showShort("点击了取消")
                }
            )
        }



        binding.layoutScrollContent.btnSingleSelect
            .setOnClickListener {

                val datas = arrayListOf("java", "kotlin", "android", "python", "flutter")

                showSingleSelectList(title = "语言", items = datas) { dialog, index, text ->

                    ToastUtils.showShort(text.toString())
                }
            }



        binding.layoutScrollContent.btnInput
            .setOnClickListener {

                showInputDialog(positiveText = "完成") { materialDialog, charSequence ->
                    ToastUtils.showShort(charSequence.toString())
                }

            }


        binding.layoutScrollContent.btnLoading
            .setOnClickListener {
                showLoadingDialog("登录中...")

                Observable.timer(3, TimeUnit.SECONDS)
                    .subscribe {
                        dismissLoadingDialog()
                    }
            }


        binding.layoutScrollContent.btnProgress
            .setOnClickListener {
                var count = 0

                showProgressDialog("模拟进度")


                Observable.interval(200, TimeUnit.MILLISECONDS)
                    .transform(this)
                    .subscribe(object : NextObserver<Long>() {
                        lateinit var d: Disposable
                        override fun onSubscribe(d: Disposable) {
                            this.d = d
                        }

                        override fun onNext(t: Long) {
                            LogUtils.i(count)
                            count += 5
                            if (count <= 100) {
                                changeProgress(count)
                            } else {
                                d.dispose()
                                dismissProgressDialog()
                            }
                        }
                    })


            }

        binding.layoutScrollContent.btnSelectYear
            .setOnClickListener {

                selectYear {
                    ToastUtils.showShort(it)
                }

            }
        binding.layoutScrollContent.btnSelectDate
            .setOnClickListener {


                selectDate { ToastUtils.showShort(it) }

            }
        binding.layoutScrollContent.btnSelectTime
            .setOnClickListener {
                selectHourAndMinute {
                    ToastUtils.showShort(it)
                }
            }



        binding.layoutScrollContent.btnBottomDialog
            .setOnClickListener {
                MaterialDialog(this, BottomSheet(LayoutMode.WRAP_CONTENT))
                    .show {
                        title(R.string.hint)
                        message(text = "bottom sheet")
                        positiveButton(text = "确定")
                        negativeButton(text = "取消")

                    }
            }


    }


}
