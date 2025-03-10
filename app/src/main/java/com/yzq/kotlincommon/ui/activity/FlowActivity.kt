package com.yzq.kotlincommon.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.commit
import com.google.android.material.navigation.NavigationBarView
import com.therouter.router.Route
import com.yzq.baseui.BaseActivity
import com.yzq.baseui.BaseFragment
import com.yzq.binding.viewBinding
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.ActivityFlowBinding
import com.yzq.kotlincommon.ui.fragment.flow.FlowFragment
import com.yzq.kotlincommon.ui.fragment.flow.SharedFlowFragment
import com.yzq.logger.Logger
import com.yzq.router.RoutePath
import com.yzq.util.ext.initToolbar


/**
 * @description 协程中的flow
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2022/12/27
 * @time    09:54
 */

@Route(path = RoutePath.Main.FLOW)
class FlowActivity : BaseActivity(), NavigationBarView.OnItemSelectedListener {

    private val binding by viewBinding(ActivityFlowBinding::inflate)

    private val flowFragment by lazy { FlowFragment.newInstance() }
    private val sharedFlowFragment by lazy { SharedFlowFragment.newInstance() }
    private val fragmentList = arrayListOf<BaseFragment>()

    override fun initWidget() {
        super.initWidget()

        binding.run {
            initToolbar(includedToolbar.toolbar, "Flow")
            bottomNavigation.setOnItemSelectedListener(this@FlowActivity)

        }


        showFragment(flowFragment)

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
        Logger.i("onNavigationItemReselected:${item}")
        if (!item.isChecked) {
            /*不是重复选中 再进行切换*/
            when (item.itemId) {
                R.id.menu_flow -> showFragment(flowFragment)
                R.id.menu_shared_flow -> showFragment(sharedFlowFragment)
            }
        }
        return true
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.i("onCreate")
    }

    override fun onStart() {
        super.onStart()
        Logger.i("onStart")
    }

    override fun onResume() {
        super.onResume()
        Logger.i("onResume")
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        Logger.i("onWindowFocusChanged")
    }


}