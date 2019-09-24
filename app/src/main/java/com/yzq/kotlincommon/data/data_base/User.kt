package com.yzq.kotlincommon.data.data_base

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class User(

    @PrimaryKey(autoGenerate = true)
    var id: Int=0,
    var name: String
)