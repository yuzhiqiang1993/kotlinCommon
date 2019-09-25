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

    var updatePosition = MutableLiveData<Int>()


    init {
        userDao = UserDataBase.instance.userDao()
    }


    /*查*/
    fun loadData() {


        viewModelScope.launch {

            val userList = withContext(Dispatchers.IO) {
                userDao.getAllUsers()
            }

            users.value = userList


        }


    }

    /*增*/
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


    /*删*/
    fun deleteUser(user: User) {
        viewModelScope.launch {

            withContext(Dispatchers.IO) {

                userDao.deleteUser(user)

            }

            loadData()
        }
    }


    /*改*/
    fun updateUser(user: User, position: Int) {

        viewModelScope.launch {

            withContext(Dispatchers.IO) {
                userDao.updateUser(user)
            }

            updatePosition.value = position
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