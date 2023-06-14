package com.yzq.kotlincommon.ui.activity

import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.therouter.router.Route
import com.yzq.base.extend.initToolbar
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.databind
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.ActivityDataBindingBinding
import com.yzq.kotlincommon.view_model.DataBindingViewModel
import com.yzq.kotlincommon.widget.edittext.EmojiExcludeFilter

/**
 * @description: DataBinding
 * @author : XeonYu
 * @date : 2020/11/14
 * @time : 18:44
 */

@Route(path = RoutePath.Main.DATA_BINDING)
class DataBindingActivity : BaseActivity() {

    private val binding: ActivityDataBindingBinding by databind(R.layout.activity_data_binding)

    private val vm: DataBindingViewModel by viewModels()

    override fun initWidget() {
        super.initWidget()
        initToolbar(binding.layoutToolbar.toolbar, "DataBinding")

        binding.etContent.filters = arrayOf(EmojiExcludeFilter())
        binding.etContent.doOnTextChanged { text, start, before, count ->
            ToastUtils.showShort(text)
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
