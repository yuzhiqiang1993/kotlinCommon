package com.yzq.kotlincommon.ui.activity

import android.graphics.Color
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.google.android.material.navigation.NavigationView
import com.tencent.bugly.beta.Beta
import com.yzq.common.constants.RoutePath
import com.yzq.common.data.NaviItem
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.adapter.MainAdapter
import com.yzq.kotlincommon.databinding.ActivityMainBinding
import com.yzq.lib_base.BaseApp
import com.yzq.lib_base.extend.init
import com.yzq.lib_base.ui.BaseViewBindingActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @description: 导航页面
 * @author : yzq
 * @date   : 2018/11/26
 * @time   : 10:48
 *
 */

@Route(path = RoutePath.Main.MAIN)
class MainActivity : BaseViewBindingActivity<ActivityMainBinding>(),
    NavigationView.OnNavigationItemSelectedListener, OnItemClickListener {


    private var items = arrayListOf<NaviItem>()


    private lateinit var mainAdapter: MainAdapter


    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)


    override fun initWidget() {


        BarUtils.setStatusBarColor(this, Color.argb(0, 0, 0, 0))
        BarUtils.addMarginTopEqualStatusBarHeight(binding.layoutMain.toolbar)

        initToolbar(binding.layoutMain.toolbar, "kotlin common", displayHome = false)


        binding.layoutMain.recy.init()


        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.layoutMain.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)

        launch {

            delay(1000)
            withContext(Dispatchers.IO) {
                LogUtils.i("检查更新")
                Beta.checkUpgrade()
            }
        }
    }


    override fun initData() {

        items.add(NaviItem("电影列表", RoutePath.Main.MOVIES))
        items.add(NaviItem("任务页面（悬浮吸顶、侧滑菜单）", RoutePath.Main.TASK))
        items.add(NaviItem("图片页面（选择压缩）", RoutePath.Main.IMG_COMPRESS))
        items.add(NaviItem("弹窗", RoutePath.Main.DIALOG))
        items.add(NaviItem("Zxing", RoutePath.Main.ZXING))
        items.add(NaviItem("登录页面（SharedPreference）", RoutePath.Main.LOGIN))
        items.add(NaviItem("下拉菜单", RoutePath.Main.DROP_DOWN_MENU))
        items.add(NaviItem("高德定位", RoutePath.GaoDe.GAO_DE))
        items.add(NaviItem("FlexBoxLayout", RoutePath.Main.FLEX_BOX))
        items.add(NaviItem("瀑布流图片", RoutePath.Main.IMG_LIST))
        items.add(NaviItem("fragment", RoutePath.Main.FRAGMENT))
        items.add(NaviItem("coroutine(协程)", RoutePath.Main.COROUTINE))
        items.add(NaviItem("room(数据库)", RoutePath.Main.ROOM))
        items.add(NaviItem("下载进度", RoutePath.Main.DOWNLOAD))
        items.add(NaviItem("视频播放", RoutePath.Main.EXO_PLAYER))
        items.add(NaviItem("ViewPager", RoutePath.Main.VIEW_PAGER))
        items.add(NaviItem("WebView", RoutePath.Main.WEB_VIEW))
        items.add(NaviItem("DataBinding", RoutePath.Main.DATA_BINDING))
        items.add(NaviItem("ViewBinding", RoutePath.Main.VIEW_BINDING))


        setData()
    }

    private fun setData() {
        val recy = findViewById<RecyclerView>(R.id.recy)
        mainAdapter = MainAdapter(R.layout.item_main_layout, items)
        mainAdapter.setOnItemClickListener(this)
        recy.adapter = mainAdapter


    }


    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val clickItem = mainAdapter.data[position]

        skip(clickItem.path)
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

    var lastBackTimeMillis: Long = 0
    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            if (System.currentTimeMillis() - lastBackTimeMillis > 2000) {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show()
                lastBackTimeMillis = System.currentTimeMillis()

            } else {
                BaseApp.INSTANCE.exitApp()

            }
        }
    }


}
