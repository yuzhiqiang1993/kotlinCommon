package com.yzq.common.widget

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.text.InputType
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.MaterialDialog
import com.blankj.utilcode.util.LogUtils
import com.ycuwq.datepicker.date.YearPicker
import com.yzq.common.R
import com.yzq.common.constants.BaseContstants
import io.reactivex.Observable
import java.util.*


/**
 * @description: Dialog封装
 * @author : yzq
 * @date   : 2018/7/9
 * @time   : 13:30
 */

class Dialog {

    companion object {

        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context

        fun initDialog(context: Context) {
            this.context = context
        }


        fun getNewBuilder(): MaterialDialog.Builder {
            return MaterialDialog.Builder(context).canceledOnTouchOutside(false).cancelable(false)
        }


        /*简单的提示框  没有回调*/
        fun showBase(title: String = BaseContstants.HINT, content: String, positiveText: String = BaseContstants.SURE) {
            getNewBuilder()
                    .title(title)
                    .content(content)
                    .positiveText(positiveText)
                    .build()
                    .show()

        }


        /*只有确定按钮且只有确定回调*/
        fun showOnlyPostiveCallBackDialog(
                title: String = BaseContstants.HINT,
                content: String,
                positiveText: String = BaseContstants.SURE
        ): Observable<Boolean> {

            return Observable.create<Boolean> {

                getNewBuilder()
                        .title(title)
                        .content(content)
                        .positiveText(positiveText)
                        .onPositive(object : MaterialDialog.SingleButtonCallback {
                            override fun onClick(dialog: MaterialDialog, which: DialogAction) {

                                it.onNext(true)
                                it.onComplete()

                            }

                        })
                        .show()

            }


        }


        /*有取消和确定按钮的弹窗 确定按钮有回调*/
        fun showPositiveCallbackDialog(
                title: String = BaseContstants.HINT,
                content: String,
                positiveText: String = BaseContstants.SURE,
                negativeText: String = BaseContstants.CANCLE
        ): Observable<Boolean> {

            return Observable.create<Boolean> {

                getNewBuilder()
                        .title(title)
                        .content(content)
                        .positiveText(positiveText)
                        .negativeText(negativeText)
                        .onPositive(object : MaterialDialog.SingleButtonCallback {
                            override fun onClick(dialog: MaterialDialog, which: DialogAction) {

                                it.onNext(true)
                                it.onComplete()

                            }

                        })
                        .show()

            }


        }


        /*带有确定和取消回调的弹窗*/
        fun showCallbackDialog(
                title: String = BaseContstants.HINT,
                content: String,
                positiveText: String = BaseContstants.SURE,
                negativeText: String = BaseContstants.CANCLE
        ): Observable<Boolean> {
            return Observable.create<Boolean> {

                getNewBuilder()
                        .title(title)
                        .content(content)
                        .positiveText(positiveText)
                        .negativeText(negativeText)
                        .onPositive(object : MaterialDialog.SingleButtonCallback {
                            override fun onClick(dialog: MaterialDialog, which: DialogAction) {

                                it.onNext(true)
                                it.onComplete()

                            }

                        })
                        .onNegative(object : MaterialDialog.SingleButtonCallback {
                            override fun onClick(dialog: MaterialDialog, which: DialogAction) {

                                it.onNext(false)
                                it.onComplete()

                            }

                        }).show()

            }


        }


        /*返回页面提示弹窗*/
        fun showBackHintDialog(
                title: String = BaseContstants.HINT,
                content: String = BaseContstants.BACK_HINT,
                positiveText: String = BaseContstants.SURE,
                negativeText: String = BaseContstants.CANCLE
        ): Observable<Boolean> {

            return Observable.create {

                getNewBuilder()
                        .title(title)
                        .content(content)
                        .positiveText(positiveText)
                        .negativeText(negativeText)
                        .onPositive(object : MaterialDialog.SingleButtonCallback {
                            override fun onClick(dialog: MaterialDialog, which: DialogAction) {

                                it.onNext(true)
                                it.onComplete()

                            }

                        })
                        .show()
            }


        }


        /*单选列表弹窗*/
        fun showSingleSelectList(
                title: String = BaseContstants.HINT,
                content: String = "",
                items: List<*>

        ): Observable<String> {

            return Observable.create<String> {
                val dialog = getNewBuilder()
                dialog.title(title)
                        .items(items)
                if (!TextUtils.isEmpty(content)) {
                    dialog.content(content)
                }
                dialog.itemsCallback(object : MaterialDialog.ListCallback {
                    override fun onSelection(dialog: MaterialDialog?, itemView: View?, position: Int, text: CharSequence) {
                        it.onNext(text.toString().trim())
                        it.onComplete()
                    }

                }).show()


            }


        }


        /*带输入框的弹窗*/
        fun showInputDialog(
                title: String = BaseContstants.HINT,
                positiveText: String = BaseContstants.SURE,
                negativeText: String = BaseContstants.CANCLE,
                content: String = "",
                inputHint: String = "",
                prefill: String = "",
                inputType: Int = InputType.TYPE_CLASS_TEXT,
                allowEmptyInput: Boolean = false
        ): Observable<String> {
            return Observable.create<String> {
                val dialog = getNewBuilder()
                dialog.title(title)
                        .positiveText(positiveText)
                        .negativeText(negativeText)
                        .inputType(inputType)

                if (!TextUtils.isEmpty(content)) {
                    dialog.content(content)
                }
                dialog.input(inputHint, prefill, allowEmptyInput, object : MaterialDialog.InputCallback {
                    override fun onInput(dialog: MaterialDialog, input: CharSequence) {

                        it.onNext(input.toString().trim())
                        it.onComplete()
                    }

                }).show()

            }

        }

        fun getLoaddingDialog(): MaterialDialog {
            return getNewBuilder()
                    .progress(true, 0)
                    .progressIndeterminateStyle(false)
                    .build()
        }

        fun getProgressDialog(title: String, content: String = ""): MaterialDialog {

            val dialog = getNewBuilder()

            dialog.title(title)
            if (!TextUtils.isEmpty(content)) {
                dialog.content(content)
            }
            return dialog.progress(false, 100, true)
                    .build()


        }


        /*选择日期*/
        fun showDatePickerDialog(): Observable<String> {
            return Observable.create<String> {
                val c = Calendar.getInstance()
                DatePickerDialog(context, object : DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
                        val month: String
                        val day: String
                        val monthNum = monthOfYear + 1
                        if (monthNum < 10) {
                            month = "0$monthNum"
                        } else {
                            month = monthNum.toString()
                        }
                        if (dayOfMonth < 10) {
                            day = "0$dayOfMonth"
                        } else {
                            day = dayOfMonth.toString()
                        }
                        val selectDate = "${year}-${month}-${day}"
                        LogUtils.i("选择的日期$selectDate")
                        it.onNext(selectDate)
                        it.onComplete()

                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show()
            }

        }


        /*时间选择器*/
        fun showTimePickerDialog(): Observable<String> {

            return Observable.create<String> {
                val c = Calendar.getInstance()
                TimePickerDialog(context, object : TimePickerDialog.OnTimeSetListener {
                    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minuteOfHour: Int) {

                        var hour: String
                        var minute: String

                        hour = hourOfDay.toString()
                        if (hourOfDay < 10) {
                            hour = "0${hourOfDay}"
                        }

                        minute = minuteOfHour.toString()
                        if (minuteOfHour < 10) {
                            minute = "0${minuteOfHour}"
                        }

                        val selectTime = "${hour}:${minute}"
                        LogUtils.i("选择的日期$selectTime")
                        it.onNext(selectTime)
                        it.onComplete()

                    }
                }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show()


            }


        }


        /*选择年份*/
        fun selectYear(
                title: String = "选择年份",
                positiveText: String = BaseContstants.SURE,
                negativeText: String = BaseContstants.CANCLE
        ): Observable<String> {

            return Observable.create<String> {

                val yearView = LayoutInflater.from(context).inflate(R.layout.layout_year_picker, null, false)
                val yearPicker = yearView.findViewById<YearPicker>(R.id.yearPicker)

                getNewBuilder()
                        .title(title)
                        .positiveText(positiveText)
                        .negativeText(negativeText)
                        .customView(yearView, false)
                        .onPositive { _, _ ->
                            it.onNext(yearPicker.selectedYear.toString())
                        }
                        .show()


            }


        }


        /*选择年与日*/
        fun selectDate(
                title: String = "选择日期",
                positiveText: String = BaseContstants.SURE,
                negativeText: String = BaseContstants.CANCLE
        ): Observable<String> {


            return Observable.create<String> {

                val dateView = LayoutInflater.from(context).inflate(R.layout.layout_date_picker, null, false)
                val datePicker = dateView.findViewById<com.ycuwq.datepicker.date.DatePicker>(R.id.datePicker)

                getNewBuilder()
                        .title(title)
                        .positiveText(positiveText)
                        .negativeText(negativeText)
                        .customView(dateView, false)
                        .onPositive { _, _ ->

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

                            it.onNext(selectedDate)
                            it.onComplete()

                        }
                        .show()


            }


        }


        /*选择小时和分钟*/
        fun selectHourAndMinute(
                title: String = "选择时间",
                positiveText: String = BaseContstants.SURE,
                negativeText: String = BaseContstants.CANCLE
        ): Observable<String> {


            return Observable.create<String> {

                val dateView = LayoutInflater.from(context).inflate(R.layout.layout_hour_minute_picker, null, false)
                val hourAndMinutePicker =
                        dateView.findViewById<com.ycuwq.datepicker.time.HourAndMinutePicker>(R.id.hourMinutePicker)

                getNewBuilder()
                        .title(title)
                        .positiveText(positiveText)
                        .negativeText(negativeText)
                        .customView(dateView, false)
                        .onPositive { _, _ ->

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

                            it.onNext(selectedTime)
                            it.onComplete()

                        }
                        .show()


            }


        }
    }
}


