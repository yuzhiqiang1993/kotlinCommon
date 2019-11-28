package com.yzq.kotlincommon.ui.fragment


import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.adapter.ViewPagerAdapter
import com.yzq.lib_base.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_view_pager.*


/**
 * @description: 常规ViewPager
 * @author : yzq
 * @date   : 2019/11/28
 * @time   : 11:00
 */


class ViewPagerFragment : BaseFragment() {

    override fun getContentLayoutId() = R.layout.fragment_view_pager

    private val tabs = arrayListOf<TabLayout.Tab>()
    private val viewPagerAdapter =
        ViewPagerAdapter(
            R.layout.item_view_pager,
            arrayListOf(
                "tab1",
                "tab2",
                "tab3",
                "tab4"
            )
        )

    override fun initWidget() {
        super.initWidget()

        view_pager.adapter = viewPagerAdapter


        TabLayoutMediator(tab_layout, view_pager) { tab, position ->

            tab.text = viewPagerAdapter.data[position]


        }.attach()


    }

}
