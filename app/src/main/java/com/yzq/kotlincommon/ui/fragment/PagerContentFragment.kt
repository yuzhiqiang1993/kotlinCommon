package com.yzq.kotlincommon.ui.fragment

import com.hjq.toast.Toaster
import com.yzq.base.ui.fragment.BaseFragment
import com.yzq.binding.viewBinding
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.FragmentPagerContentBinding
import com.yzq.kotlincommon.widget.banner.BannerAdapter
import com.yzq.logger.Logger

/**
 * @description: ViewPager中加载的Fragment
 * @author : yzq
 * @date : 2019/11/28
 * @time : 14:38
 */

class PagerContentFragment(val content: String) : BaseFragment(R.layout.fragment_pager_content) {

    private val binding by viewBinding(FragmentPagerContentBinding::bind)

    private val bannerImgs = arrayListOf(
        "http://gips2.baidu.com/it/u=195724436,3554684702&fm=3028&app=3028&f=JPEG&fmt=auto?w=1280&h=960",
        "http://gips0.baidu.com/it/u=3602773692,1512483864&fm=3028&app=3028&f=JPEG&fmt=auto?w=960&h=1280",
        "http://gips3.baidu.com/it/u=3419425165,837936650&fm=3028&app=3028&f=JPEG&fmt=auto?w=1024&h=1024"
    )

    override fun initWidget() {
        super.initWidget()
        binding.run {
            bannerViewPager.setAutoPlay(true).setAdapter(BannerAdapter())
                .setOnPageClickListener { clickedView, position ->
                    Logger.i("setOnPageClickListener:$position")
                    Toaster.showShort("click:$position")
                }.create(bannerImgs)

            tvContent.text = content
        }
    }


}
