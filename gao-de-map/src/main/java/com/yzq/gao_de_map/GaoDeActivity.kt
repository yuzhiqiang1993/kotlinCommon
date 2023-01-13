package com.yzq.gao_de_map

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import androidx.activity.viewModels
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.therouter.router.Route
import com.yzq.base.extend.initToolbar
import com.yzq.base.extend.observeUIState
import com.yzq.base.extend.setOnThrottleTimeClick
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewbind
import com.yzq.gao_de_map.databinding.ActivityGaoDeBinding
import com.yzq.gao_de_map.ext.openGaoDeMap
import com.yzq.gao_de_map.ext.openGaoDeNavi
import com.yzq.gao_de_map.service.LocationService
import com.yzq.gao_de_map.utils.MapPermissionUtils

@Route(path = com.yzq.common.constants.RoutePath.GaoDe.GAO_DE)
class GaoDeActivity : BaseActivity() {

    private val binding by viewbind(ActivityGaoDeBinding::inflate)
    private val signLocationViewModel: SignLocationViewModel by viewModels()


    override fun initWidget() {

        initToolbar(binding.includedToolbar.toolbar, "高德")

        binding.run {
            btnLocation.setOnThrottleTimeClick {
                MapPermissionUtils.checkLocationPermission(true, this@GaoDeActivity) {
                    signLocationViewModel.startLocation()
                }
            }

            btnContinueLocation.setOnThrottleTimeClick {
                MapPermissionUtils.checkLocationPermission(true, this@GaoDeActivity) {
                    /**
                     * 锁屏后一分钟左右的时间会导致cpu休眠，此时后台定位不会执行，就算执行也会定位失败，Alarm+weaklock的方案实测不行
                     * 申请ACCESS_BACKGROUND_LOCATION权限（定位权限选始终允许），引导用户开启 允许完全后台权限 是最好的方案
                     */
//                    continueLocationViewModel.startLocation()
                    val intent = Intent(this@GaoDeActivity, LocationService::class.java)
                    startService(intent)
                }
            }

            btnStopContinueLocation.setOnThrottleTimeClick {
                val intent = Intent(this@GaoDeActivity, LocationService::class.java)
                stopService(intent)
            }

            btnAllowBack.setOnThrottleTimeClick {
                ignoringBatteryOptimizations()
            }

            btnOpenGaode.setOnThrottleTimeClick {
                openGaoDeMap()
            }

            btnGaodeNav.setOnThrottleTimeClick {
                openGaoDeNavi("39.91", "116.40", "天安门")
            }
        }
    }

    /*申请忽略电池优化*/
    private fun ignoringBatteryOptimizations() {
        val appPackageName = AppUtils.getAppPackageName()
        LogUtils.i("appPackageName:${appPackageName}")
        val powerManager = getSystemService(POWER_SERVICE) as PowerManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val batteryOptimizations =
                powerManager.isIgnoringBatteryOptimizations(appPackageName)
            LogUtils.i("是否忽略电池优化:${batteryOptimizations}")
            if (!batteryOptimizations) {
                val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
                intent.data = Uri.parse("package:$appPackageName")
                startActivity(intent)
            } else {
                ToastUtils.showShort("已经忽略电池优化")
            }
        }
    }

    override fun observeViewModel() {
        observeUIState(signLocationViewModel, loadingDialog)
        signLocationViewModel.locationData.observe(this) { t ->
            if (t.errorCode == 0) {
                binding.tvLocationResult.text = t.address
            } else {
                binding.tvLocationResult.text = t.locationDetail
            }
        }
    }

}
