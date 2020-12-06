package com.yzq.kotlincommon.ui.fragment


import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.yzq.kotlincommon.R
import com.yzq.lib_base.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_view_pager.*


class ViewPagerWithFragment : BaseFragment() {


    override fun getContentLayoutId() = R.layout.fragment_view_pager_with


    private val tabs = arrayListOf<TabLayout.Tab>()


    private val tabTitles = arrayListOf(
        "首页",
        "推荐",
        "热点",
        "科技教育",
        "娱乐新闻",
        "互联网",
        "探索",
        "软件"
    )


    override fun initWidget() {
        super.initWidget()

        view_pager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {

                return tabTitles.size
            }

            override fun createFragment(position: Int): Fragment {
                return PagerContentFragment(tabTitles[position])
            }
        }


        TabLayoutMediator(tab_layout, view_pager) { tab, position ->

            tab.setCustomView(R.layout.layout_custom_tab)

            tab.customView!!.findViewById<AppCompatTextView>(R.id.tv_tab_title).text =
                tabTitles[position]

            tabs.add(tab)


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
