package com.yzq.kotlincommon.ui.activity

import com.alibaba.ha.adapter.AliHaAdapter
import com.alibaba.ha.adapter.service.tlog.TLogService
import com.hjq.toast.Toaster
import com.therouter.router.Route
import com.yzq.baseui.BaseActivity
import com.yzq.binding.viewBinding
import com.yzq.kotlincommon.databinding.ActivityAliEmasactiviyBinding
import com.yzq.router.RoutePath
import com.yzq.util.ext.initToolbar

/**
 * @description: 阿里EMAS测试页面
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date : 2022/1/7
 * @time : 6:30 下午
 */
@Route(path = RoutePath.Main.EMAS)
class AliEMASActivity : BaseActivity() {

    private val binding by viewBinding(ActivityAliEmasactiviyBinding::inflate)

    override fun initWidget() {
        TLogService.logi("APP", "AliEMASActiviy", "initWidget")

        binding.apply {

            initToolbar(toolbar = includedToolbar.toolbar, "EMAS")

            btnCustomReport.setOnClickListener {
                //自定义崩溃上报
                AliHaAdapter.getInstance().reportCustomError(Exception("test exception"))
                Toaster.showLong("已上报")
            }
        }
    }
}
