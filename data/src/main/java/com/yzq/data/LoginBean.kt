package com.yzq.data

data class LoginBean(
    var account: androidx.databinding.ObservableField<String> = androidx.databinding.ObservableField(
        ""
    ),
    var pwd: androidx.databinding.ObservableField<String> = androidx.databinding.ObservableField("")
) : androidx.databinding.BaseObservable()
