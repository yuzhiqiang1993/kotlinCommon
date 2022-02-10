package com.yzq.lib_base.singletion


/**
 * @description: 需要传参双重判空单例
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2022/1/5
 * @time   : 10:34 上午
 */

class UserManager private constructor(name: String) {

    companion object {

        @Volatile private var INSTANCE: UserManager? = null

        fun getInstance(name: String) {

            /*这种写法其实就是下面那种写法*/
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserManager(name).also { INSTANCE = it }
            }

//            if (INSTANCE == null) {
//                synchronized(this) {
//                    if (INSTANCE == null) {
//                        INSTANCE = UserManager(name = name)
//                    }
//                }
//            }

        }

    }

}

fun main() {
    UserManager.getInstance("xeon")
}