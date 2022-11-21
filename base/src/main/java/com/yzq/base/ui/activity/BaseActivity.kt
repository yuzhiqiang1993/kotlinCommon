package com.yzq.base.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import com.yzq.base.R
import com.yzq.base.ui.ImgPreviewActivity
import com.yzq.base.ui.fragment.BaseFragment
import com.yzq.base.ui.state_view.StateViewManager

/**
 * @Description: Activity基类
 * @author : yzq
 * @date : 2018/7/2
 * @time : 13:55
 *
 */

abstract class BaseActivity : AppCompatActivity {

    constructor() : super()
    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    /*视图状态管理器*/
    protected val stateViewManager by lazy { StateViewManager(this) }

    protected val currentClassTag = "${System.currentTimeMillis()}-${this.javaClass.simpleName}"

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

        /*参数初始化，intent携带的值*/
        initArgs(intent.extras)
        /*viewmodel的初始化*/
        initViewModel()
        /*变量初始化*/
        initVariable()
        /*控件的初始化，例如绑定点击时间之类的*/
        initWidget()
        /*一些监听的初始化，*/
        initListener()
        /*初始化数据*/
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

    /**
     * 初始化数据
     *
     */
    protected open fun initData() {
    }

    /**
     * Toolbar的返回按钮
     *
     */
    override fun onSupportNavigateUp(): Boolean {

        onBackPressed()
        return super.onSupportNavigateUp()
    }

    /**
     *
     * @param path String  图片路径
     * @param view View  view
     */
    protected fun preViewImg(path: String, view: View) {
        val intent = Intent(this, ImgPreviewActivity::class.java)
        intent.putExtra(ImgPreviewActivity.IMG_PATH, path)

        val options =
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                view,
                getString(R.string.img_transition)
            )
        startActivity(intent, options.toBundle())
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {

            if (it is BaseFragment) {
                if (it.onBackPressed()) {
                    return
                }
            }
        }
        super.onBackPressed()
    }

}
