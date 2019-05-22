package com.yzq.kotlincommon.dagger

import com.yzq.kotlincommon.ui.GaoDeActivity
import com.yzq.kotlincommon.ui.ImageCompressActivity
import com.yzq.kotlincommon.ui.ImageListActivity
import com.yzq.kotlincommon.ui.NewsActivity
import dagger.Component


@Component
interface MainComponent {

    fun inject(mainActivity: NewsActivity)

    fun inject(imageActivity: ImageCompressActivity)
    fun inject(gaoDeActivity: GaoDeActivity)
    fun inject(imageListActivity: ImageListActivity)
}