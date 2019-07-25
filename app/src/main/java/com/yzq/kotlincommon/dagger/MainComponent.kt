package com.yzq.kotlincommon.dagger

import com.yzq.kotlincommon.ui.ImageCompressActivity
import com.yzq.kotlincommon.ui.ImageListActivity
import com.yzq.kotlincommon.ui.MoviesActivity
import dagger.Component


@Component
interface MainComponent {

    fun inject(mainActivity: MoviesActivity)

    fun inject(imageActivity: ImageCompressActivity)
    fun inject(imageListActivity: ImageListActivity)
}