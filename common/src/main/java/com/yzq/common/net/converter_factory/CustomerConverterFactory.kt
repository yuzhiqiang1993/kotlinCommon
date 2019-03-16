package com.yzq.common.net.converter_factory

import com.blankj.utilcode.util.LogUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class CustomerConverterFactory(var gson: Gson) : Converter.Factory() {


    companion object {
        fun create(): CustomerConverterFactory {
            LogUtils.i("CustomerConverterFactory create")

            return CustomerConverterFactory(gson = Gson())
        }
    }


    override fun requestBodyConverter(type: Type, parameterAnnotations: Array<Annotation>,
                                      methodAnnotations: Array<Annotation>, retrofit: Retrofit): Converter<*, RequestBody>? {

        val adapter = gson.getAdapter(TypeToken.get(type))

        return RequestBodyConverterFactory(gson, adapter)
    }

    override fun responseBodyConverter(type: Type, annotations: Array<Annotation>, retrofit: Retrofit): Converter<ResponseBody, *>? {


        val adapter = gson.getAdapter(TypeToken.get(type))

        return ResponseConverterFactory(gson, adapter)
    }



    override fun stringConverter(type: Type, annotations: Array<Annotation>, retrofit: Retrofit): Converter<*, String>? {

        val adapter = gson.getAdapter(TypeToken.get(type))
       return RequestStringConverterFactory(gson, adapter)
    }


}