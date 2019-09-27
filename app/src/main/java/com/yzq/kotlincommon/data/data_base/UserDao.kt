package com.yzq.kotlincommon.data.data_base

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface UserDao {



    /*当返回值类型为 LiveData 时，默认就是异步的 我们无需使用suspend关键字修饰，去掉*/
    @Query("select * from user")
    fun getAllUsers(): LiveData<List<User>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: User)


    @Delete
    fun deleteUser(user: User)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(user: User)


}