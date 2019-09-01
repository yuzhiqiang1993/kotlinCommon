package com.yzq.kotlincommon.ui.activity

import android.graphics.Color
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.android.material.navigation.NavigationView
import com.tencent.bugly.beta.Beta
import com.yzq.common.extend.transform
import com.yzq.common.ui.BaseActivity
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.adapter.MainAdapter
import io.reactivex.Observable
import kotlinx.android.synthetic.main.appbar_main.*
import java.util.concurrent.TimeUnit

/**
 * @description: 导航页面
 * @author : yzq
 * @date   : 2018/11/26
 * @time   : 10:48
 *
 */

@Route(path = RoutePath.Main.MAIN)
class MainActivity : BaseActivity(), BaseQuickAdapter.OnItemClickListener, NavigationView.OnNavigationItemSelectedListener {


    private var items = arrayListOf<String>()


    private lateinit var mainAdapter: MainAdapter
    override fun getContentLayoutId(): Int {

        return R.layout.activity_main
    }


    override fun initWidget() {

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        BarUtils.setStatusBarColor(this, Color.argb(0, 0, 0, 0))
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar)

        initToolbar(toolbar, "kotlin common", displayHome = false)

        initRecycleView(recy)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        Observable.timer(3, TimeUnit.SECONDS)
                .transform(this)
                .subscribe {
                    LogUtils.i("检查更新")
                    Beta.checkUpgrade()
                }

    }


    override fun initData() {

        items.add("电影列表")
        items.add("任务页面（悬浮吸顶、侧滑菜单）")
        items.add("图片页面（选择压缩）")
        items.add("弹窗")
        items.add("Zxing")
        items.add("登录页面（SharedPreference）")
        items.add("AutoDispose")
        items.add("下拉菜单")
        items.add("高德定位")
        items.add("FlexBoxLayout")
        items.add("瀑布流图片")
        items.add("fragment")

        setdata()
    }

    private fun setdata() {
        val recy = findViewById<RecyclerView>(R.id.recy)
        mainAdapter = MainAdapter(R.layout.item_main_layout, items)
        mainAdapter.onItemClickListener = this
        recy.adapter = mainAdapter


    }


    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {

        when (position) {
            0 -> skip(com.yzq.common.constants.RoutePath.Main.MOVIES)
            1 -> skip(com.yzq.common.constants.RoutePath.Main.TASK)
            2 -> skip(com.yzq.common.constants.RoutePath.Main.IMG_COMPRESS)
            3 -> skip(com.yzq.common.constants.RoutePath.Main.DIALOG)
            4 -> skip(com.yzq.common.constants.RoutePath.Main.ZXING)
            5 -> skip(com.yzq.common.constants.RoutePath.Main.LOGIN)
            6 -> skip(com.yzq.common.constants.RoutePath.Main.AUTODISPOSE)
            7 -> skip(com.yzq.common.constants.RoutePath.Main.DROP_DOWN_MENU)
            8 -> skip(com.yzq.common.constants.RoutePath.GaoDe.GAO_DE)
            9 -> skip(com.yzq.common.constants.RoutePath.Main.FLEX_BOX)
            10 -> skip(com.yzq.common.constants.RoutePath.Main.IMG_LIST)
            11 -> skip(com.yzq.common.constants.RoutePath.Main.FRAGMENT)

        }
    }

    private fun skip(path: String) {

        ARouter.getInstance().build(path).navigation(this)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_map -> {

            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_tools -> {

            }
            R.id.nav_share -> {

            }

        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            moveTaskToBack(false)
        }
    }


}
