package com.yzq.kotlincommon.ui.activity

import androidx.lifecycle.lifecycleScope
import com.hjq.toast.Toaster
import com.loper7.date_time_picker.DateTimeConfig
import com.therouter.router.Route
import com.yzq.base.extend.initToolbar
import com.yzq.base.extend.setOnThrottleTimeClick
import com.yzq.baseui.BaseActivity
import com.yzq.binding.viewBinding
import com.yzq.coroutine.ext.launchSafety
import com.yzq.dialog.BubbleLoadingDialog
import com.yzq.dialog.LottieDialog
import com.yzq.dialog.PromptDialog
import com.yzq.dialog.selectYear
import com.yzq.dialog.showDatePicker
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.ActivityDialogBinding
import com.yzq.kotlincommon.dialog.CustomDialog
import com.yzq.router.RoutePath
import kotlinx.coroutines.delay

/**
 * @description: 弹窗
 * @author : yzq
 * @date : 2019/4/30
 * @time : 13:38
 *
 */
@Route(path = RoutePath.Main.DIALOG)
class DialogActivity : BaseActivity() {


    private val binding by viewBinding(ActivityDialogBinding::inflate)
    private val loadingDialog by lazy { BubbleLoadingDialog(this) }

    override fun initWidget() {

        binding.run {
            initToolbar(includedToolbar.toolbar, "弹窗", true)


            layoutScrollContent.btnBase.setOnThrottleTimeClick {
//                showBaseDialog(message = "基础弹窗，没有任何回调，只有确定按钮且没有回调，一般用于信息提示")
//                Bugly.testCrash(BuglyConstants.JAVA_CRASH)

                PromptDialog(this@DialogActivity).apply {
                    title("提示")
                    content("基础弹窗，没有任何回调，只有确定按钮且没有回调，一般用于信息提示")
                    singlePositiveBtn { v ->

                    }
                }.safeShow()

            }

            layoutScrollContent.btnOnlyPositiveCallback.setOnThrottleTimeClick {
                PromptDialog(this@DialogActivity).apply {
                    content("只有确定选项和回调的弹窗，一般用于强制性的操作")
                    singlePositiveBtn { v ->
                        Toaster.showShort("点击了确定")
                    }
                }.safeShow()
            }
            layoutScrollContent.btnPositiveCallback.setOnThrottleTimeClick {

                PromptDialog(this@DialogActivity).apply {
                    content("双选项，但只有确定按钮回调的弹窗，一般用于选择性的操作")
                    positiveBtn { v ->
                        Toaster.showShort("点击了确定")
                    }
                }.safeShow()

            }

            layoutScrollContent.btnCallback.setOnThrottleTimeClick {

                PromptDialog(this@DialogActivity).apply {
                    content("双选项双回调")
                    positiveBtn { v ->
                        Toaster.showShort("点击了确定")
                    }
                    negativeBtn {
                        Toaster.showShort("点击了取消")
                    }
                }.safeShow()
            }


            layoutScrollContent.btnLoading.setOnThrottleTimeClick {
                lifecycleScope.launchSafety {
                    loadingDialog.content(resources.getString(R.string.loading)).safeShow()
                    delay(1000)
                    loadingDialog.content("就要完成了...")
                    delay(2000)
                    loadingDialog.safeDismiss()
                }
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
                CustomDialog(this@DialogActivity)
                    .config {
                        animStyle(R.style.DialogAnimation)
                    }
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
                LottieDialog(this@DialogActivity).lottieUrl("https://assets7.lottiefiles.com/packages/lf20_5lTxAupekw.json")

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
