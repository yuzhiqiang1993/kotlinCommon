package com.yzq.lib_base.rx

import android.text.TextUtils
import com.blankj.utilcode.util.NetworkUtils
import com.google.gson.JsonParseException
import com.yzq.lib_constants.BaseConstants
import com.yzq.lib_base.view_model.BaseViewModel
import io.reactivex.SingleObserver
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
abstract class BaseProgressObserver<T>(
    private val vm: BaseViewModel,
    private val title: String,
    private val url: String
) : SingleObserver<T>, ProgressListener {

    override fun onSubscribe(d: Disposable) {

        if (NetworkUtils.isConnected()) {
            ProgressManager.getInstance().addRequestListener(url, this)

            vm.showProgressDialog(title)
        } else {
            vm.showNoNet()
            d.dispose()

        }
    }

    override fun onError(e: Throwable) {

        e.printStackTrace()
        vm.dismissProgressDialog()

        if (e is JSONException || e is JsonParseException) {
            vm.showErrorDialog(com.yzq.lib_constants.BaseConstants.PARSE_DATA_ERROE)
        } else if (e is SocketTimeoutException) {
            vm.showErrorDialog(com.yzq.lib_constants.BaseConstants.SERVER_TIMEOUT)
        } else {
            val msg = if (TextUtils.isEmpty(e.message)) com.yzq.lib_constants.BaseConstants.UNKONW_ERROR else e.message!!
            vm.showErrorDialog(msg)

        }

    }


    override fun onSuccess(t: T) {
        vm.dismissProgressDialog()

    }

    override fun onProgress(progressInfo: ProgressInfo?) {

        vm.changeProgress(progressInfo!!.percent)

    }

    override fun onError(id: Long, e: java.lang.Exception?) {

        vm.dismissProgressDialog()

    }
}