package com.yzq.kotlincommon.ui.activity

import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.therouter.router.Route
import com.yzq.base.extend.initToolbar
import com.yzq.base.extend.setOnThrottleTimeClick
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewbind
import com.yzq.common.api.ApiResult
import com.yzq.common.api.onSuccess
import com.yzq.common.constants.RoutePath
import com.yzq.common.ext.apiCall
import com.yzq.common.ext.baseRespApiCall
import com.yzq.common.ext.dataConvert
import com.yzq.common.net.RetrofitFactory
import com.yzq.common.net.api.ApiService
import com.yzq.coroutine.safety_coroutine.launchSafety
import com.yzq.coroutine.safety_coroutine.lifeScope
import com.yzq.coroutine.safety_coroutine.scope.LifeSafetyScope
import com.yzq.kotlincommon.databinding.ActivityApiCallBinding
import com.yzq.kotlincommon.view_model.ApiCallViewModel
import com.yzq.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

/**
 * @description 网络请求的示例
 * @author yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date 2022/11/2
 * @time 15:39
 */

@Route(path = RoutePath.Main.API_CALL)
class ApiCallActivity : BaseActivity() {

    private val binding by viewbind(ActivityApiCallBinding::inflate)

    private val vm: ApiCallViewModel by viewModels()

    override fun initWidget() {

        binding.run {

            initToolbar(includedToolbar.toolbar, "接口请求")

            btnApiCall.setOnThrottleTimeClick {
                requestByApiCall()
            }

            btnLifeScope.setOnThrottleTimeClick {
                requestByLifeScope()
            }
            btnLaunchSafety.setOnThrottleTimeClick {
                requestByLaunchSafety()
            }

            btnConcurrent.setOnThrottleTimeClick {
                concurrentRequest()
            }

            btnViewmodel.setOnThrottleTimeClick {
                vm.requestData()
            }
        }
    }

    private fun requestByLaunchSafety() {

        lifecycleScope.launchSafety {
            Logger.i("准备请求")
            delay(2000)
            val localUserList =
                RetrofitFactory.instance.getService(ApiService::class.java).listLocalUser()
                    .dataConvert()
            Logger.i("localUserList:$localUserList")
        }.catch {
            Logger.i("异常了:$it")
            it.printStackTrace()
        }.invokeOnCompletion {
            Logger.i("结束了")
            if (it != null) {
                Logger.i("有异常:$it")
            }
        }

    }

    private fun concurrentRequest() {


        LifeSafetyScope(this, Lifecycle.Event.ON_DESTROY, Dispatchers.IO).launch {

        }.catch {

        }.finally {

        }

        /**
         *  并发请求
         */
        lifecycleScope.launchSafety {
            /**
             * 如果代码块没有被supervisorScope包裹，那么catch只会被调用一次，也就是首次出现异常的时候，其他子协程会受到影响被取消，并且finall会中会携带首次异常的信息
             *
             * 如果代码块被supervisorScope包裹，就是里面的子协程互不影响，每个子协程出现异常都会调用catch，且finally中不会携带异常信息
             */
            supervisorScope {
                launch {
                    RetrofitFactory.instance.getService(ApiService::class.java).listLocalUser()
                        .dataConvert()
                }
                launch {
                    delay(4000)
                    RetrofitFactory.instance.getService(ApiService::class.java).geocoder()
                }
            }
        }.catch {

            Logger.i("异常了:$it")
            it.printStackTrace()
        }.invokeOnCompletion {
            Logger.i("结束了")
            if (it != null) {
                Logger.i("有异常:$it")
            }
        }
    }

    private fun requestByApiCall() {

        /*使用apicall时可以直接使用官方提供的 lifecycleScope，具备取消功能*/
        lifecycleScope.launch {
            Logger.i("准备请求")
            /*响应数据为BaseResp类型的请求*/
            val baseRespApiCall = baseRespApiCall {
                RetrofitFactory.instance.getService(ApiService::class.java).listLocalUser()
            }
            baseRespApiCall.onSuccess {
                it?.forEach {
                    Logger.i("it:$it")
                }
            }

            /*响应数据为Response类型的请求*/
            val apiResult = apiCall {
                RetrofitFactory.instance.getService(ApiService::class.java).geocoder()
            }

            when (apiResult) {
                is ApiResult.Error -> {
                    Logger.i("onError--> code:${apiResult.code},msg:${apiResult.message}")
                }

                is ApiResult.Exception -> {
                    Logger.i("异常了:${apiResult.t}")
                    apiResult.t.printStackTrace()
                }

                is ApiResult.Success -> {
                    Logger.i("onSuccess--> ${apiResult.data}")
                }
            }

//            apiResult.onSuccess {
//                Logger.i("onSuccess--> ${it}")
//            }
//            apiResult.onError { code, message ->
//                Logger.i("onError--> code:${code},msg:${message}")
//            }
//            apiResult.onException {
//                it.printStackTrace()
//                Logger.i("onException:$it")
//            }
        }
    }

    private fun requestByLifeScope() {
        /**
         * 请求方式二
         * lifeScope请求，lifeScope对异常也做了处理
         */


        lifeScope {
            Logger.i("准备请求")
            delay(2000)
            val localUserList =
                RetrofitFactory.instance.getService(ApiService::class.java).listLocalUser()
                    .dataConvert()
            Logger.i("localUserList:$localUserList")
        }.catch {
            Logger.i("异常了:$it")
            it.printStackTrace()
        }.finally {
            if (it != null) {
                it.printStackTrace()
                Logger.i("结束了,但是有异常，$it")
            } else {
                Logger.i("正常结束了")
            }
        }
    }

    override fun observeViewModel() {
        vm.run {
        }
    }
}
