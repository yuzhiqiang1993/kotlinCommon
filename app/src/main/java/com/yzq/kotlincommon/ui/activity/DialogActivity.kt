package com.yzq.kotlincommon.ui.activity

import androidx.lifecycle.lifecycleScope
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.hjq.toast.Toaster
import com.loper7.date_time_picker.DateTimeConfig
import com.tencent.bugly.library.Bugly
import com.tencent.bugly.library.BuglyConstants
import com.therouter.router.Route
import com.yzq.base.extend.initToolbar
import com.yzq.base.extend.setOnThrottleTimeClick
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.coroutine.safety_coroutine.launchSafety
import com.yzq.dialog.LottieDialog
import com.yzq.dialog.changeProgress
import com.yzq.dialog.selectYear
import com.yzq.dialog.showBaseDialog
import com.yzq.dialog.showCallbackDialog
import com.yzq.dialog.showDatePicker
import com.yzq.dialog.showInputDialog
import com.yzq.dialog.showOnlyPostiveCallBackDialog
import com.yzq.dialog.showPositiveCallbackDialog
import com.yzq.dialog.showProgressDialog
import com.yzq.dialog.showSingleSelectList
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.ActivityDialogBinding
import com.yzq.kotlincommon.dialog.CustomDialog
import com.yzq.logger.Logger
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

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
            initToolbar(includedToolbar.toolbar, "弹窗", true)


            layoutScrollContent.btnBase.setOnThrottleTimeClick {
                showBaseDialog(message = "基础弹窗，没有任何回调，只有确定按钮且没有回调，一般用于信息提示")
                Bugly.testCrash(BuglyConstants.JAVA_CRASH)
            }

            layoutScrollContent.btnOnlyPositiveCallback.setOnThrottleTimeClick {
                showOnlyPostiveCallBackDialog(message = "只有确定选项和回调的弹窗，一般用于强制性的操作") {
                    Toaster.showShort("点击了确定")
                    Bugly.testCrash(BuglyConstants.NATIVE_CRASH)
                }
            }
            layoutScrollContent.btnPositiveCallback.setOnThrottleTimeClick {
                showPositiveCallbackDialog(message = "双选项，但只有确定按钮回调的弹窗，一般用于选择性的操作") {
                    Toaster.showShort("点击了确定")
                }
            }

            layoutScrollContent.btnCallback.setOnThrottleTimeClick {
                showCallbackDialog(message = "双选项双回调", positiveCallback = {
                    Toaster.showShort("点击了确定")
                }, negativeCallback = {
                    Toaster.showShort("点击了取消")
                })
            }

            layoutScrollContent.btnSingleSelect.setOnThrottleTimeClick {
                val datas = arrayListOf("java", "kotlin", "android", "python", "flutter")

                showSingleSelectList(title = "语言", items = datas) { dialog, index, text ->
                    Toaster.showShort(text.toString())
                }
            }

            layoutScrollContent.btnInput.setOnThrottleTimeClick {
                showInputDialog(positiveText = "完成") { materialDialog, charSequence ->
                    Toaster.showShort(charSequence.toString())
                }
            }


            layoutScrollContent.btnLoading.setOnThrottleTimeClick {
                lifecycleScope.launchSafety {
                    bubleLoadingDialog
                        .content(resources.getString(R.string.loading))
                        .safeShow()
                    delay(1000)
                    bubleLoadingDialog.content("就要完成了...")
                    delay(2000)
                    bubleLoadingDialog.safeDismiss()
                }
            }

            layoutScrollContent.btnProgress.setOnThrottleTimeClick {
                var count = 0

                val progressDialog = showProgressDialog("模拟进度")

                val timerTask = object : TimerTask() {
                    override fun run() {

                        Logger.i("当前线程：${Thread.currentThread().name}")
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

            layoutScrollContent.btnSelectYear.setOnThrottleTimeClick {
                selectYear { millisecond, dateStr ->
                    Toaster.showLong(dateStr)
                }
            }
            layoutScrollContent.btnSelectDate.setOnThrottleTimeClick {
                val dateFormat = "yyyy-MM-dd"
                val displayType =
                    arrayListOf(DateTimeConfig.YEAR, DateTimeConfig.MONTH, DateTimeConfig.DAY)

                showDatePicker(
                    title = "选择年月日", displayType = displayType, dateFormat = dateFormat
                ) { millisecond, dateStr ->
                    Toaster.showLong(dateStr)
                }
            }
            layoutScrollContent.btnSelectTime.setOnThrottleTimeClick {
                val dateFormat = "HH:mm:ss"
                val displayType = arrayListOf(
                    DateTimeConfig.HOUR, DateTimeConfig.MIN, DateTimeConfig.SECOND
                )

                showDatePicker(
                    title = "选择时分秒", displayType = displayType, dateFormat = dateFormat
                ) { millisecond, dateStr ->
                    Toaster.showLong(dateStr)
                }
            }

            layoutScrollContent.btnBottomDialog.setOnThrottleTimeClick {
                MaterialDialog(this@DialogActivity, BottomSheet(LayoutMode.WRAP_CONTENT)).show {
                    title(com.yzq.kotlincommon.R.string.hint)
                    message(text = "bottom sheet")
                    positiveButton(text = "确定")
                    negativeButton(text = "取消")
                }
            }


            layoutScrollContent.btnDialogFragment.setOnThrottleTimeClick {
                CustomDialog(this@DialogActivity)
                    .safeShow()
            }

//            val dialogConfig = DialogConfig
//                .Builder()
//                .width(300)
//                .height(300)
//                .alpha(0.6f)
//                .cancelable(true)
//                .build()
            val lottieDialog =
                LottieDialog(this@DialogActivity)
                    .lottieUrl("https://assets7.lottiefiles.com/packages/lf20_5lTxAupekw.json")

            layoutScrollContent.btnDialogLottie.setOnThrottleTimeClick {
                lottieDialog.safeShow()
                lifecycleScope.launchSafety {
                    delay(5000)
                    lottieDialog.dismiss()
                }


            }

        }
    }
}
