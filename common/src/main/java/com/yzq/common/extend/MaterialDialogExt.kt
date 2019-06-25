package com.yzq.common.extend

import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
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
    customView(R.layout.loading_dialog_layout, scrollable = false)
    return this

}

/**
 * 设置加载框的文本
 *
 * @param loaddingMessage
 */
fun MaterialDialog.setLoadingMessage(loaddingMessage: String): MaterialDialog {

    val loaddingMessageTv = getCustomView().findViewById<AppCompatTextView>(R.id.loadding_message_tv)
    loaddingMessageTv.text = loaddingMessage
    return this

}


/**
 * @description: 扩展的进度框
 * @author : yzq
 * @date   : 2019/3/21
 * @time   : 11:22
 *
 */

fun MaterialDialog.progress(): MaterialDialog {

    customView(R.layout.progress_dialog_layout, scrollable = false)
    return this
}

/**
 * 改变进度
 *
 * @param percent
 */
fun MaterialDialog.changeProgress(percent: Int): MaterialDialog {

    val progressBar = getCustomView().findViewById<ProgressBar>(R.id.progress_horizontal)

    val currentProgress = getCustomView().findViewById<TextView>(R.id.current_progress_tv)
    progressBar.progress = percent
    currentProgress.text = "$percent"

    return this


}