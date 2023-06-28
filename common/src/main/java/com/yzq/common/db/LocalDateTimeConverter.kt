package com.yzq.common.db

import androidx.room.TypeConverter
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter


/**
 * @description 日期转换器
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 */

class LocalDateTimeConverter {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    @TypeConverter
    fun fromString(value: String): LocalDateTime {
        return LocalDateTime.parse(value, formatter)
    }

    @TypeConverter
    fun toString(dateTime: LocalDateTime): String {
        return dateTime.format(formatter)
    }

//    @TypeConverter
//    fun fromTimestamp(timestamp: Long): LocalDateTime {
//        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())
//    }
//
//    @TypeConverter
//    fun toTimestamp(dateTime: LocalDateTime): Long {
//        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
//    }
}
