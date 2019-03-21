package com.yzq.common.extend

import android.widget.ProgressBar
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.yzq.common.R


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