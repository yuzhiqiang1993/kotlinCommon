package com.yzq.kotlincommon.ui.activity

import android.annotation.SuppressLint
import com.hjq.permissions.Permission
import com.therouter.router.Route
import com.yzq.ali.asr.AsrAliCallback
import com.yzq.ali.asr.AsrAliManager
import com.yzq.baseui.BaseActivity
import com.yzq.binding.viewBinding
import com.yzq.core.extend.setOnThrottleTimeClick
import com.yzq.kotlincommon.databinding.ActivityAsrBinding
import com.yzq.permission.getPermissions
import com.yzq.router.RoutePath
import com.yzq.util.ext.initToolbar

@Route(path = RoutePath.Main.ASR_ALI)
class AsrAliActivity : BaseActivity() {

    companion object {
        const val TAG = "AsrAliActivity"
    }

    private val stringBuilder = StringBuilder()

    private val binding by viewBinding(ActivityAsrBinding::inflate)

    private val asrAliCallback: AsrAliCallback = object : AsrAliCallback {
        override fun onResult(result: String, isFinish: Boolean) {
            stringBuilder.appendLine("识别结果：${result},isFinish:${isFinish}")
            binding.tvResult.text = stringBuilder.toString()
        }

        override fun onError(error: String) {
        }

        override fun onPcmData(data: ByteArray, volume: Int) {
        }

    }


    @SuppressLint("MissingPermission")
    override fun initWidget() {

        initToolbar(binding.includedToolbar.toolbar, "阿里实时语音识别")

        binding.btnStart.setOnThrottleTimeClick {

            getPermissions(Permission.RECORD_AUDIO) {
                AsrAliManager.start(asrAliCallback)
            }
        }


        binding.btnStop.setOnThrottleTimeClick {
            AsrAliManager.stop()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        AsrAliManager.release()
    }
}