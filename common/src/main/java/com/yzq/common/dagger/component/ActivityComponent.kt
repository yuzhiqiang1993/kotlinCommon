package com.yzq.common.dagger.component

import com.yzq.common.ui.BaseActivity
import dagger.Component


@Component
interface ActivityComponent {

    fun inject(baseActivity: BaseActivity)
}