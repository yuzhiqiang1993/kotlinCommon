package com.yzq.kotlincommon.view_model

import androidx.lifecycle.viewModelScope
import com.yzq.base.utils.MoshiUtils
import com.yzq.base.view_model.BaseViewModel
import com.yzq.common.api.BaseResp
import com.yzq.common.data.moshi.User
import com.yzq.common.ext.dataConvert
import com.yzq.common.net.RetrofitFactory
import com.yzq.common.net.api.ApiService
import com.yzq.common.net.constants.ResponseCode
import com.yzq.coroutine.ext.launchSafety
import com.yzq.logger.Logger
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class MoshiViewModel : BaseViewModel() {

    private lateinit var jsonStr: String

    fun serialize() {
        /*生成数据 */

        viewModelScope.launchSafety {
            val userList = mutableListOf<User>()
            for (it in 1..3) {
                userList.add(
                    User(
                        "喻志强$it",
                        it,
                        arrayListOf(User.Hobby("type$it", "爱好$it"))
                    )
                )
            }

            val baseResp = BaseResp<List<User>>(ResponseCode.SUCCESS, userList, "ok")
            jsonStr = MoshiUtils.toJson(baseResp) ?: ""
        }
    }

    fun deserialize() {
        viewModelScope.launchSafety {

            if (jsonStr.isNotEmpty()) {
                val genericType = MoshiUtils.getGenericType<BaseResp<List<User>>>()
                Logger.i("genericType==========:$genericType")
                val userList = MoshiUtils.fromJson<BaseResp<List<User>>>(jsonStr).dataConvert()
                userList?.forEach {
                    Logger.i(MoshiUtils.toJson(it, "  ") ?: "")
                }
            }
        }
    }

    fun requestData() {

        viewModelScope.launchSafety {
            supervisorScope {
                launch {

                    val userList =
                        RetrofitFactory.instance.getService(ApiService::class.java).listLocalUser()
                            .dataConvert()
                    userList?.forEach {
                        Logger.i("${it.name}--${it.age}")
                    }
                }

                launch {
                    delay(4000)
                    val userInfo =
                        RetrofitFactory.instance.getService(ApiService::class.java).userInfo()
                    Logger.i("userInfo:$userInfo")
                }
            }

        }
    }
}
