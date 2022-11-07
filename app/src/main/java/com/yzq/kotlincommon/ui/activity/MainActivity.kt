package com.yzq.kotlincommon.ui.activity

import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.google.android.material.navigation.NavigationView
import com.tencent.bugly.beta.Beta
import com.yzq.application.BaseApp
import com.yzq.base.extend.init
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.common.constants.RoutePath
import com.yzq.common.data.NaviItem
import com.yzq.coroutine.scope.lifeScope
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.adapter.MainAdapter
import com.yzq.kotlincommon.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * @description: 导航页面
 * @author : yzq
 * @date   : 2018/11/26
 * @time   : 10:48
 *
 */

@Route(path = RoutePath.Main.MAIN)
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main),
    NavigationView.OnNavigationItemSelectedListener, OnItemClickListener {

    private var items = arrayListOf<NaviItem>()

    private lateinit var mainAdapter: MainAdapter

    override fun createBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initWidget() {

        initToolbar(binding.includedAppbarMain.toolbar, "kotlin common", displayHome = false)

        binding.includedAppbarMain.recy.init()

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.includedAppbarMain.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)

        lifeScope {
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
        items.add(NaviItem("BsDiff", RoutePath.Main.BS_DIFF))
        items.add(NaviItem("登录页面", RoutePath.Main.LOGIN))
        items.add(NaviItem("下拉菜单", RoutePath.Main.DROP_DOWN_MENU))
        items.add(NaviItem("高德定位", RoutePath.GaoDe.GAO_DE))
        items.add(NaviItem("FlexBoxLayout", RoutePath.Main.FLEX_BOX))
        items.add(NaviItem("瀑布流图片", RoutePath.Main.IMG_LIST))
        items.add(NaviItem("fragment", RoutePath.Main.FRAGMENT))
        items.add(NaviItem("coroutine(协程)", RoutePath.Main.COROUTINE))
        items.add(NaviItem("room(数据库)", RoutePath.Main.ROOM))
        items.add(NaviItem("下载进度", RoutePath.Main.DOWNLOAD))
        items.add(NaviItem("ViewPager", RoutePath.Main.VIEW_PAGER))
        items.add(NaviItem("WebView", RoutePath.Main.WEB_VIEW))
        items.add(NaviItem("DataBinding", RoutePath.Main.DATA_BINDING))
        items.add(NaviItem("ViewBinding", RoutePath.Main.VIEW_BINDING))
        items.add(NaviItem("ALi EMAS", RoutePath.Main.EMAS))
        items.add(NaviItem("Channel", RoutePath.Main.CHANNEL))
        items.add(NaviItem("Moshi", RoutePath.Main.MOSHI))
        items.add(NaviItem("Network", RoutePath.Main.NETWORK))
        items.add(NaviItem("BindingDelegate", RoutePath.Main.VIEW_BINDING_DELEGATE))
        items.add(NaviItem("接口请求", RoutePath.Main.API_CALL))


        setData()
    }

    private fun setData() {
//        val recy = findViewById<RecyclerView>(R.id.recy)
        mainAdapter = MainAdapter(R.layout.item_main_layout, items)
        mainAdapter.setOnItemClickListener(this)
        binding.includedAppbarMain.recy.adapter = mainAdapter

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
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private var lastBackTimeMillis: Long = 0


    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = binding.drawerLayout
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
