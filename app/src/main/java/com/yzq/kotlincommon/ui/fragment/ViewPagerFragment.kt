package com.yzq.kotlincommon.ui.fragment


import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.adapter.ViewPagerAdapter
import com.yzq.lib_base.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_view_pager.*


/**
 * @description: 加载常规的Biew
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
                "首页",
                "推荐",
                "热点",
                "科技教育",
                "娱乐新闻",
                "互联网",
                "探索",
                "软件"
            )
        )

    override fun initWidget() {
        super.initWidget()

        view_pager.adapter = viewPagerAdapter


        TabLayoutMediator(tab_layout, view_pager) { tab, position ->

            tab.setCustomView(R.layout.layout_custom_tab)

            tab.customView!!.findViewById<AppCompatTextView>(R.id.tv_tab_title).text =
                viewPagerAdapter.data[position]

            tabs.add(tab)


//            tab.text = viewPagerAdapter.data[position]


        }.attach()




        view_pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {


            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                changeTabsText(position)


            }


        })


    }


    /*更改选中文字样式*/
    private fun changeTabsText(position: Int) {


        tabs.forEachIndexed { index, tab ->
            val tabTv =
                tab.customView!!.findViewById<AppCompatTextView>(R.id.tv_tab_title)

            if (position == index) {
                tabTv.textSize = 18f
                tabTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))

            } else {
                tabTv.textSize = 14f
                tabTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_600))
            }


        }
    }

}
