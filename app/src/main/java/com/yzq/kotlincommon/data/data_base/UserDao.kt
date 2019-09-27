package com.yzq.kotlincommon.data.data_base

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface UserDao {


    @Query("select * from user order by id desc")
    fun getAllUsers(): LiveData<List<User>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(vararg user: User)


    @Delete
    fun deleteUser(vararg user: User)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(vararg user: User)


}