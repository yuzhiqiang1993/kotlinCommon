package com.yzq.kotlincommon.ui

import android.annotation.SuppressLint
import androidx.appcompat.widget.Toolbar
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ToastUtils
import com.yzq.common.constants.RoutePath
import com.yzq.common.extend.transform
import com.yzq.common.ui.BaseActivity
import com.yzq.common.widget.Dialog
import com.yzq.kotlincommon.R
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.dialog_scroll_layout.*
import java.util.concurrent.TimeUnit


/**
 * @description: 弹窗
 * @author : yzq
 * @date   : 2019/4/30
 * @time   : 13:38
 *
 */

@Route(path = RoutePath.Main.DIALOG)
class DialogActivity : BaseActivity() {


    override fun getContentLayoutId(): Int {

        return R.layout.activity_dialog
    }


    @SuppressLint("AutoDispose")
    override fun initWidget() {
        super.initWidget()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        initToolbar(toolbar, "弹窗", true)



        btn_base.setOnClickListener {
            Dialog.showBase(message = "基础弹窗，没有任何回调，只有确定按钮且没有回调，一般用于信息提示")
        }

        btn_only_positive_callback.setOnClickListener {

            Dialog.showOnlyPostiveCallBackDialog(message = "只有确定选项和回调的弹窗，一般用于强制性的操作").subscribe {

                ToastUtils.showShort("点击了确定")
            }
        }
        btn_positive_callback.setOnClickListener {
            Dialog.showPositiveCallbackDialog(message = "双选项，但只有确定按钮回调的弹窗，一般用于选择性的操作").subscribe {

                ToastUtils.showShort("点击了确定")
            }

        }

        btn_callback.setOnClickListener {
            Dialog.showCallbackDialog(message = "双选项双回调").subscribe {
                if (it) {
                    ToastUtils.showShort("点击了确定")
                } else {
                    ToastUtils.showShort("点击了取消")
                }

            }

        }

        btn_single_select.setOnClickListener {

            val datas = arrayListOf("java", "kotlin", "android", "python", "flutter")

            Dialog.showSingleSelectList(title = "语言", items = datas).subscribe {
                ToastUtils.showShort(it)

            }
        }

        btn_input.setOnClickListener {
            Dialog.showInputDialog().subscribe {
                ToastUtils.showShort(it)
            }

        }

        btn_loadding.setOnClickListener {

            showLoadingDialog("登录中...")

            Observable.timer(3, TimeUnit.SECONDS)
                    .subscribe {
                        dismissLoadingDialog()
                    }
        }


        btn_progress.setOnClickListener {
            var count = 0
            showProgressDialog("模拟进度")
            Observable.interval(200, TimeUnit.MILLISECONDS)
                    .transform(this)
                    .subscribe(object : Observer<Long> {

                        lateinit var d: Disposable
                        override fun onComplete() {
                        }

                        override fun onSubscribe(d: Disposable) {
                            this.d = d
                        }

                        override fun onNext(t: Long) {

                            count += 5
                            if (count <= 100) {
                                changeProgress(count)
                            } else {
                                dismissProgressDialog()
                                d.dispose()
                            }

                        }

                        override fun onError(e: Throwable) {
                        }

                    })


        }

        btn_select_year.setOnClickListener { Dialog.selectYear().subscribe { ToastUtils.showShort(it) } }
        btn_select_date.setOnClickListener { Dialog.selectDate().subscribe { ToastUtils.showShort(it) } }
        btn_select_time.setOnClickListener { Dialog.selectHourAndMinute().subscribe { ToastUtils.showShort(it) } }



        btn_bottom_dialog.setOnClickListener {


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
