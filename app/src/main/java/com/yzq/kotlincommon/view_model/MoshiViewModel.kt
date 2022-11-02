package com.yzq.kotlincommon.view_model

import com.blankj.utilcode.util.LogUtils
import com.yzq.base.utils.MoshiUtils
import com.yzq.base.view_model.BaseViewModel
import com.yzq.common.api.BaseResp
import com.yzq.common.data.moshi.User
import com.yzq.common.ext.dataConvert
import com.yzq.common.net.RetrofitFactory
import com.yzq.common.net.api.ApiService
import com.yzq.common.net.constants.ResponseCode
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MoshiViewModel : BaseViewModel() {

    private lateinit var jsonStr: String

    fun serialize() {
        /*生成数据 */

        launchLoadingDialog {
            val userList = mutableListOf<User>()
            (1..3).forEach {
                userList.add(User("喻志强${it}", it, arrayListOf(User.Hobby("type${it}", "爱好${it}"))))
            }

            val baseResp = BaseResp<List<User>>(ResponseCode.SUCCESS, userList, "ok")
            jsonStr = MoshiUtils.toJson(baseResp)
            LogUtils.i(jsonStr)
        }
    }

    fun deserialize() {
        launchLoadingDialog {

            if (jsonStr.isNotEmpty()) {
                val genericType = MoshiUtils.getGenericType<BaseResp<List<User>>>()
                LogUtils.i("genericType==========:$genericType")
                val userList = MoshiUtils.fromJson<BaseResp<List<User>>>(jsonStr).dataConvert()
                userList?.forEach {
                    LogUtils.i(MoshiUtils.toJson(it, "  "))
                }

            }
        }
    }

    fun requestData() {


        launchSupervisor {
            launch {

                val userList =
                    RetrofitFactory.instance.getService(ApiService::class.java).listLocalUser()
                        .dataConvert()
                userList?.forEach {
                    LogUtils.i("${it.name}--${it.age}")
                }
            }

            launch {
                delay(4000)
                val userInfo =
                    RetrofitFactory.instance.getService(ApiService::class.java).userInfo()
                LogUtils.i("userInfo:$userInfo")
            }


        }

    }
}