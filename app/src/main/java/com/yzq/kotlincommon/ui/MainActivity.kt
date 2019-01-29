package com.yzq.kotlincommon.ui

import android.view.View
import androidx.appcompat.widget.Toolbar
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.tencent.bugly.beta.Beta
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

        mainAdapter = MainAdapter(R.layout.item_main_ayout, items)
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
        items.add("日期选择")
        items.add("Zxing")
        items.add("登录页面（Preference）")
        items.add("AutoDispose")
        items.add("下拉菜单")


    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {

        when (position) {
            0 -> skip(RoutePath.Main.NEWS)
            1 -> skip(RoutePath.Main.TASK)
            2 -> skip(RoutePath.Main.IMG)
            3 -> skip(RoutePath.Main.DATE_TIME)
            4 -> skip(RoutePath.Main.ZXING)
            5 -> skip(RoutePath.Main.LOGIN)
            6 -> skip(RoutePath.Main.AUTODISPOSE)
            7 -> skip(RoutePath.Main.DROP_DOWN_MENU)

        }
    }

    private fun skip(path: String) {

        ARouter.getInstance().build(path).navigation()
    }

}
