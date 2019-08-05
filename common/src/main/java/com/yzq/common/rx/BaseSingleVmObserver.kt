package com.yzq.common.rx

import android.text.TextUtils
import com.blankj.utilcode.util.NetworkUtils
import com.google.gson.JsonParseException
import com.yzq.common.constants.BaseConstants
import com.yzq.common.mvvm.BaseViewModel
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

abstract class BaseSingleVmObserver<T>(private val vm: BaseViewModel) : SingleObserver<T> {


    override fun onSubscribe(d: Disposable) {

        /*没有网络给出提示并取消发送*/
        if (!NetworkUtils.isConnected()) {
            vm.showNoNet()
            d.dispose()
        }
    }


    override fun onError(e: Throwable) {

        e.printStackTrace()

        if (e is JSONException || e is JsonParseException) {
            vm.showError(BaseConstants.PARSE_DATA_ERROE)
        } else if (e is SocketTimeoutException) {
            vm.showError(BaseConstants.SERVER_TIMEOUT)
        } else {
            var msg = if (TextUtils.isEmpty(e.message)) "未知错误" else e.message!!
            vm.showError(msg)
        }
    }


}