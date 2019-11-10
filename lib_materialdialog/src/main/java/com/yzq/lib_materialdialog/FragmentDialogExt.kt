package com.yzq.lib_materialdialog

import android.text.TextUtils
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.DialogCallback
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.input.InputCallback
import com.afollestad.materialdialogs.input.input
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.afollestad.materialdialogs.list.ItemListener
import com.afollestad.materialdialogs.list.listItems
import com.ycuwq.datepicker.date.DatePicker
import com.ycuwq.datepicker.date.YearPicker
import com.ycuwq.datepicker.time.HourAndMinutePicker


/*获取一个新的Dialog实例*/
fun Fragment.getNewDialog(): MaterialDialog {


    return MaterialDialog(requireActivity()).cancelOnTouchOutside(false).cancelable(false)
        .lifecycleOwner(this)
}


/**
 * 基础弹窗  没有任何回调  只有确定按钮
 *
 * @param title  标题
 * @param message  信息
 * @param positiveText  确定按钮的文本
 */

fun Fragment.showBaseDialog(
    title: String = HINT,
    message: String,
    positiveText: String = SURE
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
fun Fragment.showOnlyPostiveCallBackDialog(
    title: String = HINT,
    message: String,
    positiveText: String = SURE,
    callback: DialogCallback
) {

    getNewDialog().show {
        title(text = title)
        message(text = message)
        positiveButton(text = positiveText, click = callback)
    }


}


/**
 *
 * @receiver Fragment
 * @param title String  标题
 * @param message String  显示内容
 * @param positiveText String  确定文本
 * @param negativeText String  取消文本
 * @param positiveCallback Function1<MaterialDialog, Unit>  确定回调
 */
fun Fragment.showPositiveCallbackDialog(
    title: String = HINT,
    message: String,
    positiveText: String = SURE,
    negativeText: String = CANCLE,
    positiveCallback: DialogCallback
) {


    getNewDialog().show {
        title(text = title)
        message(text = message)
        positiveButton(text = positiveText, click = positiveCallback)
        negativeButton(text = negativeText)


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
fun Fragment.showCallbackDialog(
    title: String = HINT,
    message: String,
    positiveText: String = SURE,
    negativeText: String = CANCLE,
    positiveCallback: DialogCallback,
    negativeCallback: DialogCallback
) {


    getNewDialog().show {
        title(text = title)
        message(text = message)
        positiveButton(text = positiveText, click = positiveCallback)
        negativeButton(text = negativeText, click = negativeCallback)
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
fun Fragment.showBackHintDialog(
    title: String = HINT,
    message: String = BACK_HINT,
    positiveText: String = SURE,
    positiveCallback: DialogCallback,
    negativeText: String = CANCLE
) {


    getNewDialog().show {

        title(text = title)
        message(text = message)
        positiveButton(text = positiveText)
        negativeButton(text = negativeText, click = positiveCallback)


    }


}


/**
 * 单选列表弹窗
 *
 * @param title  标题
 * @param message  消息
 * @param items  选项
 */
fun Fragment.showSingleSelectList(
    title: String = HINT,
    message: String = "",
    items: List<String>,
    listListener: ItemListener

) {
    getNewDialog().show {
        title(text = title)
        if (!TextUtils.isEmpty(message)) {
            message(text = message)
        }
        listItems(items = items, selection = listListener)


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


fun Fragment.showInputDialog(
    title: String = HINT,
    positiveText: String = SURE,
    negativeText: String = CANCLE,
    message: String = "",
    inputHint: String = "",
    prefill: String = "",
    inputType: Int = android.text.InputType.TYPE_CLASS_TEXT,
    allowEmptyInput: Boolean = false,
    waitForPositiveButton: Boolean = true,
    inputCallback: InputCallback
) {
    getNewDialog().show {

        title(text = title)
        if (!TextUtils.isEmpty(message)) {
            message(text = message)
        }
        positiveButton(text = positiveText)
        negativeButton(text = negativeText)

        input(
            hint = inputHint,
            prefill = prefill,
            allowEmpty = allowEmptyInput,
            inputType = inputType,
            waitForPositiveButton = waitForPositiveButton,
            callback = inputCallback
        )


    }

}

/**
 * 加载框
 *
 */
fun Fragment.getLoadingDialog(): MaterialDialog {
    return getNewDialog().loading()
}

/**
 * 进度框
 *
 * @param title  标题
 */
fun Fragment.getProgressDialog(): MaterialDialog {

    return getNewDialog().progress().cancelOnTouchOutside(false).cancelable(false)

}


/*选择年份*/
fun Fragment.selectYear(
    title: String = "选择年份",
    positiveText: String = SURE,
    negativeText: String = CANCLE,
    datePickerListener: DatePickerListener
) {

    getNewDialog().show {
        title(text = title)
        customView(viewRes = R.layout.layout_year_picker, scrollable = false)
        negativeButton(text = negativeText)
        positiveButton(text = positiveText) {
            val yearPicker = it.getCustomView().findViewById<YearPicker>(R.id.year_picker)
            datePickerListener(yearPicker.selectedYear.toString())

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
fun Fragment.selectDate(
    title: String = "选择日期",
    positiveText: String = SURE,
    negativeText: String = CANCLE,
    datePickerListener: DatePickerListener
) {


    getNewDialog().show {
        title(text = title)
        customView(viewRes = R.layout.layout_date_picker, scrollable = false)
        negativeButton(text = negativeText)
        positiveButton(text = positiveText) {
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
            datePickerListener(selectedDate)


        }


    }


}


/**
 * 选择小时和分钟
 * @receiver Fragment
 * @param title String  标题
 * @param positiveText String  确定按钮文本
 * @param negativeText String  取消按钮文本
 * @param datePickerListener Function1<String, Unit>  回调
 */
fun Fragment.selectHourAndMinute(
    title: String = "选择时间",
    positiveText: String = SURE,
    negativeText: String = CANCLE,
    datePickerListener: DatePickerListener
) {


    getNewDialog().show {
        title(text = title)
        customView(viewRes = R.layout.layout_hour_minute_picker, scrollable = false)
        negativeButton(text = negativeText)
        positiveButton(text = positiveText) {

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

            datePickerListener(selectedTime)
        }


    }
}
