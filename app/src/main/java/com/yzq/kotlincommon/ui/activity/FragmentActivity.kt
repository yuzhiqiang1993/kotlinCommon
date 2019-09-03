package com.yzq.kotlincommon.ui.activity

import androidx.appcompat.widget.Toolbar
import com.alibaba.android.arouter.facade.annotation.Route
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.blankj.utilcode.util.LogUtils
import com.yzq.lib_base.ui.BaseFragment
import com.yzq.lib_base.ui.BaseMvvmActivity
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.mvvm.view_model.FragmentViewModel
import kotlinx.android.synthetic.main.activity_fragment.*


@Route(path = RoutePath.Main.FRAGMENT)
class FragmentActivity : BaseMvvmActivity<FragmentViewModel>(), BottomNavigationBar.OnTabSelectedListener {

    override fun getViewModelClass(): Class<FragmentViewModel> = FragmentViewModel::class.java


    override fun getContentLayoutId(): Int = R.layout.activity_fragment


    override fun initWidget() {

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        initToolbar(toolbar, "Fragment")

        bottom_navigation_bar.addItem(BottomNavigationItem(R.drawable.ic_edit, "任务"))
                .addItem(BottomNavigationItem(R.drawable.ic_user, "用户"))
                .initialise()

        bottom_navigation_bar.setTabSelectedListener(this)


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

        if (!fragment.isAdded) {
            supportFragmentManager.beginTransaction().add(R.id.layout_fragment, fragment).commit()
        }

        vm.addFragment(fragment)


        vm.fragmentList.forEach {

            supportFragmentManager.beginTransaction().hide(it).commit()
        }

        supportFragmentManager.beginTransaction().show(fragment).commit()


    }

    override fun onTabReselected(position: Int) {

    }

    override fun onTabUnselected(position: Int) {

    }


    override fun onTabSelected(position: Int) {
        LogUtils.i("onTabSelected:${position}")
        when (position) {
            0 -> {
                showFragment(vm.taskFragment)
            }
            1 -> {
                showFragment(vm.userFragment)
            }
        }


    }

}
