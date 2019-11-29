package com.yzq.kotlincommon.ui.activity

import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.mvvm.view_model.FragmentViewModel
import com.yzq.lib_base.ui.BaseFragment
import com.yzq.lib_base.ui.BaseMvvmActivity
import kotlinx.android.synthetic.main.activity_fragment.*


@Route(path = RoutePath.Main.FRAGMENT)
class FragmentActivity : BaseMvvmActivity<FragmentViewModel>(),
    BottomNavigationView.OnNavigationItemSelectedListener {

    override fun getViewModelClass(): Class<FragmentViewModel> = FragmentViewModel::class.java


    override fun getContentLayoutId(): Int = R.layout.activity_fragment


    override fun initWidget() {

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        initToolbar(toolbar, "Fragment")



        bottom_navigation.setOnNavigationItemSelectedListener(this)


        showFragment(vm.taskFragment)


    }

    override fun observeViewModel() {

//        vm.tabSelectedPosition.observe(this, object : Observer<Int> {
//            override fun onChanged(position: Int) {
//                LogUtils.i("position：${position}")
//                showFragment(position)
//            }
//        })

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
