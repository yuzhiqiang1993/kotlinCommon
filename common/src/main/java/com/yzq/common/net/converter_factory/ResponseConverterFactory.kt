package com.yzq.common.net.converter_factory

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import okhttp3.ResponseBody
import retrofit2.Converter


/**
 * @description: 对响应数据进行拦截处理
 * @author : yzq
 * @date   : 2019/3/16
 * @time   : 14:52
 *
 */

class ResponseConverterFactory<T>(val gson: Gson, val adapter: TypeAdapter<T>) : Converter<ResponseBody, T> {

    override fun convert(value: ResponseBody): T? {

        val jsonReader = gson.newJsonReader(value.charStream())
        val result = adapter.read(jsonReader)

      //  LogUtils.i("返回的结果：${result}")

        /*这里进行解密操作*/
        return result

    }
}