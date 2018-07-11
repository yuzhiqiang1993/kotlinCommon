package com.yzq.common.dagger.component

import com.yzq.common.ui.BaseFragment
import dagger.Component

@Component
interface FragmentComponent {

    fun inject(baseFragment: BaseFragment)
}