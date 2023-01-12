package com.yzq.kotlincommon.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.hjq.permissions.Permission
import com.therouter.router.Route
import com.yzq.base.extend.initToolbar
import com.yzq.base.extend.setOnThrottleTimeClick
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.databinding.ActivityServiceBinding
import com.yzq.kotlincommon.service.BackendService
import com.yzq.kotlincommon.service.ForegroundService
import com.yzq.permission.getPermissions

/**
 * @description Service使用示例
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2023/1/12
 * @time    09:47
 */

@Route(path = RoutePath.Main.SERVICE)
class ServiceActivity : BaseActivity() {
    private val binding by viewbind(ActivityServiceBinding::inflate)


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


}