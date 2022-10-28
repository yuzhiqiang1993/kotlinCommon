package com.yzq.kotlincommon.ui.activity

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.constant.PermissionConstants
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.databinding.ActivityZxingBinding
import com.yzq.permission.getPermissions
import com.yzq.zxinglibrary.android.CaptureActivity
import com.yzq.zxinglibrary.bean.ZxingConfig
import com.yzq.zxinglibrary.common.Constant


/**
 * @description: 二维码扫描
 * @author : yzq
 * @date   : 2018/12/5
 * @time   : 10:05
 *
 */

@Route(path = RoutePath.Main.ZXING)
class ZxingActivity : BaseActivity<ActivityZxingBinding>() {
    private lateinit var qrCodeActivityResult: ActivityResultLauncher<Intent>

    override fun createBinding() = ActivityZxingBinding.inflate(layoutInflater)
    override fun initWidget() {

        initToolbar(binding.includedToolbar.toolbar, "Zxing")

        binding.btnScan.setOnClickListener { excuteZxing() }


        qrCodeActivityResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                val data = it.data
                if (data != null) {
                    val content = data.getStringExtra(Constant.CODED_CONTENT)
                    binding.tvResult.text = content
                }

            }
    }


    private fun excuteZxing() =
        getPermissions(PermissionConstants.CAMERA, PermissionConstants.STORAGE) {
            val intent = Intent(this, CaptureActivity::class.java)
            val zxingConfig = ZxingConfig()
            zxingConfig.isFullScreenScan = false
            intent.putExtra(Constant.INTENT_ZXING_CONFIG, zxingConfig)
            qrCodeActivityResult.launch(intent)
        }


}
