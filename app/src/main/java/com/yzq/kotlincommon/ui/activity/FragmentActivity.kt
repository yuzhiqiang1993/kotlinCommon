package com.yzq.kotlincommon.ui.activity

import android.view.MenuItem
import androidx.activity.viewModels
import androidx.fragment.app.commit
import com.google.android.material.navigation.NavigationBarView
import com.therouter.router.Route
import com.yzq.baseui.BaseActivity
import com.yzq.baseui.BaseFragment
import com.yzq.binding.viewBinding
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.ActivityFragmentBinding
import com.yzq.kotlincommon.view_model.FragmentViewModel
import com.yzq.router.RoutePath
import com.yzq.util.ext.initToolbar

@Route(path = RoutePath.Main.FRAGMENT)
class FragmentActivity : BaseActivity(), NavigationBarView.OnItemSelectedListener {

    private val binding by viewBinding(ActivityFragmentBinding::inflate)
    private val vm: FragmentViewModel by viewModels()

    override fun initWidget() {

        initToolbar(binding.layoutToolbar.toolbar, "Fragment")

        binding.bottomNavigation.setOnItemSelectedListener(this)

        showFragment(vm.taskFragment)
    }

    private fun showFragment(fragment: BaseFragment) {

        supportFragmentManager.commit {
            /*优化事务操作*/
//            setReorderingAllowed(true)
            if (!fragment.isAdded) {
                add(R.id.fragment_container_view, fragment)
            }
            if (!vm.fragmentList.contains(fragment)) {
                vm.fragmentList.add(fragment)
            }
            vm.fragmentList.forEach {
                if (fragment != it) {
                    hide(it)
                }
            }

            show(fragment)
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {

        if (!menuItem.isChecked) {
            when (menuItem.itemId) {
                R.id.menu_task -> showFragment(vm.taskFragment)
                R.id.menu_user -> showFragment(vm.userFragment)
            }
        }
        return true
    }
}
