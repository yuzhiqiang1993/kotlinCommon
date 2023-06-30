package com.yzq.storage.db

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


/**
 * @description 用户表操作类，DAO：Data Access Object,就是用来操作表的对象
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 */

@Dao
interface UserDao {

    @Query("select * from user order by id ")
    @WorkerThread
    fun getAllUsers(): LiveData<MutableList<User>>

    //    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    fun insertUser(vararg user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @WorkerThread
    fun insertUser(user: List<User>)

    @Delete
    @WorkerThread
    fun deleteUser(vararg user: User)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    @WorkerThread
    fun updateUser(vararg user: User)

    @Query("delete from user")
    @WorkerThread
    fun clearUser()

}