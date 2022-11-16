package com.yzq.kotlincommon.ui.activity

import com.afollestad.materialdialogs.utils.MDUtil.textChanged
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.yzq.base.ui.activity.BaseVmActivity
import com.yzq.binding.databind
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.ActivityDataBindingBinding
import com.yzq.kotlincommon.view_model.DataBindingViewModel

/**
 * @description: DataBinding
 * @author : XeonYu
 * @date : 2020/11/14
 * @time : 18:44
 */

@Route(path = RoutePath.Main.DATA_BINDING)
class DataBindingActivity : BaseVmActivity<DataBindingViewModel>() {

    private val binding: ActivityDataBindingBinding by databind(R.layout.activity_data_binding)

    override fun getViewModelClass() = DataBindingViewModel::class.java

    override fun initWidget() {
        super.initWidget()
        initToolbar(binding.layoutToolbar.toolbar, "DataBinding")

        binding.etContent.textChanged {
            ToastUtils.showShort(it)
        }
    }

    override fun initData() {
        super.initData()
        vm.resetData()
    }

    override fun observeViewModel() {
        vm.dataBindingLiveData.observe(this) {
            LogUtils.i("数据发生变化了:$it")
            binding.data = it
        }
    }
}
