package com.yzq.kotlincommon.mvvm.view_model

import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import com.yzq.common.data.data_base.User
import com.yzq.common.data.data_base.UserDao
import com.yzq.common.data.data_base.UserDataBase

import com.yzq.lib_base.view_model.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RoomViewModel : BaseViewModel() {


    val users by lazy { userDao.getAllUsers() }
    private var userDao: UserDao = UserDataBase.instance.userDao()


    /*增*/
    fun insertUser() {


        viewModelScope.launch {

            withContext(Dispatchers.IO) {
                val randomName = getRandomStr()

                val user = User(name = randomName)
                userDao.insertUser(user)
            }

            LogUtils.i("插入成功")


        }


    }


    /*删*/
    fun deleteUser(user: User) {
        viewModelScope.launch {

            withContext(Dispatchers.IO) {

                userDao.deleteUser(user)

            }


        }
    }


    /*改*/
    fun updateUser(id: Int, name: String) {


        viewModelScope.launch {

            withContext(Dispatchers.IO) {
                val user = User(id, name)
                userDao.updateUser(user)
            }

        }

    }

    private fun getRandomStr(): String {
        val str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        val random = java.util.Random()
        val stringBuilder = StringBuilder()
        for (i in 0 until 10) {
            val number = random.nextInt(str.length)
            stringBuilder.append(str[number])
        }
        return stringBuilder.toString()
    }


}