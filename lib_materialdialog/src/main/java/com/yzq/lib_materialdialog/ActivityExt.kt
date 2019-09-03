package com.yzq.lib_materialdialog

import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.input.input
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.afollestad.materialdialogs.list.listItems
import com.ycuwq.datepicker.date.DatePicker
import com.ycuwq.datepicker.date.YearPicker
import com.ycuwq.datepicker.time.HourAndMinutePicker
import com.yzq.lib_constants.BaseConstants
import io.reactivex.Single


/*获取一个新的Dialog实例*/
fun AppCompatActivity.getNewDialog(): MaterialDialog {

    return MaterialDialog(this).cancelOnTouchOutside(false).cancelable(false).lifecycleOwner(this)
}


/**
 * 基础弹窗  没有任何回调  只有确定按钮
 *
 * @param title  标题
 * @param message  信息
 * @param positiveText  确定按钮的文本
 */

fun AppCompatActivity.showBaseDialog(
    title: String = BaseConstants.HINT,
    message: String,
    positiveText: String = BaseConstants.SURE
) {

    getNewDialog().show {
        title(R.string.hint)
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
fun AppCompatActivity.showOnlyPostiveCallBackDialog(
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
fun AppCompatActivity.showPositiveCallbackDialog(
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
fun AppCompatActivity.showCallbackDialog(
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
fun AppCompatActivity.showBackHintDialog(
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
fun AppCompatActivity.showSingleSelectList(
    title: String = BaseConstants.HINT,
    message: String = "",
    items: List<String>

): Single<String> {

    return Single.create { singleEmitter ->

        getNewDialog().show {
            title(text = title)
            if (!android.text.TextUtils.isEmpty(message)) {
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
fun AppCompatActivity.showInputDialog(
    title: String = BaseConstants.HINT,
    positiveText: String = BaseConstants.SURE,
    negativeText: String = BaseConstants.CANCLE,
    message: String = "",
    inputHint: String = "",
    prefill: String = "",
    inputType: Int = android.text.InputType.TYPE_CLASS_TEXT,
    allowEmptyInput: Boolean = false
): Single<String> {
    return Single.create<String> { singleEmitter ->

        getNewDialog().show {

            title(text = title)
            if (!android.text.TextUtils.isEmpty(message)) {
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
fun AppCompatActivity.getLoadingDialog(): MaterialDialog {
    return getNewDialog().loading()
}

/**
 * 进度框
 *
 * @param title  标题
 */
fun AppCompatActivity.getProgressDialog(title: String): MaterialDialog {

    return getNewDialog().progress().title(text = title)

}


/*选择年份*/
fun AppCompatActivity.selectYear(
    title: String = "选择年份",
    positiveText: String = BaseConstants.SURE,
    negativeText: String = BaseConstants.CANCLE
): Single<String> {

    return io.reactivex.Single.create { singleEmitter ->

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
fun AppCompatActivity.selectDate(
    title: String = "选择日期",
    positiveText: String = BaseConstants.SURE,
    negativeText: String = BaseConstants.CANCLE
): Single<String> {

    return io.reactivex.Single.create { singleEmitter ->

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
fun AppCompatActivity.selectHourAndMinute(
    title: String = "选择时间",
    positiveText: String = BaseConstants.SURE,
    negativeText: String = BaseConstants.CANCLE
): Single<String> {
    return io.reactivex.Single.create { singleEmitter ->

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

                singleEmitter.onSuccess(selectedTime)
            }
        }
    }

}