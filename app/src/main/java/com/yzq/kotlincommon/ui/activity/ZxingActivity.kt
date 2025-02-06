package com.yzq.kotlincommon.ui.activity

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.hjq.permissions.Permission
import com.therouter.router.Route
import com.yzq.base.extend.copyText
import com.yzq.base.extend.initToolbar
import com.yzq.base.extend.setOnThrottleTimeClick
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewBinding
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.databinding.ActivityZxingBinding
import com.yzq.permission.getPermissions
import com.yzq.zxinglibrary.android.CaptureActivity
import com.yzq.zxinglibrary.bean.ZxingConfig
import com.yzq.zxinglibrary.common.Constant

/**
 * @description: 二维码扫描
 * @author : yzq
 * @date : 2018/12/5
 * @time : 10:05
 *
 */

@Route(path = RoutePath.Main.ZXING)
class ZxingActivity : BaseActivity() {
    private lateinit var qrCodeActivityResult: ActivityResultLauncher<Intent>

    private val binding by viewBinding(ActivityZxingBinding::inflate)

    override fun initWidget() {

        initToolbar(binding.includedToolbar.toolbar, "Zxing")

        binding.run {
            btnScan.setOnThrottleTimeClick {
                excuteZxing()
            }

            tvResult.setOnLongClickListener {
                copyText(tvResult.text)
                true
            }


        }

        qrCodeActivityResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                val data = it.data
                if (data != null) {
                    val content = data.getStringExtra(Constant.CODED_CONTENT)
                    binding.tvResult.text = content
                }
            }


    }

    private fun excuteZxing() {

        getPermissions(Permission.CAMERA, Permission.READ_MEDIA_IMAGES) {
            val intent = Intent(this, CaptureActivity::class.java)
            val zxingConfig = ZxingConfig()
            zxingConfig.isFullScreenScan = false
            intent.putExtra(Constant.INTENT_ZXING_CONFIG, zxingConfig)
            qrCodeActivityResult.launch(intent)
        }

    }

}
