package com.yzq.common.extend

import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.yzq.common.R


/**
 * @description: 扩展的加载框
 * @author : yzq
 * @date   : 2019/2/21
 * @time   : 13:03
 *
 */
fun MaterialDialog.loading(): MaterialDialog {
    customView(viewRes = R.layout.loading_dialog_layout, scrollable = false)
    return this

}

fun MaterialDialog.setLoadingMessage(loaddingMessage: String): MaterialDialog {

    val loaddingMessageTv = getCustomView().findViewById<TextView>(R.id.loadding_message_tv)
    loaddingMessageTv.text = loaddingMessage
    return this

}