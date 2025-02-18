package com.yzq.kotlincommon.ui.activity

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.lifecycle.asLiveData
import com.hjq.permissions.Permission
import com.therouter.router.Route
import com.yzq.application.AppStateListener
import com.yzq.baseui.BaseActivity
import com.yzq.binding.viewBinding
import com.yzq.core.extend.setOnThrottleTimeClick
import com.yzq.kotlincommon.databinding.ActivityServiceBinding
import com.yzq.kotlincommon.service.BackendService
import com.yzq.kotlincommon.service.BindService
import com.yzq.kotlincommon.service.ForegroundService
import com.yzq.logger.Logger
import com.yzq.permission.getPermissions
import com.yzq.router.RoutePath
import com.yzq.util.ext.initToolbar
import kotlinx.coroutines.flow.filterNotNull

/**
 * @description Service使用示例
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2023/1/12
 * @time    09:47
 */

@Route(path = RoutePath.Main.SERVICE)
class ServiceActivity : BaseActivity(), AppStateListener {
    private val binding by viewBinding(ActivityServiceBinding::inflate)


    /**
     * Service connection
     * 调用bindService时传入，跟Service建立连接
     */
    private var serviceConnection: ServiceConnection? = null


    override fun initWidget() {

        binding.run {

            initToolbar(includedToolbar.toolbar, "Service")

            btnFrontService.setOnThrottleTimeClick {
                /*启动前台服务需要判断是否有通知权限*/
                getPermissions(Permission.NOTIFICATION_SERVICE, Permission.POST_NOTIFICATIONS) {
                    val intent = Intent(this@ServiceActivity, ForegroundService::class.java)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startForegroundService(intent)
                    } else {
                        startService(intent)
                    }
                }
            }


            btnBindService.setOnThrottleTimeClick {
                val intent = Intent(this@ServiceActivity, BindService::class.java)
                startService(intent)
                if (serviceConnection == null) {
                    createServieConnection()
                }
                bindService(intent, serviceConnection!!, BIND_AUTO_CREATE)
            }

            btnBackendService.setOnThrottleTimeClick {
                val intent = Intent(this@ServiceActivity, BackendService::class.java)
                val bundle = Bundle().apply {
                    putString("key1", "key1")
                    putInt("key2", 2)
                }
                intent.putExtras(bundle)
                /**
                 * start service 启动的服务生命周期跟App一直，处于手动停止，否则只有等app销毁才会跟着销毁
                 */
                this@ServiceActivity.startService(intent)

            }
        }

    }

    private fun createServieConnection() {
        serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                Logger.i("onServiceConnected")
                /*获取Service返回的binder*/
                val serviceBinder = service as BindService.ServiceBinder

                serviceBinder.run {
                    changeName("更改的值")
                    locationFlow
                        .filterNotNull()
                        .asLiveData()
                        .observe(this@ServiceActivity) {
                            Logger.i("定位结果：${it}")
                        }
                }
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                /*服务系统杀掉时才会执行*/
                Logger.i("onServiceConnected")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        /*这里要 unbindService 否则会内存泄漏 并且stopService调用后BindService才会onDestory*/
        serviceConnection?.run {
            unbindService(this)
        }
        stopService(Intent(this, BindService::class.java))
    }


}