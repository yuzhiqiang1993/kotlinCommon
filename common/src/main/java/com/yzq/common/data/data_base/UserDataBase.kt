package com.yzq.common.data.data_base

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yzq.lib_application.AppContext


@Database(entities = [User::class], version = 1)
abstract class UserDataBase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

        val instance: UserDataBase by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {

            Room.databaseBuilder(AppContext, UserDataBase::class.java, "user_database").build()
        }


    }

}