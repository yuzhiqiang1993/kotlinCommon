package com.yzq.kotlincommon.mvp.model

import com.blankj.utilcode.util.LogUtils
import com.yzq.common.net.GsonConvert
import com.yzq.kotlincommon.data.BaiDuImgBean
import com.yzq.kotlincommon.net.UrlConstants
import io.reactivex.Observable
import okhttp3.*
import java.io.IOException
import javax.inject.Inject

class ImgListModel @Inject constructor() {
    fun getImgs(): Observable<BaiDuImgBean> {


        return Observable.create {
            val httpClient = OkHttpClient()
            val httpUrl = HttpUrl.parse(UrlConstants.BAIDU_IMG)!!.newBuilder().build()
            val request = Request.Builder().url(httpUrl).build()

            httpClient.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {

                    if (response.isSuccessful) {

                        val data = response.body()!!.string()
                        LogUtils.i("请求的数据：${data}")
                        val baiDuImgBean = GsonConvert.fromJson(data, BaiDuImgBean::class.java)
                        it.onNext(baiDuImgBean)
                        it.onComplete()
                    }

                }

                override fun onFailure(call: Call, e: IOException) {


                    it.onError(e)
                    it.onComplete()
                }

            })


        }


    }


}