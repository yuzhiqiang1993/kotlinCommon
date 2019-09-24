package com.yzq.kotlincommon.data.data_base

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface UserDao {

    @Query("select * from user")
    fun getAllUsers(): List<User>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)


}