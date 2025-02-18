package com.yzq.reactnative

import android.content.Context
import com.facebook.react.devsupport.interfaces.ErrorType
import com.facebook.react.devsupport.interfaces.RedBoxHandler
import com.facebook.react.devsupport.interfaces.StackFrame
import com.yzq.logger.Logger


/**
 * @description:  JS 异常回调处理实现，在这个实现中我们可以打印 JS 异常日志，上报错误
 * @author : yuzhiqiang
 */
class BaseJsExecptionHandler : RedBoxHandler {

    companion object {
        const val TAG = "BaseJsExecptionHandler"
    }

    override fun handleRedbox(title: String?, stack: Array<StackFrame>, errorType: ErrorType) {

        Logger.it(
            TAG,
            "handleRedbox: $title stack: ${stack.contentToString()} errorType: $errorType"
        )
    }

    override fun isReportEnabled(): Boolean {

        return true
    }

    override fun reportRedbox(
        context: Context,
        title: String,
        stack: Array<StackFrame>,
        sourceUrl: String,
        reportCompletedListener: RedBoxHandler.ReportCompletedListener
    ) {
        Logger.i("reportRedbox: $title stack: ${stack.contentToString()} sourceUrl: $sourceUrl")
    }
}