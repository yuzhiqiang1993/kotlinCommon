package com.yzq.common.extend

import android.annotation.SuppressLint
import android.text.InputType
import android.text.TextUtils
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.input.input
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.afollestad.materialdialogs.list.listItems
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.UriUtils
import com.qingmei2.rximagepicker.core.RxImagePicker
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission
import com.ycuwq.datepicker.date.DatePicker
import com.ycuwq.datepicker.date.YearPicker
import com.ycuwq.datepicker.time.HourAndMinutePicker
import com.yzq.common.R
import com.yzq.common.ui.BaseActivity
import com.yzq.lib_base.AppContext
import com.yzq.lib_base.constants.BaseConstants
import com.yzq.lib_base.extend.loading
import com.yzq.lib_base.extend.progress
import io.reactivex.Single
import java.io.File


/*
* 对BaseActivity类的扩展
* */



/*获取一个新的Dialog实例*/
fun BaseActivity.getNewDialog(): MaterialDialog {

    return MaterialDialog(this).cancelOnTouchOutside(false).cancelable(false).lifecycleOwner(this)
}


/**
 * 基础弹窗  没有任何回调  只有确定按钮
 *
 * @param title  标题
 * @param message  信息
 * @param positiveText  确定按钮的文本
 */

fun BaseActivity.showBaseDialog(
    title: String = BaseConstants.HINT,
    message: String,
    positiveText: String = BaseConstants.SURE
) {

    getNewDialog().show {
        title(text = title)
        message(text = message)
        positiveButton(text = positiveText)

    }

}


/**
 * 显示一个只有确定按钮的弹窗
 *
 * @param title  标题
 * @param message  提示信息
 * @param positiveText  确定按钮的文字
 */
fun BaseActivity.showOnlyPostiveCallBackDialog(
    title: String = BaseConstants.HINT,
    message: String,
    positiveText: String = BaseConstants.SURE
): Single<Boolean> {

    return Single.create { singleEmitter ->

        getNewDialog().show {
            title(text = title)
            message(text = message)
            positiveButton(text = positiveText)

            positiveButton {
                singleEmitter.onSuccess(true)
            }

        }


    }


}


/**
 * 有取消和确定按钮的弹窗 确定按钮有回调
 *
 * @param title  标题
 * @param message  信息
 * @param positiveText 确定按钮文本
 * @param negativeText  取消按钮文本
 */
fun BaseActivity.showPositiveCallbackDialog(
    title: String = BaseConstants.HINT,
    message: String,
    positiveText: String = BaseConstants.SURE,
    negativeText: String = BaseConstants.CANCLE
): Single<Boolean> {

    return Single.create { singleEmitter ->

        getNewDialog().show {
            title(text = title)
            message(text = message)
            positiveButton(text = positiveText)
            negativeButton(text = negativeText)
            positiveButton {
                singleEmitter.onSuccess(true)
            }
        }


    }


}


/**
 * 带有确定和取消回调的弹窗
 *
 * @param title  标题
 * @param message  信息
 * @param positiveText  确定按钮文本
 * @param negativeText  取消按钮文本
 */
fun BaseActivity.showCallbackDialog(
    title: String = BaseConstants.HINT,
    message: String,
    positiveText: String = BaseConstants.SURE,
    negativeText: String = BaseConstants.CANCLE
): Single<Boolean> {
    return Single.create { singleEmitter ->

        getNewDialog().show {
            title(text = title)
            message(text = message)
            positiveButton(text = positiveText)
            negativeButton(text = negativeText)
            positiveButton {
                singleEmitter.onSuccess(true)
            }
            negativeButton {
                singleEmitter.onSuccess(false)

            }
        }


    }


}


/**
 * 返回页面提示弹窗
 *
 * @param title  标题
 * @param message  消息
 * @param positiveText  确定按钮文本
 * @param negativeText  取消按钮文本
 */
fun BaseActivity.showBackHintDialog(
    title: String = BaseConstants.HINT,
    message: String = BaseConstants.BACK_HINT,
    positiveText: String = BaseConstants.SURE,
    negativeText: String = BaseConstants.CANCLE
): Single<Boolean> {

    return Single.create { singleEmitter ->

        getNewDialog().show {

            title(text = title)
            message(text = message)
            positiveButton(text = positiveText)
            negativeButton(text = negativeText)
            positiveButton {
                singleEmitter.onSuccess(true)
            }
        }

    }


}


/**
 * 单选列表弹窗
 *
 * @param title  标题
 * @param message  消息
 * @param items  选项
 */
fun BaseActivity.showSingleSelectList(
    title: String = BaseConstants.HINT,
    message: String = "",
    items: List<String>

): Single<String> {

    return Single.create { singleEmitter ->

        getNewDialog().show {
            title(text = title)
            if (!TextUtils.isEmpty(message)) {
                message(text = message)
            }
            listItems(items = items) { dialog, index, text ->
                singleEmitter.onSuccess(text)
            }

        }


    }


}


/**
 * 带输入框的弹窗
 *
 * @param title  标题
 * @param positiveText  确定按钮文字
 * @param negativeText  取消按钮文字
 * @param message  提示信息
 * @param inputHint  输入提示文本
 * @param prefill  预填充的文本
 * @param inputType  输入类型
 * @param allowEmptyInput  是否允许输入空
 */
fun BaseActivity.showInputDialog(
    title: String = BaseConstants.HINT,
    positiveText: String = BaseConstants.SURE,
    negativeText: String = BaseConstants.CANCLE,
    message: String = "",
    inputHint: String = "",
    prefill: String = "",
    inputType: Int = InputType.TYPE_CLASS_TEXT,
    allowEmptyInput: Boolean = false
): Single<String> {
    return Single.create<String> { singleEmitter ->

        getNewDialog().show {

            title(text = title)
            if (!TextUtils.isEmpty(message)) {
                message(text = message)
            }
            positiveButton(text = positiveText)
            negativeButton(text = negativeText)

            input(
                inputHint,
                prefill = prefill,
                allowEmpty = allowEmptyInput,
                inputType = inputType
            ) { materialDialog, charSequence ->
                singleEmitter.onSuccess(charSequence.toString().trim())

            }

        }


    }

}

/**
 * 加载框
 *
 */
fun BaseActivity.getLoadingDialog(): MaterialDialog {
    return getNewDialog().loading()
}

/**
 * 进度框
 *
 * @param title  标题
 */
fun BaseActivity.getProgressDialog(title: String): MaterialDialog {

    return getNewDialog().progress().title(text = title)

}


/*选择年份*/
fun BaseActivity.selectYear(
    title: String = "选择年份",
    positiveText: String = BaseConstants.SURE,
    negativeText: String = BaseConstants.CANCLE
): Single<String> {

    return Single.create { singleEmitter ->

        getNewDialog().show {
            title(text = title)
            positiveButton(text = positiveText)
            negativeButton(text = negativeText)
            customView(viewRes = R.layout.layout_year_picker, scrollable = false)
            positiveButton {
                val yearPicker = it.getCustomView().findViewById<YearPicker>(R.id.year_picker)

                singleEmitter.onSuccess(yearPicker.selectedYear.toString())
            }

        }


    }


}


/**
 * 选择年月日
 *
 * @param title  标题
 * @param positiveText  确定文本
 * @param negativeText  取消文本
 */
fun BaseActivity.selectDate(
    title: String = "选择日期",
    positiveText: String = BaseConstants.SURE,
    negativeText: String = BaseConstants.CANCLE
): Single<String> {

    return Single.create { singleEmitter ->

        getNewDialog().show {
            title(text = title)
            positiveButton(text = positiveText)
            negativeButton(text = negativeText)
            customView(viewRes = R.layout.layout_date_picker, scrollable = false)
            positiveButton {
                val datePicker = it.findViewById<DatePicker>(R.id.date_picker)
                var selectedMonth = datePicker.month.toString()
                var selectedDay = datePicker.day.toString()

                if (datePicker.month < 10) {
                    selectedMonth = "0${datePicker.month}"
                }

                if (datePicker.day < 10) {
                    selectedDay = "0${datePicker.day}"
                }

                val selectedDate = "${datePicker.year}-${selectedMonth}-${selectedDay}"
                LogUtils.i("选择的年与日：${selectedDate}")

                singleEmitter.onSuccess(selectedDate)


            }

        }


    }


}


/**
 * 选择小时和分钟
 *
 * @param title  标题
 * @param positiveText 确定按钮文本
 * @param negativeText  取消按钮文本
 */
fun BaseActivity.selectHourAndMinute(
    title: String = "选择时间",
    positiveText: String = BaseConstants.SURE,
    negativeText: String = BaseConstants.CANCLE
): Single<String> {
    return Single.create { singleEmitter ->

        getNewDialog().show {
            title(text = title)
            positiveButton(text = positiveText)
            negativeButton(text = negativeText)
            customView(viewRes = R.layout.layout_hour_minute_picker, scrollable = false)
            positiveButton {

                val hourAndMinutePicker =
                    it.findViewById<HourAndMinutePicker>(R.id.hour_minute_picker)
                var selectedHour = hourAndMinutePicker.hour.toString()
                var selectedMinute = hourAndMinutePicker.minute.toString()
                if (hourAndMinutePicker.hour < 10) {
                    selectedHour = "0${hourAndMinutePicker.hour}"
                }

                if (hourAndMinutePicker.minute < 10) {
                    selectedMinute = "0${hourAndMinutePicker.minute}"
                }


                val selectedTime = "${selectedHour}:${selectedMinute}"
                LogUtils.i("选择时间：${selectedTime}}")

                singleEmitter.onSuccess(selectedTime)
            }
        }


    }

}


/*权限申请*/
fun BaseActivity.requestPermission(vararg permissions: String): Single<Boolean> {
    return Single.create { singleEmitter ->

        AndPermission.with(this)
            .runtime()
            .permission(permissions)
            .onGranted {
                singleEmitter.onSuccess(true)
            }.onDenied {
                permissionDenied(it)
            }.start()


    }

}

/**
 * 权限被拒绝
 *
 * @param permissions  要申请的权限
 *
 */
private fun BaseActivity.permissionDenied(permissions: List<String>) {

    if (AndPermission.hasAlwaysDeniedPermission(AppContext, permissions)) {
        this.showPermissionDailog(permissions)
    } else {
        ToastUtils.showShort("权限被拒绝")
    }


}


private val REQUEST_CODE_SETTING = 1
/**
 * 用户拒绝权限后的提示框
 *
 * @param permissions  用户拒绝的权限
 */
@SuppressLint("CheckResult")
private fun BaseActivity.showPermissionDailog(permissions: List<String>) {

    val permissionNames = Permission.transformText(AppContext, permissions)
    val message = "我们需要的 ${TextUtils.join("、", permissionNames)} 权限被拒绝,这将导致部分功能不可用，请手动开启! "


    showPositiveCallbackDialog(
        title = "开启权限",
        message = message,
        positiveText = "去开启",
        negativeText = "不开启"
    )
        .subscribe { click ->
            AndPermission.with(this)
                .runtime()
                .setting()
                .start(REQUEST_CODE_SETTING)

        }


}


/*调相机拍照*/
fun BaseActivity.openCamera(): Single<File> {
    return Single.create { singleEmitter ->

        requestPermission(Permission.CAMERA)
            .subscribe { hasPermission ->
                RxImagePicker.create().openCamera(this)
                    .subscribe { result ->
                        val file = UriUtils.uri2File(result.uri)
                        singleEmitter.onSuccess(file)
                    }


            }
    }
}
