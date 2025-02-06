package com.yzq.kotlincommon.ui.activity

import com.facebook.hermes.reactexecutor.HermesExecutorFactory
import com.facebook.react.ReactInstanceManager
import com.facebook.react.common.LifecycleState
import com.facebook.react.shell.MainReactPackage
import com.therouter.router.Route
import com.yzq.application.AppManager
import com.yzq.base.extend.initToolbar
import com.yzq.binding.viewBinding
import com.yzq.common.BuildConfig
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.databinding.ActivityReactNativeBinding
import com.yzq.reactnative.BaseJsExecptionHandler
import com.yzq.reactnative.BaseRnActivity


/**
 * @description 用于承载RN的Activity
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 */

@Route(path = RoutePath.Main.REACT_NATIVE)
class ReactNativeActivity : BaseRnActivity() {


    private val binding by viewBinding(ActivityReactNativeBinding::inflate)
    override fun getReactRootView() = binding.reactRootView

    override fun createReactInstanceManager(): ReactInstanceManager {
        return ReactInstanceManager.builder()
            //设置Application上下文；
            .setApplication(AppManager.application)
            //添加package，package内部包含了多个Native Module；
            .addPackage(MainReactPackage())
            //JS 异常回调处理实现；
            .setRedBoxHandler(BaseJsExecptionHandler())
            .setCurrentActivity(this)
            .setBundleAssetName("rn/index.android.bundle")//打包时放在assets目录下的JS bundle包的名字，加载内置bundle时使用；
            .setJSMainModulePath("index")//入口文件名字，即index.js；
            .setJavaScriptExecutorFactory(HermesExecutorFactory())
            .setUseDeveloperSupport(BuildConfig.DEBUG)//开发者支持，开发模式下开启；
            .setInitialLifecycleState(LifecycleState.RESUMED)//设置初始生命周期状态，这里设置为resume即可；
            .build()


    }


    override fun initWidget() {

        binding.run {
            initToolbar(includedToolbar.toolbar, "ReactNative")
            reactRootView.startReactApplication(reactInstanceManager, "RNProject0_73_1", null)
            fab.setOnClickListener {
                //显示开发者调试菜单
                reactInstanceManager.devSupportManager?.showDevOptionsDialog()
            }
        }

    }


}

