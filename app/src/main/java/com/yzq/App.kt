package com.yzq

import com.blankj.utilcode.util.DeviceUtils
import com.yzq.common.BaseApp
import com.yzq.common.net.RetrofitFactory

class App : BaseApp() {
    override fun onCreate() {
        super.onCreate()


        RetrofitFactory.setBaseUrl("http://192.168.1.201/api/")
        var headers = HashMap<String, String>()

        headers.put("111",DeviceUtils.getAndroidID())
        headers.put("222",DeviceUtils.getAndroidID())

        RetrofitFactory.setCommonHeaders(headers)
    }
}