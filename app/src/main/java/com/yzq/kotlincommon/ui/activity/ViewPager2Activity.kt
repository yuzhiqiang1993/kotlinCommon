package com.yzq.kotlincommon.ui.activity

import android.view.MenuItem
import androidx.fragment.app.commit
import com.google.android.material.navigation.NavigationBarView
import com.therouter.router.Route
import com.yzq.base.extend.initToolbar
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.base.ui.fragment.BaseFragment
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.ActivityViewPager2Binding
import com.yzq.kotlincommon.ui.fragment.ViewPagerFragment
import com.yzq.kotlincommon.ui.fragment.ViewPagerWithFragment

/**
 * @description: ViewPager2
 * @author : yzq
 * @date : 2019/11/27
 * @time : 13:21
 */

@Route(path = RoutePath.Main.VIEW_PAGER)
class ViewPager2Activity :
    BaseActivity(),
    NavigationBarView.OnItemSelectedListener {

    private val binding by viewbind(ActivityViewPager2Binding::inflate)
    private val viewPagerFragment by lazy { ViewPagerFragment() }
    private val viewPagerWithFragment by lazy { ViewPagerWithFragment() }
    private val fragmentList = arrayListOf<BaseFragment>()

    override fun initWidget() {
        super.initWidget()

        initToolbar(binding.layoutToolbar.toolbar, "ViewPager示例")

        binding.bottomNavigation.setOnItemSelectedListener(this)

        showFragment(viewPagerFragment)
    }

    private fun showFragment(fragment: BaseFragment) {

        supportFragmentManager.commit {
            /*优化事务操作*/
            setReorderingAllowed(true)
            if (!fragment.isAdded) {
                add(R.id.fragment_container_view, fragment)
            }
            if (!fragmentList.contains(fragment)) {
                fragmentList.add(fragment)
            }
            fragmentList.forEach {
                if (fragment != it) {
                    hide(it)
                }
            }
            show(fragment)
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
