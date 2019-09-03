package com.yzq.lib_base.rx

import android.text.TextUtils
import com.blankj.utilcode.util.NetworkUtils
import com.google.gson.JsonParseException
import com.yzq.lib_base.view_model.BaseViewModel
import com.yzq.lib_constants.BaseConstants
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
abstract class BaseDialogObserver<T>(
    private val vm: BaseViewModel,
    private val content: String = BaseConstants.LOADING
) : SingleObserver<T> {


    override fun onSubscribe(d: Disposable) {
        if (NetworkUtils.isConnected()) {
            vm.showloadingDialog(content)

        } else {
            vm.showNoNet()
            d.dispose()
        }
    }


    override fun onError(e: Throwable) {
        vm.dismissLoadingDialog()
        e.printStackTrace()

        if (e is JSONException || e is JsonParseException) {
            vm.showErrorDialog(BaseConstants.PARSE_DATA_ERROE)
        } else if (e is SocketTimeoutException) {
            vm.showErrorDialog(BaseConstants.SERVER_TIMEOUT)
        } else {

            val msg = if (TextUtils.isEmpty(e.message)) "未知错误" else e.message!!
            vm.showErrorDialog(msg)

        }

    }


}