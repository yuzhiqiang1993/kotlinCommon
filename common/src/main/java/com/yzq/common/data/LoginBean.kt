package com.yzq.common.data

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.yzq.common.BR

class LoginBean : BaseObservable() {

    @Bindable
    var account: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.account)
        }


    @Bindable
    var pwd: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.pwd)
        }
}
