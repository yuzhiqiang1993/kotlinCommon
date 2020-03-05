package com.yzq.common.data.data_base

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface UserDao {


    @Query("select * from user order by id desc")
    fun getAllUsers(): LiveData<MutableList<User>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(vararg user: User)

    @Delete
    fun deleteUser(vararg user: User)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(vararg user: User)


}