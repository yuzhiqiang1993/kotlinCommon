package com.yzq.data.task

data class TaskBean(var name: String, var type: String, override var itemHover: Boolean = false) :
    com.drake.brv.item.ItemHover
