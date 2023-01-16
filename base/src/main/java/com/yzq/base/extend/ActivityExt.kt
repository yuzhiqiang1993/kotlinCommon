package com.yzq.base.extend

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.yzq.statusbar.immersive
import com.yzq.statusbar.statusPadding

/**
 * 初始化toolbar
 * @param toolbar
 * @param title
 * @param displayHome
 */
fun AppCompatActivity.initToolbar(
    toolbar: Toolbar,
    title: String,
    displayHome: Boolean = true,
) {
    toolbar.title = title
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(displayHome)
    /*沉浸式*/
    immersive()
    toolbar.statusPadding()
}

