package com.yzq.data

data class DataBindingBean(
    var content: androidx.databinding.ObservableField<String> = androidx.databinding.ObservableField<String>(
        "init value"
    )
) : androidx.databinding.BaseObservable()