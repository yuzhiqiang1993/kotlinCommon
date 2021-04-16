package com.yzq.common.data.data_base

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.room.*


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