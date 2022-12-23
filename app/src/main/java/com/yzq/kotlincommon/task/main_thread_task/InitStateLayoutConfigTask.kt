package com.yzq.kotlincommon.task.main_thread_task

import androidx.appcompat.widget.AppCompatTextView
import com.blankj.utilcode.util.LogUtils
import com.drake.statelayout.StateConfig
import com.yzq.base.startup.base.MainThreadTask
import com.yzq.common.api.ApiResult
import com.yzq.kotlincommon.R

/**
 * @description 初始化状态布局
 * @author yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date 2022/11/18
 * @time 14:40
 */
class InitStateLayoutConfigTask : MainThreadTask() {
    override fun taskRun() {
        StateConfig
            .apply {
                emptyLayout = R.layout.layout_empty
                errorLayout = R.layout.layout_error
                loadingLayout = R.layout.layout_loading
                isNetworkingRetry = false//不检查网络
                /* 参与重试的id，会回调onRefresh*/
                setRetryIds(R.id.tv_msg, R.id.iv)

                onError { tag ->
                    LogUtils.i("onError--->${tag}")
                    val msgTv = findViewById<AppCompatTextView>(R.id.tv_msg)
                    when (tag) {
                        is Throwable -> {
                            msgTv?.setText(tag.message)
                        }
                        is ApiResult.Error -> {
                            msgTv?.setText(
                                buildString {
                                    append(tag.code)
                                    append("--")
                                    append(tag.message)
                                }
                            )
                        }
                        is String -> {
                            msgTv?.setText(tag)
                        }
                    }
                }
            }
    }
}
