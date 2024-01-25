package com.yzq.react_native

import android.os.Bundle
import android.view.View
import com.facebook.react.ReactInstanceManager
import com.facebook.react.ReactRootView
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.logger.Logger


/**
 * @description 用于承载RN的Activity
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 */
abstract class BaseRnActivity : BaseActivity(), DefaultHardwareBackBtnHandler {

    protected var reactInstanceManager: ReactInstanceManager = createReactInstanceManager()


    private var rnRootView: ReactRootView? = null

    abstract fun getReactRootView(): ReactRootView

    abstract fun createReactInstanceManager(): ReactInstanceManager


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        rnRootView = getReactRootView()
        rnRootView?.id = View.NO_ID
    }


    override fun onPause() {
        super.onPause()
        reactInstanceManager.onHostPause(this)
    }

    override fun onResume() {
        super.onResume()
        reactInstanceManager.onHostResume(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.i("onDestroy")
        reactInstanceManager.onHostDestroy(this)
        reactInstanceManager.destroy()
        rnRootView?.unmountReactApplication()
        rnRootView = null
    }

    override fun invokeDefaultOnBackPressed() {
        super.onBackPressed()
    }

    override fun onBackPressed() {
        Logger.i("onBackPressed")
        super.onBackPressed()
        reactInstanceManager.onBackPressed()

    }

}