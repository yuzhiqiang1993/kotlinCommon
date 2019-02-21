package com.yzq.common.extend

import android.widget.ProgressBar
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.yzq.common.R


fun MaterialDialog.progress(): MaterialDialog {

    customView(R.layout.progress_dialog_layout, scrollable = false)
    return this
}

fun MaterialDialog.changeProgress(percent: Int): MaterialDialog {

    val progressBar = getCustomView().findViewById<ProgressBar>(R.id.progress_horizontal)

    val currentProgress = getCustomView().findViewById<TextView>(R.id.current_progress_tv)
    progressBar.progress = percent
    currentProgress.text = "$percent"


    return this


}