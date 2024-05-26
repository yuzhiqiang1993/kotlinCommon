package com.yzq.base.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.DisposableEffect
import com.therouter.TheRouter
import com.yzq.base.theme.AppTheme

/**
 * @Description: Activity基类
 * @author : yzq
 * @date : 2018/7/2
 * @time : 13:55
 *
 */

abstract class BaseComposeActivity : ComponentActivity() {


    protected val TAG = "${this.javaClass.simpleName}-${this.hashCode()}"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*防止首次安装点击home键重新实例化*/
        if (!this.isTaskRoot) {
            if (intent != null) {
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN == intent.action) {
                    finishAfterTransition()
                    return
                }
            }
        }



        TheRouter.inject(this)
        //参数初始化，intent携带的值
        initArgs(intent.extras)
        //变量初始化
        initVariable()
        //compose ui 初始化
        initComposeUI()

        //一些监听的初始化
        initListener()
        //viewmodel的监听
        observeViewModel()
        //初始化数据
        initData()


    }

    private fun initComposeUI() {
        actionBar?.hide()
        setContent {

            AppTheme {

                DisposableEffect(Unit) {

                    onDispose {}
                }

            }

        }
    }


    /**
     * 初始化参数
     *
     * @param extras  传递的参数对象
     */
    protected open fun initArgs(extras: Bundle?) {
    }

    /*初始化变量*/
    protected open fun initVariable() {
    }

    /**
     * 初始化控件
     *
     */
    protected open fun initWidget() {
    }

    protected open fun initListener() {
    }

    protected open fun observeViewModel() {}

    /**
     * 初始化数据
     *
     */
    protected open fun initData() {
    }


}
