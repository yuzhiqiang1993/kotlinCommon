package com.yzq.baseui

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

/**
 * @Description: Activity基类
 * @author : yzq
 * @date : 2018/7/2
 * @time : 13:55
 *
 */

abstract class BaseActivity(@LayoutRes private val contentLayoutId: Int = 0) :
    AppCompatActivity(contentLayoutId) {

    protected val TAG = "${this.javaClass.simpleName}-${this.hashCode()}"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (contentLayoutId == 0) {
            //contentLayoutId没有给值且没有其他设置contentView的方式时，需要自行设置contentView
            initContentView()
        }

        //防止首次安装点击home键重新实例化
        if (!this.isTaskRoot) {
            if (intent != null) {
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN == intent.action) {
                    finishAfterTransition()
                    return
                }
            }
        }


        //参数初始化，intent携带的值
        initArgs(intent.extras)
        //变量初始化
        initVariable()
        //控件的初始化，例如绑定点击时间之类的
        initWidget()
        //一些监听的初始化
        initListener()
        //viewmodel的监听
        observeViewModel()
        //初始化数据
        initData()

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                handleBackPressed()
            }

        })
    }


    protected open fun initContentView() {}

    /**
     * 处理返回键
     */
    protected open fun handleBackPressed() {
        finish()
    }

    /**
     * 初始化参数
     *
     * @param extras  传递的参数对象
     */
    protected open fun initArgs(extras: Bundle?) {
    }

    /**
     * 初始化变量
     */
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
