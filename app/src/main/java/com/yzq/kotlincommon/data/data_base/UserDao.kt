package com.yzq.kotlincommon.data.data_base

import androidx.room.*


@Dao
interface UserDao {

    @Query("select * from user")
    fun getAllUsers(): List<User>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)


    @Delete
    suspend fun deleteUser(user: User)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUser(user: User)


}