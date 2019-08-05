package com.yzq.kotlincommon.dagger

import com.yzq.kotlincommon.ui.ImageCompressActivity
import com.yzq.kotlincommon.ui.ImageListActivity
import com.yzq.kotlincommon.ui.MoviesActivity
import com.yzq.kotlincommon.ui.MoviesVmActivity
import dagger.Component


@Component
interface MainComponent {

    fun inject(moviesActivity: MoviesActivity)
    fun inject(moviesVmActivity: MoviesVmActivity)

    fun inject(imageActivity: ImageCompressActivity)
    fun inject(imageListActivity: ImageListActivity)
}