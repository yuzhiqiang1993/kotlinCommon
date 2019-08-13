package com.yzq.kotlincommon.ui.activity

import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.blankj.utilcode.util.LogUtils
import com.yzq.common.constants.RoutePath
import com.yzq.common.ui.BaseMvvmActivity
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




        LogUtils.i("supportFragmentManager" + supportFragmentManager)

        vm.fragmentList.forEach {

            if (!it.isAdded) {

                LogUtils.i("没有添加过")
                supportFragmentManager.beginTransaction().add(R.id.layout_fragment, it).commit()
            }
        }


    }

    override fun observeViewModel() {

        vm.tabSelectedPosition.observe(this, object : Observer<Int> {
            override fun onChanged(position: Int) {
                LogUtils.i("position：${position}")
                showFragment(position)
            }
        })

    }

    private fun showFragment(position: Int) {

        vm.fragmentList.forEach {

            supportFragmentManager.beginTransaction().hide(it).commit()
        }


        supportFragmentManager.beginTransaction().show(vm.fragmentList[position]).commit()


    }

    override fun onTabReselected(position: Int) {

    }

    override fun onTabUnselected(position: Int) {

    }


    override fun onTabSelected(position: Int) {

        vm.changeTabSelected(position)

    }

}
