package com.yzq.kotlincommon.ui.activity

import com.alibaba.ha.adapter.AliHaAdapter
import com.alibaba.ha.adapter.service.tlog.TLogService
import com.blankj.utilcode.util.ToastUtils
import com.therouter.router.Route
import com.yzq.base.extend.initToolbar
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.databinding.ActivityAliEmasactiviyBinding

/**
 * @description: 阿里EMAS测试页面
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date : 2022/1/7
 * @time : 6:30 下午
 */
@Route(path = RoutePath.Main.EMAS)
class AliEMASActivity : BaseActivity() {

    private val binding by viewbind(ActivityAliEmasactiviyBinding::inflate)

    override fun initWidget() {
        TLogService.logi("APP", "AliEMASActiviy", "initWidget")

        binding.apply {

            initToolbar(toolbar = toolbar.toolbar, "EMAS")

            btnCustomReport.setOnClickListener {
                /*自定义崩溃上报*/
                AliHaAdapter.getInstance().reportCustomError(Exception("test exception"))
                ToastUtils.showLong("已上报")
            }
        }
    }
}
