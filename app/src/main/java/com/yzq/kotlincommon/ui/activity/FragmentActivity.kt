package com.yzq.kotlincommon.ui.activity

import android.view.MenuItem
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.navigation.NavigationBarView
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.ActivityFragmentBinding
import com.yzq.kotlincommon.mvvm.view_model.FragmentViewModel
import com.yzq.lib_base.ui.activity.BaseVbVmActivity
import com.yzq.lib_base.ui.fragment.BaseFragment


@Route(path = RoutePath.Main.FRAGMENT)
class FragmentActivity : BaseVbVmActivity<ActivityFragmentBinding, FragmentViewModel>(),
    NavigationBarView.OnItemSelectedListener {

    override fun getViewBinding() = ActivityFragmentBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<FragmentViewModel> = FragmentViewModel::class.java


    override fun initWidget() {


        initToolbar(binding.layoutToolbar.toolbar, "Fragment")


        binding.bottomNavigation.setOnItemSelectedListener(this)

        showFragment(vm.taskFragment)


    }

    override fun observeViewModel() {

    }


    private fun showFragment(fragment: BaseFragment) {

        with(supportFragmentManager.beginTransaction()) {
            if (!fragment.isAdded) {
                add(R.id.layout_fragment, fragment)
            }

            if (!vm.fragmentList.contains(fragment)) {
                vm.fragmentList.add(fragment)
            }

            vm.fragmentList.forEach {

                if (fragment != it) {
                    hide(it)
                }

            }

            show(fragment).commit()
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
