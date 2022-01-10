package com.yzq.lib_base.singletion

/**
 * @description: 双重检测懒汉式创建单例(可传参)的模板代码，具体使用看PersonManager
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2022/1/5
 * @time   : 10:52 上午
 */

abstract class BaseDoubleCheckSingleton<in P, out T> {

    @Volatile
    private var instance: T? = null

    /*普通写法*/
    protected abstract fun createFun(p: P): T

    /*高阶函数写法 就是作为参数或者返回值的函数*/
    protected abstract val create: (P) -> T

    fun getInstance(param: P): T = instance ?: synchronized(this) {
        instance ?: create(param).also { instance = it }
    }
}