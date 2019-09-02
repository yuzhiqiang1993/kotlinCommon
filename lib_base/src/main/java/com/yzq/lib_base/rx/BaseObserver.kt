package com.yzq.lib_base.rx

import android.text.TextUtils
import com.blankj.utilcode.util.NetworkUtils
import com.google.gson.JsonParseException
import com.yzq.lib_base.constants.BaseConstants
import com.yzq.lib_base.view_model.BaseViewModel
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

abstract class BaseObserver<T>(private val vm: BaseViewModel) : SingleObserver<T> {


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
            val msg = if (TextUtils.isEmpty(e.message)) BaseConstants.UNKONW_ERROR else e.message!!
            vm.showError(msg)
        }
    }


}