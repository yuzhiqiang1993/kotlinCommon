package com.yzq.kotlincommon.ui.activity

import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.google.android.material.tabs.TabLayoutMediator
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.adapter.ViewPagerAdapter
import com.yzq.lib_base.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_view_pager2.*


/**
 * @description: ViewPager2
 * @author : yzq
 * @date   : 2019/11/27
 * @time   : 13:21
 */

@Route(path = RoutePath.Main.VIEW_PAGER)
class ViewPager2Activity : BaseActivity() {

    override fun getContentLayoutId() = R.layout.activity_view_pager2

    private val viewPagerAdapter =
        ViewPagerAdapter(
            R.layout.item_view_pager,
            arrayListOf(
                "首页",
                "康复心脑通",
                "郭林新气功",
                "名师讲坛",
                "抗癌明星"
            )
        )

    override fun initWidget() {
        super.initWidget()
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        initToolbar(toolbar, "ViewPager示例")

        view_pager.adapter = viewPagerAdapter


        TabLayoutMediator(tab_layout, view_pager) { tab, position ->

            tab.setCustomView(R.layout.layout_custom_tab)
            val tabTitleTv = tab.customView!!.findViewById<AppCompatTextView>(R.id.tv_tab_title)
            tabTitleTv.text = viewPagerAdapter.data[position]


            LogUtils.i("""当前Tab${tab.isSelected}:$position""")

        }.attach()


    }


}
