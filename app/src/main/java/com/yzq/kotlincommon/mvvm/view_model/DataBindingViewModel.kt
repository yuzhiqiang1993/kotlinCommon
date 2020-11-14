package com.yzq.kotlincommon.mvvm.view_model

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.ToastUtils
import com.yzq.common.data.DataBindingBean
import com.yzq.lib_base.view_model.BaseViewModel

class DataBindingViewModel : BaseViewModel() {


    val dataBindingLiveData by lazy { MutableLiveData<DataBindingBean>() }


    fun resetData() {

        val dataBindingBean = DataBindingBean()
        dataBindingBean.content = "init"

        dataBindingLiveData.value = dataBindingBean

    }

    fun getData() {

        ToastUtils.showShort("当前值:${dataBindingLiveData.value?.content}")
    }

}