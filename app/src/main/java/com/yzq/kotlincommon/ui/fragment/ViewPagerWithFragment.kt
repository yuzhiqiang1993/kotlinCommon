package com.yzq.kotlincommon.ui.fragment

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.yzq.base.ui.fragment.BaseFragment
import com.yzq.binding.viewBinding
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.FragmentViewPagerWithBinding
import com.yzq.kotlincommon.ui.fragment.data.PortalTab
import com.yzq.kotlincommon.ui.fragment.manager.PortalBarManager
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.ceil

class ViewPagerWithFragment :
    BaseFragment(R.layout.fragment_view_pager_with) {
    private val binding by viewBinding(FragmentViewPagerWithBinding::bind)

    private var fragmentAdapter: FragmentStateAdapter? = null
    private val fragmentList = mutableListOf<Fragment>()
    private val portalTabList: MutableList<PortalTab> = mutableListOf()
    private var portalBarManager: PortalBarManager? = null


    @SuppressLint("NotifyDataSetChanged")
    override fun initVariable() {
        portalBarManager = PortalBarManager(binding.tabLayout).apply {
            hidePortalBar()
        }
        //先添加默认的Fragment
        addDefaultFragment()
        MainScope().launch {
            delay(500)
            //模拟读取配置成功后添加Fragment
            addfragment1()//模拟测试
            addFragment3()//模拟测试
            addFragment2()//模拟测试

            //添加完成后，显示portalbar
            fragmentAdapter?.notifyDataSetChanged()

            binding.viewPager.offscreenPageLimit =
                kotlin.runCatching { ceil(fragmentList.size / 2.0).toInt() }.getOrDefault(2)

            portalBarManager?.showPortalBar(true, 1200)
        }
    }

    private fun addFragment2() {
        fragmentList.add(
            PagerContentFragment("coffee")
        )
        portalTabList.add(
            PortalTab("咖啡", R.drawable.portal_coffee, R.drawable.portal_coffee_selected)
        )

    }

    private fun addFragment3() {
        fragmentList.add(
            PagerContentFragment("yyzzc")
        )
        portalTabList.add(
            PortalTab("爷爷自在茶", R.drawable.portal_yyzzc, R.drawable.portal_yyzzc_selected)
        )

    }

    private fun addfragment1() {
        fragmentList.add(
            PagerContentFragment("Kpro")
        )
        portalTabList.add(
            PortalTab(
                "Kpro", R.drawable.portal_kpro, R.drawable.portal_kpro_selected
            )
        )

    }

    private fun addDefaultFragment() {

        fragmentList.add(PagerContentFragment("默认Fragment"))
        portalTabList.add(
            PortalTab(
                "kfc", R.drawable.portal_kfc, R.drawable.portal_kfc_selected
            )
        )


    }

    override fun initWidget() {
        fragmentAdapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = fragmentList.size
            override fun createFragment(position: Int): Fragment = fragmentList[position]
        }

        binding.viewPager.apply {
            this.adapter = fragmentAdapter
            this.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    portalBarManager?.updateTabSelection(position, portalTabList)
                }

                override fun onPageScrolled(
                    position: Int, positionOffset: Float, positionOffsetPixels: Int
                ) {
                    portalBarManager?.updateTabOnScroll(position, positionOffset)
                }
            })
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            portalBarManager?.initTabIcon(tab, position, portalTabList)
        }.attach()
    }

}
