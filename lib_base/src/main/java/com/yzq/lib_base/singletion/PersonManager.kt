package com.yzq.lib_base.singletion

class PersonManager private constructor(name: String) {

    companion object : BaseDoubleCheckSingleton<String, PersonManager>() {
        override fun create(param: String): PersonManager = PersonManager(name = param)
    }

}

fun main() {
    PersonManager.getInstance("test")
}