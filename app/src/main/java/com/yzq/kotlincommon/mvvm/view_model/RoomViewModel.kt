package com.yzq.kotlincommon.mvvm.view_model

import androidx.lifecycle.LiveData
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


    var users: LiveData<List<User>>

    private var userDao: UserDao


    var updatePosition = MutableLiveData<Int>()


    init {
        userDao = UserDataBase.instance.userDao()

        /*查  由于getAllUsers返回类型为LiveData类型  默认就异步的 所以无需使用协程 */

        users = userDao.getAllUsers()
    }


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
    fun updateUser(user: User, position: Int) {

        viewModelScope.launch {

            withContext(Dispatchers.IO) {
                userDao.updateUser(user)
            }

            updatePosition.value = position
        }

    }

    /*改*/
    fun updateUser(id: Int, name: String) {

        viewModelScope.launch {

            withContext(Dispatchers.IO) {
                val user = User(id, name)
                userDao.updateUser(user)
            }

//            updatePosition.value = position
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