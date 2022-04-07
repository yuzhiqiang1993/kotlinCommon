package com.yzq.kotlincommon.mvvm.view_model

import com.blankj.utilcode.util.LogUtils
import com.yzq.common.data.BaseResp
import com.yzq.common.data.moshi.User
import com.yzq.common.ext.*
import com.yzq.common.net.RetrofitFactory
import com.yzq.common.net.api.ApiService
import com.yzq.common.net.constants.ResponseCode
import com.yzq.lib_base.utils.MoshiUtils
import com.yzq.lib_base.view_model.BaseViewModel

class MoshiViewModel : BaseViewModel() {

    private lateinit var jsonStr: String

    fun serialize() {
        /*生成数据 */

        launchWithSupervisor {
            val userList = mutableListOf<User>()
            (1..3).forEach {
                userList.add(User("喻志强${it}", it, arrayListOf(User.Hobby("type${it}", "爱好${it}"))))
            }

            val baseResp = BaseResp<List<User>>(ResponseCode.SUCCESS, userList, "ok")
            jsonStr = baseResp.toJson(" ")
            LogUtils.i(jsonStr)
        }
    }

    fun deserialize() {
        launchWithSupervisor {

            if (jsonStr.isNotEmpty()) {
                val userList = jsonStr.toBaseRespList<User>().dataConvert()
                userList.forEach {
                    LogUtils.i(MoshiUtils.toJson(it, "  "))
                }

            }
        }
    }

    fun requestData() {
        launchLoading {

            val userList = RetrofitFactory.instance.getService(ApiService::class.java).listLocalUser().dataConvert()
            userList.forEach {
                LogUtils.i("${it.name}--${it.age}")
            }
        }

    }
}