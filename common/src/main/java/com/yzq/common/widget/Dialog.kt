package com.yzq.common.widget

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog
import com.yzq.common.constants.BaseContstants


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


        fun showOnlyPostiveCallBackDialog(title: String = BaseContstants.HINT, content: String, positiveText: String = BaseContstants.SURE, positiveCallback: MaterialDialog.SingleButtonCallback) {
            getNewBuilder()
                    .title(title)
                    .content(content)
                    .positiveText(positiveText)
                    .onPositive(positiveCallback)
                    .show()

        }

        fun showPositiveCallbackDialog(title: String = BaseContstants.HINT, content: String, positiveText: String = BaseContstants.SURE, negativeText: String = BaseContstants.CANCLE, positiveCallback: MaterialDialog.SingleButtonCallback) {
            getNewBuilder()
                    .title(title)
                    .content(content)
                    .positiveText(positiveText)
                    .negativeText(negativeText)
                    .onPositive(positiveCallback)
                    .show()

        }


        fun showCallbackDialog(title: String = BaseContstants.HINT, content: String, positiveText: String = BaseContstants.SURE, negativeText: String = BaseContstants.CANCLE, positiveCallback: MaterialDialog.SingleButtonCallback, negativeCallback: MaterialDialog.SingleButtonCallback) {
            getNewBuilder()
                    .title(title)
                    .content(content)
                    .positiveText(positiveText)
                    .negativeText(negativeText)
                    .onPositive(positiveCallback)
                    .onNegative(negativeCallback)
                    .show()

        }


        fun showBackHintDialog(title: String = BaseContstants.HINT, content: String = BaseContstants.BACK_HINT, positiveText: String = BaseContstants.SURE, negativeText: String = BaseContstants.CANCLE, positiveCallback: MaterialDialog.SingleButtonCallback) {
            getNewBuilder()
                    .title(title)
                    .content(content)
                    .positiveText(positiveText)
                    .negativeText(negativeText)
                    .onPositive(positiveCallback)
                    .show()

        }


        fun showListDialog(title: String = BaseContstants.HINT, content: String, items: List<*>, callback: MaterialDialog.ListCallback) {
            getNewBuilder()
                    .title(title)
                    .content(content)
                    .items(items)
                    .itemsCallback(callback)
                    .show()

        }

        fun showInputDialog(title: String = BaseContstants.HINT, content: String, inputHint: String, prefill: String = "", allowEmptyInput: Boolean = false, callback: MaterialDialog.InputCallback) {

            getNewBuilder()
                    .title(title)
                    .content(content)
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
                    .build();
        }
    }
}


