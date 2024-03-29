package com.yzq.storage.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
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
    @TypeConverters(value = [LocalDateTimeConverter::class])
    var insertTime: LocalDateTime = LocalDateTime.now(),
    @TypeConverters(value = [LocalDateTimeConverter::class])
    var updateTime: LocalDateTime = LocalDateTime.now(),
)