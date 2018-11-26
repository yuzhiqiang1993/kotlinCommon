package com.yzq.kotlincommon.ui

import android.support.v7.widget.Toolbar
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.yzq.common.constants.RoutePath
import com.yzq.common.ui.BaseActivity
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.adapter.MainAdapter
import kotlinx.android.synthetic.main.activity_main.*

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


    override fun initData() {
        super.initData()

        items.add("新闻页面（Retrofit）")
        items.add("任务页面（悬浮吸顶）")
        items.add("图片页面（选择压缩）")
        items.add("日期选择")
        items.add("Zxing")


    }

    override fun initWidget() {
        super.initWidget()

        var toolbar = this.findViewById<Toolbar>(R.id.toolbar)
        initToolbar(toolbar, "导航")

        mainAdapter = MainAdapter(R.layout.item_main_ayout, items)
        mainAdapter.setOnItemClickListener(this)
        recy.adapter=mainAdapter
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {

        when (position) {
            0 -> skip(RoutePath.Main.NEWS)
            1 -> skip(RoutePath.Main.TASK)
            2 -> skip(RoutePath.Main.IMG)
            3 -> skip(RoutePath.Main.DATE_TIME)
            4 -> skip(RoutePath.Main.ZXING)

        }
    }

    private fun skip(path: String) {

        ARouter.getInstance().build(path).navigation()
    }

}
