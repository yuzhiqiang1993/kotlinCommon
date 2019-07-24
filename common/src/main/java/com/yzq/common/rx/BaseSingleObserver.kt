package com.yzq.common.rx

import com.blankj.utilcode.util.NetworkUtils
import com.google.gson.JsonParseException
import com.yzq.common.constants.BaseContstants
import com.yzq.common.mvp.view.BaseView
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import org.json.JSONException
import java.net.SocketTimeoutException


/**
 * @description: 封装的SingleObserver基类
 * @author : yzq
 * @date   : 2018/7/9
 * @time   : 15:42
 *
 */

abstract class BaseSingleObserver<T>(private val view: BaseView) : SingleObserver<T> {


    override fun onSubscribe(d: Disposable) {

        /*没有网络给出提示并取消发送*/
        if (!NetworkUtils.isConnected()) {
            view.showNoNet()
            d.dispose()
        }
    }


    override fun onError(e: Throwable) {

        e.printStackTrace()

        if (e is JSONException || e is JsonParseException) {
            view.showError(BaseContstants.PARSE_DATA_ERROE)
        } else if (e is SocketTimeoutException) {
            view.showError(BaseContstants.SERVER_TIMEOUT)
        } else {
            view.showError(e.message)
        }
    }


}