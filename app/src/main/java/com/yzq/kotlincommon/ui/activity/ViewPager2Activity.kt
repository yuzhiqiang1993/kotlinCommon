package com.yzq.kotlincommon.ui.activity

import android.view.MenuItem
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.ActivityViewPager2Binding
import com.yzq.kotlincommon.ui.fragment.ViewPagerFragment
import com.yzq.kotlincommon.ui.fragment.ViewPagerWithFragment
import com.yzq.lib_base.ui.BaseFragment
import com.yzq.lib_base.ui.BaseViewBindingActivity


/**
 * @description: ViewPager2
 * @author : yzq
 * @date   : 2019/11/27
 * @time   : 13:21
 */

@Route(path = RoutePath.Main.VIEW_PAGER)
class ViewPager2Activity : BaseViewBindingActivity<ActivityViewPager2Binding>(),
    BottomNavigationView.OnNavigationItemSelectedListener {

    private val viewPagerFragment = ViewPagerFragment()
    private val viewPagerWithFragment = ViewPagerWithFragment()
    val fragmentList = arrayListOf<BaseFragment>()


    override fun getViewBinding() = ActivityViewPager2Binding.inflate(layoutInflater)


    override fun initWidget() {
        super.initWidget()

        initToolbar(binding.layoutToolbar.toolbar, "ViewPager示例")

        binding.bottomNavigation.setOnNavigationItemSelectedListener(this)

        showFragment(viewPagerFragment)


    }

    private fun showFragment(fragment: BaseFragment) {


        with(supportFragmentManager.beginTransaction()) {
            if (!fragment.isAdded) {
                add(R.id.layout_fragment, fragment)
            }

            if (!fragmentList.contains(fragment)) {
                fragmentList.add(fragment)
            }


            fragmentList.forEach {

                if (fragment != it) {
                    hide(it)
                }

            }

            show(fragment).commit()
        }


    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        if (!item.isChecked) {
            when (item.itemId) {
                R.id.menu_view -> {
                    showFragment(viewPagerFragment)
                }
                R.id.menu_fragment -> {

                    showFragment(viewPagerWithFragment)
                }
            }
        }



        return true

    }


}
