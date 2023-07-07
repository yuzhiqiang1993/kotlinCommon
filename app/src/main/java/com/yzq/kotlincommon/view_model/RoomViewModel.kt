package com.yzq.kotlincommon.view_model

import androidx.lifecycle.viewModelScope
import com.yzq.base.view_model.BaseViewModel
import com.yzq.coroutine.safety_coroutine.launchSafety
import com.yzq.logger.LogCat
import com.yzq.storage.db.User
import com.yzq.storage.db.UserDao
import com.yzq.storage.db.UserDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random
import kotlin.random.nextInt

class RoomViewModel : BaseViewModel() {

    val users by lazy { userDao.getAllUsers() }
    private var userDao: UserDao = UserDataBase.instance.userDao()

    /*增*/
    fun insertUser() {

        viewModelScope.launchSafety {

            val userList = arrayListOf<User>()
            for (i in 0..5) {

                val randomName = getRandomStr()
                val user = User(
                    name = randomName,
                    age = Random.nextInt(1..100),
                    idCardNum = Random.nextInt(100000000, 999999999).toString(),
                    phone = Random.nextLong(10000000000, 99999999999).toString()

                )

                userList.add(user)
            }

            withContext(Dispatchers.IO) {
                userDao.insertUser(userList)
            }

            LogCat.i("插入成功")
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
    fun updateUser(id: Int, name: String, age: Int) {

        viewModelScope.launch {

            withContext(Dispatchers.IO) {
                val user = User(id, name, age)
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
