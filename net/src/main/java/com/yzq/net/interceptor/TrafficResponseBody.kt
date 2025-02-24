package com.yzq.net.interceptor

import android.util.Log
import com.yzq.logger.Logger
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.Buffer
import okio.BufferedSource
import okio.ForwardingSource
import okio.IOException
import okio.Source
import okio.buffer


/**
 * @description: 自定义包装的响应，主要用于统计本次响应消耗的流量信息
 * @author : yuzhiqiang
 */

class TrafficResponseBody(
    private val url: String,
    private val responseBody: ResponseBody,
) : ResponseBody() {

    private var totalBytesRead: Long = 0//本次响应消耗的流量

    companion object {
        const val TAG = "ProgressResponseBody"
    }

    private var mBufferedSource: BufferedSource? = null

    override fun contentType(): MediaType? {
        return responseBody.contentType()
    }

    override fun contentLength(): Long {
        return responseBody.contentLength()
    }

    override fun source(): BufferedSource {
        if (mBufferedSource == null) {

            mBufferedSource = source(responseBody.source()).buffer()
        }
        return mBufferedSource!!
    }

    private fun source(source: Source): Source {
        Logger.it(TAG, "source")
        return object : ForwardingSource(source) {
            @Throws(IOException::class)
            override fun read(sink: Buffer, byteCount: Long): Long {
                var bytesRead: Long = -1L
                try {
                    //这里的bytesRead实际上是从缓冲区域读取的字节大小，并非实际消耗的流量
                    bytesRead = super.read(sink, byteCount)
                    // 读取流时记录日志
                    if (bytesRead != -1L) {
                        totalBytesRead += bytesRead
                    }

                } catch (e: IOException) {
                    e.printStackTrace()
                    throw e
                }
                return bytesRead
            }

            override fun close() {
                super.close()
                Log.i(TAG, "close，请求结束，消耗流量:${totalBytesRead}")
            }
        }
    }


}