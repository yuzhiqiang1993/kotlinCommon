package com.yzq.common.rx

import com.blankj.utilcode.util.NetworkUtils
import com.google.gson.JsonParseException
import com.yzq.common.constants.BaseContstants
import com.yzq.common.mvp.view.BaseView
import io.reactivex.Observer
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import org.json.JSONException
import java.net.SocketTimeoutException


/**
 * @description: 带弹窗的Singleobserver
 * @author : yzq
 * @date   : 2018/7/9
 * @time   : 16:00
 *
 */
abstract class BaseDialogSingleObserver<T>(private val view: BaseView, private val content: String = BaseContstants.LOADING, private val hideDialog: Boolean = true) : SingleObserver<T> {


    override fun onSubscribe(d: Disposable) {
        if (NetworkUtils.isConnected()) {
            view.showLoadingDialog(content)

        } else {
            view.showNoNet()
            d.dispose()
        }
    }


    override fun onError(e: Throwable) {
        view.dismissLoadingDialog()
        e.printStackTrace()

        if (e is JSONException || e is JsonParseException) {
            view.showErrorDialog(BaseContstants.PARSE_DATA_ERROE)
        } else if (e is SocketTimeoutException) {
            view.showErrorDialog(BaseContstants.SERVER_TIMEOUT)
        } else {
            view.showErrorDialog(e.localizedMessage)
        }

    }

    override fun onSuccess(t: T) {
        view.dismissLoadingDialog()
    }


}