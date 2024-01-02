package com.yzq.kotlincommon.ui.activity

import com.facebook.hermes.reactexecutor.HermesExecutorFactory
import com.facebook.react.ReactInstanceManager
import com.facebook.react.ReactRootView
import com.facebook.react.common.LifecycleState
import com.facebook.react.shell.MainReactPackage
import com.therouter.router.Route
import com.yzq.application.AppManager
import com.yzq.base.extend.initToolbar
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.BuildConfig
import com.yzq.kotlincommon.databinding.ActivityReactNativeBinding
import com.yzq.react_native.BaseRnActivity

@Route(path = RoutePath.Main.REACT_NATIVE)
class ReactNativeActivity : BaseRnActivity() {


    private val binding by viewbind(ActivityReactNativeBinding::inflate)
    override fun getReactRootView() = binding.reactRootView

    override fun createReactInstanceManager(): ReactInstanceManager {
        return ReactInstanceManager.builder()
            .setApplication(AppManager.application)
            .setCurrentActivity(this)
            .setBundleAssetName("index.android.bundle")//打包时放在assets目录下的JS bundle包的名字，App release之后会从该目录下加载JS bundle；
            .setJSMainModulePath("index")//入口文件名字，即index.js；
            .addPackage(MainReactPackage())
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
                reactInstanceManager.devSupportManager?.showDevOptionsDialog()
            }
        }

    }


}

