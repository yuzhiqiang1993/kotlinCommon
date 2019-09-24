package com.yzq.kotlincommon.mvvm.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import com.yzq.kotlincommon.data.data_base.User
import com.yzq.kotlincommon.data.data_base.UserDao
import com.yzq.kotlincommon.data.data_base.UserDataBase
import com.yzq.lib_base.view_model.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RoomViewModel : BaseViewModel() {


    private var userDao: UserDao


    var users = MutableLiveData<List<User>>()


    init {
        userDao = UserDataBase.instance.userDao()
    }

    fun loadData() {


        viewModelScope.launch {

            val userList = withContext(Dispatchers.IO) {
                userDao.getAllUsers()
            }


            users.value = userList

            LogUtils.i("数据库数据：${userList.toList()}")
        }


    }


    fun insertUser() {

        val randomName = getRandomStr()

        val user = User(name = randomName)

        viewModelScope.launch {

            withContext(Dispatchers.IO) {
                userDao.insertUser(user)
            }

            LogUtils.i("插入成功")

            loadData()
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