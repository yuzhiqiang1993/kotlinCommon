package com.yzq.kotlincommon.dagger

import com.yzq.kotlincommon.ui.GaoDeActivity
import com.yzq.kotlincommon.ui.ImageActivity
import com.yzq.kotlincommon.ui.NewsActivity
import dagger.Component


@Component
interface MainComponent {

    fun inject(mainActivity: NewsActivity)

    fun inject(imageActivity: ImageActivity)
    fun inject(gaoDeActivity: GaoDeActivity)
}