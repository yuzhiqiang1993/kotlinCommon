package com.yzq.storage.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDateTime


/**
 * @description 用户表
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 */


@Entity(tableName = "user")
data class User(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var age: Int,
    var idCardNum: String = "",
    var phone: String = "",
    var insertTime: LocalDateTime = LocalDateTime.now(),
    var updateTime: LocalDateTime = LocalDateTime.now(),
)