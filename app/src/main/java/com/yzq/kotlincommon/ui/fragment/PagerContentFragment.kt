package com.yzq.kotlincommon.ui.fragment


import com.yzq.kotlincommon.R
import com.yzq.lib_base.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_pager_content.*


/**
 * @description: ViewPager中加载的Fragment
 * @author : yzq
 * @date   : 2019/11/28
 * @time   : 14:38
 */

class PagerContentFragment(var content: String) : BaseFragment() {


    override fun getContentLayoutId() = R.layout.fragment_pager_content


    override fun initWidget() {
        super.initWidget()

        tv_content.text = content
    }

}
