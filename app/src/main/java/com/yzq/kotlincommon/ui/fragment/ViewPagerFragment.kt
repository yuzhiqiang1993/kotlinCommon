package com.yzq.kotlincommon.ui.fragment


import com.google.android.material.tabs.TabLayoutMediator
import com.yzq.base.ui.fragment.BaseBindingFragment
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.adapter.ViewPagerAdapter
import com.yzq.kotlincommon.databinding.FragmentViewPagerBinding


/**
 * @description: 常规ViewPager
 * @author : yzq
 * @date   : 2019/11/28
 * @time   : 11:00
 */


class ViewPagerFragment :
    BaseBindingFragment<FragmentViewPagerBinding>(R.layout.fragment_view_pager) {


    override val bindingBind = FragmentViewPagerBinding::bind

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
        binding.viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->

            tab.text = viewPagerAdapter.data[position]

        }.attach()


    }

}
