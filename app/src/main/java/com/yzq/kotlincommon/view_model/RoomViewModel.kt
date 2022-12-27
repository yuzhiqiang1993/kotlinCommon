package com.yzq.kotlincommon.view_model

import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import com.yzq.base.view_model.BaseViewModel
import com.yzq.common.data.data_base.User
import com.yzq.common.data.data_base.UserDao
import com.yzq.common.data.data_base.UserDataBase
import com.yzq.coroutine.scope.launchSafety
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoomViewModel : BaseViewModel() {

    val users by lazy { userDao.getAllUsers() }
    private var userDao: UserDao = UserDataBase.instance.userDao()

    /*增*/
    fun insertUser() {

        viewModelScope.launchSafety {

            val userList = arrayListOf<User>()
            for (i in 0..5) {

                val randomName = getRandomStr()
                val user = User(name = randomName)

                userList.add(user)
            }

            withContext(Dispatchers.IO) {
                userDao.insertUser(userList)
            }

            LogUtils.i("插入成功")
        }
    }

    /*删*/
    fun deleteUser(user: User) {
        viewModelScope.launchSafety {

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

    fun clearUser() {

        viewModelScope.launchSafety {
            withContext(Dispatchers.IO) {

                userDao.clearUser()
            }
        }
    }
}
