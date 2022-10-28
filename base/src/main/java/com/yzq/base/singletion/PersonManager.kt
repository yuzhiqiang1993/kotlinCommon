package com.yzq.base.singletion

class PersonManager private constructor(val name: String) {

    companion object : BaseDoubleCheckSingleton<String, PersonManager>() {

        /*
        * 高阶函数写法
        * 接收的类型是(String)->PersonManager
        * 这里把构造函数的引用传过去就好
        * */
        override val create = ::PersonManager

        /*普通写法*/
        override fun createFun(p: String) = PersonManager(p)

    }

}

fun main() {
    val instance = PersonManager.getInstance("test")
    println("instance.name = ${instance.name}")
}