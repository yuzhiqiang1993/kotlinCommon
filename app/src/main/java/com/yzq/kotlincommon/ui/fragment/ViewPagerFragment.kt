package com.yzq.kotlincommon.ui.fragment

import com.google.android.material.tabs.TabLayoutMediator
import com.yzq.base.ui.fragment.BaseFragment
import com.yzq.binding.viewBinding
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.adapter.ViewPagerAdapter
import com.yzq.kotlincommon.databinding.FragmentViewPagerBinding

/**
 * @description: 常规ViewPager
 * @author : yzq
 * @date : 2019/11/28
 * @time : 11:00
 */

class ViewPagerFragment : BaseFragment(R.layout.fragment_view_pager) {

    private val binding by viewBinding(FragmentViewPagerBinding::bind)

    private val viewPagerAdapter =
        ViewPagerAdapter(
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
