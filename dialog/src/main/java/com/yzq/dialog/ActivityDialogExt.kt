package com.yzq.dialog

import android.text.format.DateFormat
import androidx.core.app.ComponentActivity
import com.loper7.date_time_picker.DateTimeConfig
import com.loper7.date_time_picker.dialog.CardDatePickerDialog


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
    positiveText: String = resources.getString(R.string.sure),
    negativeText: String = resources.getString(R.string.cancel),
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
    positiveText: String = resources.getString(R.string.sure),
    negativeText: String = resources.getString(R.string.cancel),
    datePickerListener: DatePickerListener,
) {

    CardDatePickerDialog.builder(this).setTitle(title).showBackNow(showBackNow)
        .showFocusDateInfo(showFocusDateInfo).showDateLabel(showDateLabel).setMaxTime(maxTime)
        .setMinTime(minTime).setDefaultTime(defaultTime).setDisplayType(displayType)
//        .setBackGroundModel(CardDatePickerDialog.CARD)
        .setChooseDateModel(DateTimeConfig.GLOBAL_CHINA)
        .setBackGroundModel(R.drawable.shape_bg_dialog_custom)
        .setOnChoose(positiveText) { millisecond ->
            val dateStr = DateFormat.format(dateFormat, millisecond).toString()
            datePickerListener(millisecond, dateStr)
        }.setOnCancel(negativeText).build().show()
}
