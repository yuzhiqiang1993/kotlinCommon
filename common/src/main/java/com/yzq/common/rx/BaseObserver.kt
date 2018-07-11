package com.yzq.common.rx

import com.blankj.utilcode.util.NetworkUtils
import com.google.gson.JsonParseException
import com.yzq.common.constants.BaseContstants
import com.yzq.common.mvp.view.BaseView
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.json.JSONException
import java.net.SocketTimeoutException


/**
 * @description: 封装的observer基类
 * @author : yzq
 * @date   : 2018/7/9
 * @time   : 15:42
 *
 */

abstract class BaseObserver<T>(private val view: BaseView) : Observer<T> {


    override fun onSubscribe(d: Disposable) {

        /*没有网络给出提示并取消发送*/
        if (!NetworkUtils.isConnected()) {
            view.showNoNet()
            this.onError(kotlin.Throwable(BaseContstants.NO_NET))
            d.dispose()
        }

    }


    override fun onError(e: Throwable) {


        if (e is JSONException || e is JsonParseException) {
            view.showError(BaseContstants.PARSE_DATA_ERROE)
        } else if (e is SocketTimeoutException) {
            view.showError(BaseContstants.SERVER_TIMEOUT)
        } else {
            view.showError(e.localizedMessage)
        }
    }


    override fun onComplete() {


    }


}