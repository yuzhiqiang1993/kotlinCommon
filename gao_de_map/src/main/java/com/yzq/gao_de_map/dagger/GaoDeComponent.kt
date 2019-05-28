package com.yzq.gao_de_map.dagger

import com.yzq.gao_de_map.ui.GaoDeActivity
import dagger.Component


@Component
interface GaoDeComponent {

    fun inject(gaoDeActivity: GaoDeActivity)
}