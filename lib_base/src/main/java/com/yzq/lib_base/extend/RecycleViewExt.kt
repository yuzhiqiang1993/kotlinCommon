package com.yzq.lib_base.extend

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yzq.lib_widget.ItemDecoration


/*
* 列表初始化
* */
fun RecyclerView.init(
    layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context),
    needItemDecoration: Boolean = true
) {

    this.layoutManager = layoutManager
    if (needItemDecoration) {
        this.addItemDecoration(ItemDecoration.baseItemDecoration(context = context))
    }
}