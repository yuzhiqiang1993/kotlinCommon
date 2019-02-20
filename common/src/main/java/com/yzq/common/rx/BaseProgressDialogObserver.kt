package com.yzq.common.rx

import com.blankj.utilcode.util.NetworkUtils
import com.google.gson.JsonParseException
import com.yzq.common.mvp.view.BaseView
import com.yzq.common.constants.BaseContstants
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import me.jessyan.progressmanager.ProgressListener
import me.jessyan.progressmanager.ProgressManager
import me.jessyan.progressmanager.body.ProgressInfo
import org.json.JSONException
import java.net.SocketTimeoutException


/**
 * @description: 带进度的observer
 * @author : yzq
 * @date   : 2018/7/9
 * @time   : 16:23
 *
 */
abstract class BaseProgressDialogObserver<T>(private val view: BaseView, private val title: String,private val content: String="",private val url: String) : Observer<T>, ProgressListener {

    override fun onSubscribe(d: Disposable) {

        if (NetworkUtils.isConnected()) {
            ProgressManager.getInstance().addRequestListener(url, this)
            view.showProgressDialog(title, content)
        } else {
            onError(Exception(BaseContstants.NO_NET))
            d.dispose()
        }
    }

    override fun onError(e: Throwable) {

        view.dismissProgressDialog()

        if (e is JSONException || e is JsonParseException) {
            view.showErrorDialog(BaseContstants.PARSE_DATA_ERROE)
        } else if (e is SocketTimeoutException) {
            view.showErrorDialog(BaseContstants.SERVER_TIMEOUT)
        } else {
            view.showErrorDialog(e.localizedMessage)
        }

    }


    override fun onComplete() {
        view.dismissProgressDialog()
    }


    override fun onProgress(progressInfo: ProgressInfo?) {

        view.changeProgress(progressInfo!!.percent)

    }

    override fun onError(id: Long, e: java.lang.Exception?) {

        view.dismissProgressDialog()

    }
}