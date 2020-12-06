package com.yzq.kotlincommon.ui.activity

import androidx.databinding.DataBindingUtil
import com.afollestad.materialdialogs.utils.MDUtil.textChanged
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.ActivityDataBindingBinding
import com.yzq.kotlincommon.mvvm.view_model.DataBindingViewModel
import com.yzq.lib_base.ui.BaseMvvmActivity
import kotlinx.android.synthetic.main.activity_data_binding.*


/**
 * @description: DataBinding
 * @author : XeonYu
 * @date   : 2020/11/14
 * @time   : 18:44
 */

@Route(path = RoutePath.Main.DATA_BINDING)
class DataBindingActivity : BaseMvvmActivity<DataBindingViewModel>() {


    private lateinit var binding: ActivityDataBindingBinding

    override fun initContentView() {
        binding = DataBindingUtil.setContentView<ActivityDataBindingBinding>(
            this,
            R.layout.activity_data_binding
        )
    }

    override fun getViewModelClass() = DataBindingViewModel::class.java

    override fun initWidget() {
        super.initWidget()
        et_content.textChanged {
            ToastUtils.showShort(it)
        }

    }

    override fun initData() {
        super.initData()
        vm.resetData()
    }

    override fun observeViewModel() {
        vm.dataBindingLiveData.observe(this, {
            LogUtils.i("数据发生变化了:${it}")
            binding.data = it
        })
    }


}