package com.yzq.common.data.data_base

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class User(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String
)