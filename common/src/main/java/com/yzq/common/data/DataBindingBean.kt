package com.yzq.common.data

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField

data class DataBindingBean(
    var content: ObservableField<String> = ObservableField<String>("init value")
) : BaseObservable()