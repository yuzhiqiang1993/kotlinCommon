package com.yzq.common.data

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField

data class LoginBean(
    var account: ObservableField<String> = ObservableField(""),
    var pwd: ObservableField<String> = ObservableField("")
) : BaseObservable()
