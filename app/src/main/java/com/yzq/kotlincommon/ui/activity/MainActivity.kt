package com.yzq.kotlincommon.ui.activity

import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.sdk.android.push.CommonCallback
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.google.android.material.navigation.NavigationView
import com.hjq.toast.Toaster
import com.therouter.TheRouter
import com.therouter.router.Route
import com.yzq.application.AppContext
import com.yzq.application.AppManager
import com.yzq.base.extend.initToolbar
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.common.data.NaviItem
import com.yzq.coroutine.ext.postDelayed
import com.yzq.floating_view.FloatingViewListener
import com.yzq.floating_view.FloatingViewManager
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.ActivityMainBinding
import com.yzq.kotlincommon.databinding.ItemMainLayoutBinding
import com.yzq.kotlincommon.databinding.LayoutFloatViewBinding
import com.yzq.logger.Logger
import com.yzq.logger.view.log_view.LogViewActivity


/**
 * @description: 导航页面
 * @author : yzq
 * @date : 2018/11/26
 * @time : 10:48
 *
 */

@Route(path = RoutePath.Main.MAIN)
class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val binding by viewbind(ActivityMainBinding::inflate)
    private var items = arrayListOf<NaviItem>()
    private var lastBackTimeMillis: Long = 0

    private var floatingViewManager: FloatingViewManager? = null

    override fun initVariable() {

        /*该操作会获取用户信息  所以建议在获取相关权限后再初始化 同时还能提升一些启动速度*/
        PushServiceFactory.getCloudPushService().register(
            AppContext,
            object : CommonCallback {
                override fun onSuccess(response: String?) {
                    Logger.i("init cloudchannel success")
                }

                override fun onFailed(errorCode: String, errorMessage: String) {
                    Logger.i("init cloudchannel failed -- errorcode:$errorCode -- errorMessage:$errorMessage")
                }
            }
        )


        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                val drawerLayout: DrawerLayout = binding.drawerLayout
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    if (System.currentTimeMillis() - lastBackTimeMillis > 2000) {
                        Toaster.showShort("再按一次退出")
                        lastBackTimeMillis = System.currentTimeMillis()
                    } else {
                        AppManager.exitApp()
                    }
                }

            }

        })
    }

    override fun initWidget() {
        val floatViewBinding = LayoutFloatViewBinding.inflate(LayoutInflater.from(AppContext))


        floatingViewManager = FloatingViewManager.Builder(this).floatingViewConfig {
            contentView(floatViewBinding.contentView)
            edgeView(floatViewBinding.edgeView)
            moveView(floatViewBinding.moveView)
            draggable(true)
            autoMoveToEdge(true)
            autoShowEdgeView(true)
            bottomMargin(100)
            marginEdge(20)
            edgeViewScale(0.5f)
        }.globalShow(true)
            .addBlackList(LogViewActivity::class.java)
            .build()

        floatingViewManager?.setFloatingViewListener(object : FloatingViewListener {
            override fun onClick() {
                Logger.showLogInfoPage()
            }

        })



        floatingViewManager?.show()


        binding.run {
            initToolbar(includedAppbarMain.toolbar, "kotlin common", displayHome = false)

            val toggle = ActionBarDrawerToggle(
                this@MainActivity,
                drawerLayout,
                includedAppbarMain.toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            )
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()
            navView.setNavigationItemSelectedListener(this@MainActivity)
        }


    }

    override fun initData() {

        items.add(NaviItem("电影列表", RoutePath.Main.MOVIES))
        items.add(NaviItem("Bugly Pro", RoutePath.Main.BUGLY))
        items.add(NaviItem("任务页面（悬浮吸顶、侧滑菜单）", RoutePath.Main.TASK))
        items.add(NaviItem("图片页面（选择压缩）", RoutePath.Main.IMG_COMPRESS))
        items.add(NaviItem("弹窗", RoutePath.Main.DIALOG))
        items.add(NaviItem("Zxing", RoutePath.Main.ZXING))
        items.add(NaviItem("BsDiff", RoutePath.Main.BS_DIFF))
        items.add(NaviItem("登录页面", RoutePath.Main.LOGIN))
        items.add(NaviItem("登录页面2", RoutePath.Main.USER_AUTH))
        items.add(NaviItem("下拉菜单", RoutePath.Main.DROP_DOWN_MENU))
        items.add(NaviItem("高德", RoutePath.GaoDe.GAO_DE))
        items.add(NaviItem("FlexBoxLayout", RoutePath.Main.FLEX_BOX))
        items.add(NaviItem("瀑布流图片", RoutePath.Main.IMG_LIST))
        items.add(NaviItem("图片加载", RoutePath.Main.IMAGE_LOAD))
        items.add(NaviItem("fragment", RoutePath.Main.FRAGMENT))
        items.add(NaviItem("coroutine(协程)", RoutePath.Main.COROUTINE))
        items.add(NaviItem("room(数据库)", RoutePath.Main.ROOM))
        items.add(NaviItem("下载进度", RoutePath.Main.DOWNLOAD))
        items.add(NaviItem("ViewPager", RoutePath.Main.VIEW_PAGER))
        items.add(NaviItem("WebView", RoutePath.Main.WEB_VIEW))
        items.add(NaviItem("DataBinding", RoutePath.Main.DATA_BINDING))
        items.add(NaviItem("ViewBinding", RoutePath.Main.VIEW_BINDING))
        items.add(NaviItem("ALi EMAS", RoutePath.Main.EMAS))
        items.add(NaviItem("Flow", RoutePath.Main.FLOW))
        items.add(NaviItem("Moshi", RoutePath.Main.MOSHI))
        items.add(NaviItem("Network", RoutePath.Main.NETWORK))
        items.add(NaviItem("BindingDelegate", RoutePath.Main.VIEW_BINDING_DELEGATE))
        items.add(NaviItem("接口请求", RoutePath.Main.API_CALL))
        items.add(NaviItem("线程池", RoutePath.Main.THREAD_POOL))
        items.add(NaviItem("Service", RoutePath.Main.SERVICE))
        items.add(NaviItem("存储", RoutePath.Main.STORAGE))
        items.add(NaviItem("Lottie", RoutePath.Main.LOTTIE))
        items.add(NaviItem("语音识别ASR", RoutePath.Main.ASR))
        items.add(NaviItem("蓝牙", RoutePath.Main.BLUETOOTH))
        items.add(NaviItem("ReactNative", RoutePath.Main.REACT_NATIVE))
        items.add(NaviItem("JavaActivity", RoutePath.Main.JAVA_ACTIVITY))
        items.add(NaviItem("Jetpack Compose", RoutePath.Main.COMPOSE))
        items.add(NaviItem("播放器", RoutePath.Player.PLAYER))

        setData()

    }

    private fun setData() {
        binding
            .includedAppbarMain
            .recy
            .linear()
            .divider(R.drawable.item_divider)
            .setup {
                addType<NaviItem>(R.layout.item_main_layout)
                onBind {
                    val itemBinding = getBinding<ItemMainLayoutBinding>()
                    itemBinding.mainItem.setTitle(getModel<NaviItem>().title)
                }
                R.id.mainItem.onClick {
                    skip(getModel<NaviItem>().path)
                }
            }.models = items


        var isScrolling: Boolean
        binding.includedAppbarMain.recy.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(
                recyclerView: RecyclerView,
                newState: Int
            ) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    //延时200ms，等待recyclerView滚动完成
                    isScrolling = true
                    floatingViewManager?.switchToEdgeView()

                } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    isScrolling = false
                    postDelayed(deleyMillis = 800) {
                        if (!isScrolling) {
                            floatingViewManager?.switchToContentView()
                        }

                    }

                }
            }
        })
    }

    private fun skip(path: String) {
        TheRouter.build(path).navigation()
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


}
