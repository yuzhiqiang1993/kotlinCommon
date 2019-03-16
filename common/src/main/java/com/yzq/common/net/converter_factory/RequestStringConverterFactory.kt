package com.yzq.common.net.converter_factory

import com.google.gson.Gson
import com.google.gson.TypeAdapter

import retrofit2.Converter


 /**
 * @description: 对请求数据的拦截处理，
  * 会对使用 @Field、@FieldMap、@Header、@HeaderMap、@Path、@Query、@QueryMap
  * 进行注解的数据进行处理
 * @author : yzq
 * @date   : 2019/3/16
 * @time   : 15:14
 *
 */

class RequestStringConverterFactory<T>(val gson: Gson, val adapter: TypeAdapter<T>) : Converter<T, String> {
    override fun convert(value: T): String? {


        /*这里会将请求参数的值打印出来，有几个请求参数就会打印几次*/
        val requestData = value as String

        //LogUtils.i("RequestStringConverterFactory:$requestData")


        return requestData


    }
}