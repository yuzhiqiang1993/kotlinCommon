package com.yzq.kotlincommon.ui.activity

import android.view.MenuItem
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.adapter.ViewPagerAdapter
import com.yzq.kotlincommon.ui.fragment.ViewPagerFragment
import com.yzq.kotlincommon.ui.fragment.ViewPagerWithFragment
import com.yzq.lib_base.ui.BaseActivity
import com.yzq.lib_base.ui.BaseFragment
import kotlinx.android.synthetic.main.activity_view_pager2.*


/**
 * @description: ViewPager2
 * @author : yzq
 * @date   : 2019/11/27
 * @time   : 13:21
 */

@Route(path = RoutePath.Main.VIEW_PAGER)
class ViewPager2Activity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun getContentLayoutId() = R.layout.activity_view_pager2

    private val viewPagerFragment = ViewPagerFragment()
    private val viewPagerWithFragment = ViewPagerWithFragment()
    val fragmentList = arrayListOf<BaseFragment>()

    override fun initWidget() {
        super.initWidget()
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        initToolbar(toolbar, "ViewPager示例")

        bottom_navigation.setOnNavigationItemSelectedListener(this)

        showFragment(viewPagerFragment)


    }

    private fun showFragment(fragment: BaseFragment) {

        if (!fragment.isAdded) {
            supportFragmentManager.beginTransaction().add(R.id.layout_fragment, fragment).commit()
        }

        addFragment(fragment)


        fragmentList.forEach {

            supportFragmentManager.beginTransaction().hide(it).commit()
        }

        supportFragmentManager.beginTransaction().show(fragment).commit()


    }


    fun addFragment(fragment: BaseFragment) {

        if (!fragmentList.contains(fragment)) {
            fragmentList.add(fragment)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_view -> {
                showFragment(viewPagerFragment)
            }
            R.id.menu_fragment -> {

                showFragment(viewPagerWithFragment)
            }
        }

        return true

    }


}
