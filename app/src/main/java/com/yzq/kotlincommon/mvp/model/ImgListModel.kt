package com.yzq.kotlincommon.mvp.model

import com.blankj.utilcode.util.LogUtils
import com.yzq.common.net.GsonConvert
import com.yzq.kotlincommon.data.BaiDuImgBean
import com.yzq.kotlincommon.net.UrlConstants
import io.reactivex.Single
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import java.io.IOException
import javax.inject.Inject

class ImgListModel @Inject constructor() {
    fun getImgs(currentPage: Int, pageSize: Int): Single<BaiDuImgBean> {


        return Single.create { singleEmitter ->
            val httpClient = OkHttpClient()


            val httpUrl = UrlConstants.BAIDU_IMG.toHttpUrl()
                    .newBuilder()
                    .addQueryParameter("pn", currentPage.toString())
                    .addQueryParameter("rn", pageSize.toString())
                    .addQueryParameter("tn", "baiduimage")
                    .addQueryParameter("word", "宠物")
                    .build()
            val request = Request.Builder().url(httpUrl).build()

            httpClient.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {

                    if (response.isSuccessful) {

                        val data = response.body!!.string()
                        LogUtils.i("请求的数据：${data}")
                        try {
                            val baiDuImgBean = GsonConvert.fromJson(data, BaiDuImgBean::class.java)
                            singleEmitter.onSuccess(baiDuImgBean)
                        } catch (e: Exception) {
                            singleEmitter.onError(Throwable(message = "数据解析错误"))
                        } finally {

                        }

                    }

                }

                override fun onFailure(call: Call, e: IOException) {
                    singleEmitter.onError(e)

                }

            })


        }


    }


}