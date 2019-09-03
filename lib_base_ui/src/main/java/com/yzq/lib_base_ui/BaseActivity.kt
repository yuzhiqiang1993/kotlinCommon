package com.yzq.lib_base_ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yzq.lib_eventbus.EventBusUtil
import com.yzq.lib_eventbus.EventMsg
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * @Description: Activity基类
 * @author : yzq
 * @date   : 2018/7/2
 * @time   : 13:55
 *
 */

abstract class BaseActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*防止首次安装点击home键重新实例化*/
        if (!this.isTaskRoot) {
            if (intent != null) {
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN == intent.action) {
                    finish()
                    return
                }
            }
        }


        EventBusUtil.register(this)

        initArgs(intent.extras)

        setContentView(getContentLayoutId())
        initViewModel()
        initWidget()
        initData()

    }


    /**
     * 初始化参数
     *
     * @param extras  传递的参数对象
     */
    protected open fun initArgs(extras: Bundle?) {

    }


    protected open fun initViewModel() {}


    /**
     * 获取要加载的布局文件ID
     *
     */
    protected abstract fun getContentLayoutId(): Int


    /**
     * 初始化控件
     *
     */
    protected open fun initWidget() {
    }

    /**
     * 处理Eventbus
     *
     * @param eventMsg  传递的消息
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onEventMainThread(eventMsg: EventMsg) {


    }

    /**
     * 初始化数据
     *
     */
    protected open fun initData() {


    }


    override fun onDestroy() {
        super.onDestroy()
        EventBusUtil.unregister(this)
    }


}

