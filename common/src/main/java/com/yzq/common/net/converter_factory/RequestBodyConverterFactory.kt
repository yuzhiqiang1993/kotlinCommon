package com.yzq.common.net.converter_factory

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Converter


/**
 * @description: 对请求体进行拦截处理，
 * 会对使用 @Body、@Part、@PartMap
 * 进行注解的数据进行处理
 * @author : yzq
 * @date   : 2019/3/16
 * @time   : 14:51
 *
 */

class RequestBodyConverterFactory<T>(val gson: Gson, val adapter: TypeAdapter<T>) : Converter<T, RequestBody> {


    val MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8")


    override fun convert(value: T): RequestBody? {


        val jsonData = adapter.toJson(value)

        /*这里对请求的数据进行加密*/

        print("RequestBodyConverterFactory convert " + jsonData)


        return RequestBody.create(MEDIA_TYPE, jsonData)

    }


}