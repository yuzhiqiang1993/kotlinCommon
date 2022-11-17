package com.yzq.common.data.task

import com.drake.brv.item.ItemHover

data class TaskBean(var name: String, var type: String, override var itemHover: Boolean = false) :
    ItemHover
