package com.yzq.kotlincommon.dagger

import com.yzq.kotlincommon.ui.MainActivity
import dagger.Component


@Component
interface MainComponent {

    fun inject(mainActivity: MainActivity)
}