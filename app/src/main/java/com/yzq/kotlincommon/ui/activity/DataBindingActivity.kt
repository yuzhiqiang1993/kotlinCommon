package com.yzq.kotlincommon.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.ActivityDataBindingBinding
import com.yzq.kotlincommon.mvvm.view_model.DataBindingViewModel
import kotlinx.android.synthetic.main.activity_data_binding.*


/**
 * @description: DataBinding
 * @author : XeonYu
 * @date   : 2020/11/14
 * @time   : 18:44
 */

@Route(path = RoutePath.Main.DATA_BINDING)
class DataBindingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val vm = ViewModelProvider(this).get(DataBindingViewModel::class.java)

        val binding = DataBindingUtil.setContentView<ActivityDataBindingBinding>(
            this,
            R.layout.activity_data_binding
        )


        vm.dataBindingLiveData.observe(this, {
            LogUtils.i("数据发生变化了:${it}")
            binding.data = it
        })


        btn_reset.setOnClickListener {
            vm.resetData()
        }


    }
}