package com.yzq.kotlincommon.ui

import android.view.KeyEvent
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.tencent.bugly.beta.Beta
import com.yzq.common.BaseApp
import com.yzq.common.constants.RoutePath
import com.yzq.common.extend.transform
import com.yzq.common.ui.BaseActivity
import com.yzq.common.widget.ItemDecoration
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.adapter.MainAdapter
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

/**
 * @description: 导航页面
 * @author : yzq
 * @date   : 2018/11/26
 * @time   : 10:48
 *
 */

class MainActivity : BaseActivity(), BaseQuickAdapter.OnItemClickListener {


    private var items = arrayListOf<String>()


    private lateinit var mainAdapter: MainAdapter
    override fun getContentLayoutId(): Int {

        return R.layout.activity_main
    }


    override fun initWidget() {
        super.initWidget()

        val toolbar = this.findViewById<Toolbar>(R.id.toolbar)
        initToolbar(toolbar, "导航")

        mainAdapter = MainAdapter(R.layout.item_main_layout, items)
        mainAdapter.onItemClickListener = this
        recy.addItemDecoration(ItemDecoration.baseItemDecoration(this))
        recy.adapter = mainAdapter


        Observable.timer(3, TimeUnit.SECONDS)
                .transform(this)
                .subscribe {
                    LogUtils.i("检查更新")
                    Beta.checkUpgrade()
                }

    }


    override fun initData() {
        super.initData()

        items.add("新闻页面（Retrofit）")
        items.add("任务页面（悬浮吸顶、侧滑菜单）")
        items.add("图片页面（选择压缩）")
        items.add("弹窗")
        items.add("Zxing")
        items.add("登录页面（SharedPreference）")
        items.add("AutoDispose")
        items.add("下拉菜单")
        items.add("高德定位")
        items.add("FlexBoxLayout")


    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {

        when (position) {
            0 -> skip(RoutePath.Main.NEWS)
            1 -> skip(RoutePath.Main.TASK)
            2 -> skip(RoutePath.Main.IMG)
            3 -> skip(RoutePath.Main.DIALOG)
            4 -> skip(RoutePath.Main.ZXING)
            5 -> skip(RoutePath.Main.LOGIN)
            6 -> skip(RoutePath.Main.AUTODISPOSE)
            7 -> skip(RoutePath.Main.DROP_DOWN_MENU)
            8 -> skip(RoutePath.Main.GAO_DE)
            9 -> skip(RoutePath.Main.FLEX_BOX)

        }
    }

    private fun skip(path: String) {

        ARouter.getInstance().build(path).navigation()
    }

    private var exitTime: Long = 0
    /*退出程序*/
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            LogUtils.i("返回键")
            if (System.currentTimeMillis() - exitTime > 2000) {
                exitTime = System.currentTimeMillis()
                ToastUtils.showShort("再按一次退出程序")
            } else {
                BaseApp.INSTANCE.exitApp()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}
