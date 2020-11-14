package com.yzq.common.data

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.yzq.common.BR

class DataBindingBean : BaseObservable() {


    @Bindable
    var content: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.content)

        }


}