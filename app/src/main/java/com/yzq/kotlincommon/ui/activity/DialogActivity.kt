package com.yzq.kotlincommon.ui.activity

import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.loper7.date_time_picker.DateTimeConfig
import com.yzq.base.extend.initToolbar
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.coroutine.scope.lifeScope
import com.yzq.kotlincommon.databinding.ActivityDialogBinding
import com.yzq.materialdialog.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

/**
 * @description: 弹窗
 * @author : yzq
 * @date : 2019/4/30
 * @time : 13:38
 *
 */
@Route(path = RoutePath.Main.DIALOG)
class DialogActivity : BaseActivity() {

    private val binding by viewbind(ActivityDialogBinding::inflate)

    override fun initWidget() {
        binding.run {
            initToolbar(layoutToolbar.toolbar, "弹窗", true)

            layoutScrollContent.btnBase.setOnClickListener {
                showBaseDialog(message = "基础弹窗，没有任何回调，只有确定按钮且没有回调，一般用于信息提示")
            }

            layoutScrollContent.btnOnlyPositiveCallback.setOnClickListener {
                showOnlyPostiveCallBackDialog(message = "只有确定选项和回调的弹窗，一般用于强制性的操作") {
                    ToastUtils.showShort("点击了确定")
                }
            }
            layoutScrollContent.btnPositiveCallback.setOnClickListener {
                showPositiveCallbackDialog(message = "双选项，但只有确定按钮回调的弹窗，一般用于选择性的操作") {
                    ToastUtils.showShort("点击了确定")
                }
            }

            layoutScrollContent.btnCallback.setOnClickListener {
                showCallbackDialog(
                    message = "双选项双回调",
                    positiveCallback = {
                        ToastUtils.showShort("点击了确定")
                    },
                    negativeCallback = {
                        ToastUtils.showShort("点击了取消")
                    }
                )
            }

            layoutScrollContent.btnSingleSelect
                .setOnClickListener {
                    val datas = arrayListOf("java", "kotlin", "android", "python", "flutter")

                    showSingleSelectList(title = "语言", items = datas) { dialog, index, text ->
                        ToastUtils.showShort(text.toString())
                    }
                }

            layoutScrollContent.btnInput
                .setOnClickListener {
                    showInputDialog(positiveText = "完成") { materialDialog, charSequence ->
                        ToastUtils.showShort(charSequence.toString())
                    }
                }

            layoutScrollContent.btnLoading
                .setOnClickListener {

                    val loadingDialog = showLoadingDialog("登录中...")

                    lifeScope {
                        delay(3000)
                        loadingDialog.dismiss()
                    }
                }

            layoutScrollContent.btnProgress
                .setOnClickListener {
                    var count = 0

                    val progressDialog = showProgressDialog("模拟进度")

                    val timerTask = object : TimerTask() {
                        override fun run() {

                            LogUtils.i("当前线程：${Thread.currentThread().name}")
                            count += 5
                            if (count <= 100) {

                                MainScope().launch {
                                    progressDialog.changeProgress(count)
                                }
                            } else {
                                cancel()
                                MainScope().launch {
                                    progressDialog.dismiss()
                                }
                            }
                        }
                    }

                    Timer().schedule(
                        timerTask, 0, 200
                    )
                }

            layoutScrollContent.btnSelectYear
                .setOnClickListener {
                    selectYear { millisecond, dateStr ->
                        ToastUtils.showLong(dateStr)
                    }
                }
            layoutScrollContent.btnSelectDate
                .setOnClickListener {
                    val dateFormat = "yyyy-MM-dd"
                    val displayType =
                        arrayListOf(DateTimeConfig.YEAR, DateTimeConfig.MONTH, DateTimeConfig.DAY)

                    showDatePicker(
                        title = "选择年月日",
                        displayType = displayType,
                        dateFormat = dateFormat
                    ) { millisecond, dateStr ->
                        ToastUtils.showLong(dateStr)
                    }
                }
            layoutScrollContent.btnSelectTime
                .setOnClickListener {
                    val dateFormat = "HH:mm:ss"
                    val displayType = arrayListOf(
                        DateTimeConfig.HOUR,
                        DateTimeConfig.MIN,
                        DateTimeConfig.SECOND
                    )

                    showDatePicker(
                        title = "选择时分秒", displayType = displayType, dateFormat = dateFormat
                    ) { millisecond, dateStr ->
                        ToastUtils.showLong(dateStr)
                    }
                }

            layoutScrollContent.btnBottomDialog
                .setOnClickListener {
                    MaterialDialog(this@DialogActivity, BottomSheet(LayoutMode.WRAP_CONTENT))
                        .show {
                            title(com.yzq.kotlincommon.R.string.hint)
                            message(text = "bottom sheet")
                            positiveButton(text = "确定")
                            negativeButton(text = "取消")
                        }
                }
        }
    }
}
