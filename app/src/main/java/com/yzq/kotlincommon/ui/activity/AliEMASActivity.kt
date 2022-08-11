package com.yzq.kotlincommon.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.ha.adapter.AliHaAdapter
import com.alibaba.ha.adapter.service.tlog.TLogService
import com.blankj.utilcode.util.ToastUtils
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.databinding.ActivityAliEmasactiviyBinding
import com.yzq.lib_base.ui.activity.BaseViewBindingActivity

/**
 * @description: 阿里EMAS测试页面
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2022/1/7
 * @time   : 6:30 下午
 */
@Route(path = RoutePath.Main.EMAS)
class AliEMASActivity : BaseViewBindingActivity<ActivityAliEmasactiviyBinding>() {

    override fun getViewBinding() = ActivityAliEmasactiviyBinding.inflate(layoutInflater)

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