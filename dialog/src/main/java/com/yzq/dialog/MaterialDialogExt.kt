package com.yzq.dialog

import android.widget.ProgressBar
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView

/**
 * @description: 扩展的进度框
 * @author : yzq
 * @date : 2019/3/21
 * @time : 11:22
 *
 */

fun MaterialDialog.progress() =
    customView(R.layout.layout_progress_dialog, scrollable = false).cornerRadius(4f)

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
