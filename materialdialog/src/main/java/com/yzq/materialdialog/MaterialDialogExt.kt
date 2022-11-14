package com.yzq.materialdialog

import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView


/**
 * @description: 扩展的加载框
 * @author : yzq
 * @date   : 2019/2/21
 * @time   : 13:03
 *
 */
fun MaterialDialog.loading(): MaterialDialog =
    customView(R.layout.loading_dialog_layout, scrollable = false)


/**
 * 设置加载框的文本
 *
 * @param loadingMessage
 */
fun MaterialDialog.setLoadingMessage(loadingMessage: String = LOADING): MaterialDialog {

    val loadingMessageTv =
        getCustomView().findViewById<AppCompatTextView>(R.id.loading_message_tv)
    loadingMessageTv.text = loadingMessage
    return this

}


/**
 * @description: 扩展的进度框
 * @author : yzq
 * @date   : 2019/3/21
 * @time   : 11:22
 *
 */

fun MaterialDialog.progress() =
    customView(R.layout.progress_dialog_layout, scrollable = false).cornerRadius(4f)


fun MaterialDialog.changeTitle(title: String) = title(text = title)


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