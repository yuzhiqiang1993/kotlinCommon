package com.yzq.kotlincommon.ui.activity

import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.R
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

    private val viewPagerFragment = ViewPagerFragment()
    private val viewPagerWithFragment = ViewPagerWithFragment()
    val fragmentList = arrayListOf<BaseFragment>()
    override fun initContentView() {

        setContentView(R.layout.activity_view_pager2)
    }

    override fun initWidget() {
        super.initWidget()
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        initToolbar(toolbar, "ViewPager示例")

        bottom_navigation.setOnNavigationItemSelectedListener(this)

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
