package com.yzq.kotlincommon.view_model

import androidx.lifecycle.MutableLiveData
import com.hjq.toast.Toaster
import com.yzq.baseui.BaseViewModel
import com.yzq.data.DataBindingBean

class DataBindingViewModel : BaseViewModel() {

    val dataBindingLiveData by lazy { MutableLiveData<DataBindingBean>() }

    fun resetData() {
        val dataBindingBean = DataBindingBean()
        dataBindingBean.content.set("init")
        dataBindingLiveData.value = dataBindingBean
    }

    fun getData() {

        Toaster.showShort("当前值:${dataBindingLiveData.value?.content}")
    }
}
