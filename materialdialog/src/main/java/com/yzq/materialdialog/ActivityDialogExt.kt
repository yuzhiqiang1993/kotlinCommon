package com.yzq.materialdialog

import android.text.TextUtils
import android.text.format.DateFormat
import androidx.core.app.ComponentActivity
import com.afollestad.materialdialogs.DialogCallback
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.InputCallback
import com.afollestad.materialdialogs.input.input
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.afollestad.materialdialogs.list.ItemListener
import com.afollestad.materialdialogs.list.listItems
import com.loper7.date_time_picker.DateTimeConfig
import com.loper7.date_time_picker.dialog.CardDatePickerDialog

/*获取一个新的Dialog实例*/
fun ComponentActivity.getNewDialog(): MaterialDialog {

    return MaterialDialog(this)
        .cancelOnTouchOutside(false)
        .cancelable(false)
        .lifecycleOwner(this)
}

/**
 * 基础弹窗  没有任何回调  只有确定按钮
 *
 * @param title  标题
 * @param message  信息
 * @param positiveText  确定按钮的文本
 */

fun ComponentActivity.showBaseDialog(
    title: String = HINT,
    message: String,
    positiveText: String = SURE,
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
fun ComponentActivity.showOnlyPostiveCallBackDialog(
    title: String = HINT,
    message: String,
    positiveText: String = SURE,
    callback: DialogCallback,
) {

    getNewDialog().show {
        title(text = title)
        message(text = message)
        positiveButton(text = positiveText, click = callback)
    }
}

/**
 *
 * @receiver ComponentActivity
 * @param title String  标题
 * @param message String  显示内容
 * @param positiveText String  确定文本
 * @param negativeText String  取消文本
 * @param positiveCallback Function1<MaterialDialog, Unit>  确定回调
 */
fun ComponentActivity.showPositiveCallbackDialog(
    title: String = HINT,
    message: String,
    positiveText: String = SURE,
    negativeText: String = CANCEL,
    positiveCallback: DialogCallback,
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
fun ComponentActivity.showCallbackDialog(
    title: String = HINT,
    message: String,
    positiveText: String = SURE,
    negativeText: String = CANCEL,
    positiveCallback: DialogCallback,
    negativeCallback: DialogCallback,
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
fun ComponentActivity.showBackHintDialog(
    title: String = HINT,
    message: String = BACK_HINT,
    positiveText: String = SURE,
    positiveCallback: DialogCallback,
    negativeText: String = CANCEL,
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
fun ComponentActivity.showSingleSelectList(
    title: String = HINT,
    message: String = "",
    items: List<String>,
    listListener: ItemListener,

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

fun ComponentActivity.showInputDialog(
    title: String = HINT,
    positiveText: String = SURE,
    negativeText: String = CANCEL,
    message: String = "",
    inputHint: String = "",
    prefill: String = "",
    inputType: Int = android.text.InputType.TYPE_CLASS_TEXT,
    allowEmptyInput: Boolean = false,
    waitForPositiveButton: Boolean = true,
    inputCallback: InputCallback,
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
fun ComponentActivity.getLoadingDialog(): MaterialDialog {
    return getNewDialog().loading()
}

/**
 * 进度框
 */
fun ComponentActivity.getProgressDialog(): MaterialDialog {

    return getNewDialog().progress().cancelOnTouchOutside(false).cancelable(false)
}

/**
 * 选择年份
 * @receiver ComponentActivity
 * @param displayType MutableList<Int>
 * @param title String
 * @param showBackNow Boolean
 * @param showFocusDateInfo Boolean
 * @param showDateLabel Boolean
 * @param maxTime Long
 * @param minTime Long
 * @param defaultTime Long
 * @param dateFormat String
 * @param positiveText String
 * @param negativeText String
 * @param datePickerListener Function2<[@kotlin.ParameterName] Long, [@kotlin.ParameterName] String, Unit>
 */
fun ComponentActivity.selectYear(
    displayType: MutableList<Int> = arrayListOf(DateTimeConfig.YEAR),
    title: String = getString(R.string.select_year),
    showBackNow: Boolean = false,
    showFocusDateInfo: Boolean = true,
    showDateLabel: Boolean = true,
    maxTime: Long = 0,
    minTime: Long = 0,
    defaultTime: Long = 0,
    dateFormat: String = "yyyy",
    positiveText: String = SURE,
    negativeText: String = CANCEL,
    datePickerListener: DatePickerListener,
) {

    showDatePicker(
        displayType,
        title,
        showBackNow,
        showFocusDateInfo,
        showDateLabel,
        maxTime,
        minTime,
        defaultTime,
        dateFormat,
        positiveText,
        negativeText,
        datePickerListener
    )
}

/**
 * Show date picker
 *
 * @param displayType 显示类型
 * @param title  //标题
 * @param showBackNow //是否显示回到现在按钮
 * @param showFocusDateInfo  //是否显示选中日期信息
 * @param showDateLabel
 * @param maxTime   最大时间
 * @param minTime   最小时间
 * @param defaultTime
 * @param dateFormat
 * @param positiveText
 * @param negativeText
 * @param datePickerListener
 */
fun ComponentActivity.showDatePicker(
    displayType: MutableList<Int> = arrayListOf(
        DateTimeConfig.YEAR,
        DateTimeConfig.MONTH,
        DateTimeConfig.DAY,
        DateTimeConfig.HOUR,
        DateTimeConfig.MIN,
        DateTimeConfig.SECOND
    ),
    title: String = "",
    showBackNow: Boolean = true,
    showFocusDateInfo: Boolean = true,
    showDateLabel: Boolean = true,
    maxTime: Long = 0,
    minTime: Long = 0,
    defaultTime: Long = 0,
    dateFormat: String = "yyyy-MM-dd HH:mm:ss",
    positiveText: String = SURE,
    negativeText: String = CANCEL,
    datePickerListener: DatePickerListener,
) {

    CardDatePickerDialog.builder(this)
        .setTitle(title)
        .showBackNow(showBackNow)
        .showFocusDateInfo(showFocusDateInfo)
        .showDateLabel(showDateLabel)
        .setMaxTime(maxTime)
        .setMinTime(minTime)
        .setDefaultTime(defaultTime)
        .setDisplayType(displayType)
        .setBackGroundModel(CardDatePickerDialog.CARD)
        .setChooseDateModel(DateTimeConfig.GLOBAL_CHINA)
//        .setBackGroundModel(R.drawable.shape_bg_dialog_custom)
        .setOnChoose(positiveText) { millisecond ->
            val dateStr = DateFormat.format(dateFormat, millisecond).toString()
            datePickerListener(millisecond, dateStr)
        }.setOnCancel(negativeText).build().show()
}
