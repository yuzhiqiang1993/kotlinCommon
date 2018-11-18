package com.yzq.common.widget

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.text.InputType
import android.widget.DatePicker
import android.widget.TimePicker
import com.afollestad.materialdialogs.MaterialDialog
import com.blankj.utilcode.util.LogUtils
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

        private lateinit var context: Context

        fun initDialog(context: Context) {
            this.context = context
        }


        fun getNewBuilder(): MaterialDialog.Builder {
            return MaterialDialog.Builder(context).canceledOnTouchOutside(false).cancelable(false)
        }


        fun showBase(title: String = BaseContstants.HINT, content: String, positiveText: String = BaseContstants.SURE) {
            getNewBuilder()
                    .title(title)
                    .content(content)
                    .positiveText(positiveText)
                    .build()
                    .show()

        }


        fun showOnlyPostiveCallBackDialog(
                title: String = BaseContstants.HINT,
                content: String,
                positiveText: String = BaseContstants.SURE,
                positiveCallback: MaterialDialog.SingleButtonCallback
        ) {
            getNewBuilder()
                    .title(title)
                    .content(content)
                    .positiveText(positiveText)
                    .onPositive(positiveCallback)
                    .show()

        }

        fun showPositiveCallbackDialog(
                title: String = BaseContstants.HINT,
                content: String,
                positiveText: String = BaseContstants.SURE,
                negativeText: String = BaseContstants.CANCLE,
                positiveCallback: MaterialDialog.SingleButtonCallback
        ) {
            getNewBuilder()
                    .title(title)
                    .content(content)
                    .positiveText(positiveText)
                    .negativeText(negativeText)
                    .onPositive(positiveCallback)
                    .show()

        }


        fun showCallbackDialog(
                title: String = BaseContstants.HINT,
                content: String,
                positiveText: String = BaseContstants.SURE,
                negativeText: String = BaseContstants.CANCLE,
                positiveCallback: MaterialDialog.SingleButtonCallback,
                negativeCallback: MaterialDialog.SingleButtonCallback
        ) {
            getNewBuilder()
                    .title(title)
                    .content(content)
                    .positiveText(positiveText)
                    .negativeText(negativeText)
                    .onPositive(positiveCallback)
                    .onNegative(negativeCallback)
                    .show()

        }


        fun showBackHintDialog(
                title: String = BaseContstants.HINT,
                content: String = BaseContstants.BACK_HINT,
                positiveText: String = BaseContstants.SURE,
                negativeText: String = BaseContstants.CANCLE,
                positiveCallback: MaterialDialog.SingleButtonCallback
        ) {
            getNewBuilder()
                    .title(title)
                    .content(content)
                    .positiveText(positiveText)
                    .negativeText(negativeText)
                    .onPositive(positiveCallback)
                    .show()

        }


        fun showListDialog(
                title: String = BaseContstants.HINT,
                content: String,
                items: List<*>,
                callback: MaterialDialog.ListCallback
        ) {
            getNewBuilder()
                    .title(title)
                    .content(content)
                    .items(items)
                    .itemsCallback(callback)
                    .show()

        }

        fun showInputDialog(
                title: String = BaseContstants.HINT,
                positiveText: String = BaseContstants.SURE,
                negativeText: String = BaseContstants.CANCLE,
                content: String,
                inputHint: String = "",
                prefill: String = "",
                inputType: Int = InputType.TYPE_CLASS_TEXT,
                allowEmptyInput: Boolean = false,
                callback: MaterialDialog.InputCallback
        ) {

            getNewBuilder()
                    .title(title)
                    .content(content)
                    .positiveText(positiveText)
                    .negativeText(negativeText)
                    .inputType(inputType)
                    .input(inputHint, prefill, allowEmptyInput, callback)
                    .show()
        }

        fun getLoaddingDialog(): MaterialDialog? {
            return getNewBuilder()
                    .progress(true, 0)
                    .progressIndeterminateStyle(false)
                    .build()
        }

        fun getProgressDialog(title: String, content: String): MaterialDialog? {
            return getNewBuilder()
                    .title(title)
                    .content(content)
                    .progress(false, 100, true)
                    .build()


        }


        /*选择日期*/
        fun showDatePickerDialog(): Observable<String> {
            return Observable.create<String> {
                var c = Calendar.getInstance()
                DatePickerDialog(context, object : DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
                        var month: String
                        var day: String
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
                var c = Calendar.getInstance()
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

                        var selectTime = "${hour}:${minute}"
                        LogUtils.i("选择的日期$selectTime")
                        it.onNext(selectTime)
                        it.onComplete()

                    }
                }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show()


            }


        }

    }
}


