package com.yzq.common.widget

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import android.text.TextUtils
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.input.input
import com.afollestad.materialdialogs.list.listItems
import com.blankj.utilcode.util.LogUtils
import com.ycuwq.datepicker.date.DatePicker
import com.ycuwq.datepicker.date.YearPicker
import com.ycuwq.datepicker.time.HourAndMinutePicker
import com.yzq.common.R
import com.yzq.common.constants.BaseContstants
import com.yzq.common.extend.loading
import com.yzq.common.extend.progress
import io.reactivex.Observable


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

        /**
         * 初始化弹窗
         *
         * @param context  上下文环境
         */
        fun initDialog(context: Context) {
            this.context = context
        }


        /**
         * 创建一个Dialog实例
         *
         */
        fun getNewDialog(): MaterialDialog {
            return MaterialDialog(context).cancelOnTouchOutside(false).cancelable(false)
        }


        /**
         * 基础弹窗  没有任何回调  只有确定按钮
         *
         * @param title  标题
         * @param message  信息
         * @param positiveText  确定按钮的文本
         */

        fun showBase(title: String = BaseContstants.HINT, message: String, positiveText: String = BaseContstants.SURE) {

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
        fun showOnlyPostiveCallBackDialog(
                title: String = BaseContstants.HINT,
                message: String,
                positiveText: String = BaseContstants.SURE
        ): Observable<Boolean> {

            return Observable.create<Boolean> { emitter ->

                getNewDialog().show {
                    title(text = title)
                    message(text = message)
                    positiveButton(text = positiveText)
                    positiveButton {
                        emitter.onNext(true)
                        emitter.onComplete()
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
        fun showPositiveCallbackDialog(
                title: String = BaseContstants.HINT,
                message: String,
                positiveText: String = BaseContstants.SURE,
                negativeText: String = BaseContstants.CANCLE
        ): Observable<Boolean> {

            return Observable.create<Boolean> { emitter ->

                getNewDialog().show {
                    title(text = title)
                    message(text = message)
                    positiveButton(text = positiveText)
                    negativeButton(text = negativeText)
                    positiveButton {
                        emitter.onNext(true)
                        emitter.onComplete()
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
        fun showCallbackDialog(
                title: String = BaseContstants.HINT,
                message: String,
                positiveText: String = BaseContstants.SURE,
                negativeText: String = BaseContstants.CANCLE
        ): Observable<Boolean> {
            return Observable.create<Boolean> { emitter ->

                getNewDialog().show {
                    title(text = title)
                    message(text = message)
                    positiveButton(text = positiveText)
                    negativeButton(text = negativeText)
                    positiveButton {
                        emitter.onNext(true)
                        emitter.onComplete()
                    }
                    negativeButton {
                        emitter.onNext(false)
                        emitter.onComplete()
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
        fun showBackHintDialog(
                title: String = BaseContstants.HINT,
                message: String = BaseContstants.BACK_HINT,
                positiveText: String = BaseContstants.SURE,
                negativeText: String = BaseContstants.CANCLE
        ): Observable<Boolean> {

            return Observable.create { emitter ->

                getNewDialog().show {

                    title(text = title)
                    message(text = message)
                    positiveButton(text = positiveText)
                    negativeButton(text = negativeText)
                    positiveButton {
                        emitter.onNext(true)
                        emitter.onComplete()
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
        fun showSingleSelectList(
                title: String = BaseContstants.HINT,
                message: String = "",
                items: List<String>

        ): Observable<String> {

            return Observable.create<String> {

                getNewDialog().show {
                    title(text = title)
                    if (!TextUtils.isEmpty(message)) {
                        message(text = message)
                    }
                    listItems(items = items) { dialog, index, text ->
                        it.onNext(text)
                        it.onComplete()
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
        fun showInputDialog(
                title: String = BaseContstants.HINT,
                positiveText: String = BaseContstants.SURE,
                negativeText: String = BaseContstants.CANCLE,
                message: String = "",
                inputHint: String = "",
                prefill: String = "",
                inputType: Int = InputType.TYPE_CLASS_TEXT,
                allowEmptyInput: Boolean = false
        ): Observable<String> {
            return Observable.create<String> {

                getNewDialog().show {

                    title(text = title)
                    if (!TextUtils.isEmpty(message)) {
                        message(text = message)
                    }
                    positiveButton(text = positiveText)
                    negativeButton(text = negativeText)

                    input(inputHint, prefill = prefill, allowEmpty = allowEmptyInput, inputType = inputType) { materialDialog, charSequence ->
                        it.onNext(charSequence.toString().trim())
                        it.onComplete()

                    }

                }


            }

        }

        /**
         * 加载框
         *
         */
        fun getLoadingDialog(): MaterialDialog {
            return getNewDialog().loading()


        }

        /**
         * 进度框
         *
         * @param title  标题
         */
        fun getProgressDialog(title: String): MaterialDialog {

            return getNewDialog().progress().title(text = title)

        }


        /*选择年份*/
        fun selectYear(
                title: String = "选择年份",
                positiveText: String = BaseContstants.SURE,
                negativeText: String = BaseContstants.CANCLE
        ): Observable<String> {

            return Observable.create<String> { emitter ->

                getNewDialog().show {
                    title(text = title)
                    positiveButton(text = positiveText)
                    negativeButton(text = negativeText)
                    customView(viewRes = R.layout.layout_year_picker, scrollable = false)
                    positiveButton {
                        val yearPicker = it.getCustomView().findViewById<YearPicker>(R.id.yearPicker)

                        emitter.onNext(yearPicker.selectedYear.toString())
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
        fun selectDate(
                title: String = "选择日期",
                positiveText: String = BaseContstants.SURE,
                negativeText: String = BaseContstants.CANCLE
        ): Observable<String> {


            return Observable.create<String> { emitter ->

                getNewDialog().show {
                    title(text = title)
                    positiveButton(text = positiveText)
                    negativeButton(text = negativeText)
                    customView(viewRes = R.layout.layout_date_picker, scrollable = false)
                    positiveButton {
                        val datePicker = it.findViewById<DatePicker>(R.id.datePicker)
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

                        emitter.onNext(selectedDate)
                        emitter.onComplete()

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
        fun selectHourAndMinute(
                title: String = "选择时间",
                positiveText: String = BaseContstants.SURE,
                negativeText: String = BaseContstants.CANCLE
        ): Observable<String> {
            return Observable.create<String> { emitter ->

                getNewDialog().show {
                    title(text = title)
                    positiveButton(text = positiveText)
                    negativeButton(text = negativeText)
                    customView(viewRes = R.layout.layout_hour_minute_picker, scrollable = false)
                    positiveButton {

                        val hourAndMinutePicker =
                                it.findViewById<HourAndMinutePicker>(R.id.hourMinutePicker)
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

                        emitter.onNext(selectedTime)
                        emitter.onComplete()
                    }
                }


            }


        }
    }
}


