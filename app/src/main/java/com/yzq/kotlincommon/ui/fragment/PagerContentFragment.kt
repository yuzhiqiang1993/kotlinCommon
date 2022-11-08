package com.yzq.kotlincommon.ui.fragment


import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.yzq.base.ui.fragment.BaseBindingFragment
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.FragmentPagerContentBinding
import com.yzq.kotlincommon.widget.banner.BannerAdapter


/**
 * @description: ViewPager中加载的Fragment
 * @author : yzq
 * @date   : 2019/11/28
 * @time   : 14:38
 */

class PagerContentFragment(var content: String) :
    BaseBindingFragment<FragmentPagerContentBinding>(R.layout.fragment_pager_content) {


    override val bindingBind = FragmentPagerContentBinding::bind


    private val bannerImgs = arrayListOf(
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575192984139&di=bb0fcbcf625b43c8f0ed625dee7a9a41&imgtype=0&src=http%3A%2F%2Fa.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F838ba61ea8d3fd1fc9c7b6853a4e251f94ca5f46.jpg",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575192984139&di=445ac9db2dac3175e14b92d75013c8d0&imgtype=0&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F908fa0ec08fa513db777cf78376d55fbb3fbd9b3.jpg",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575192984139&di=7f7e63bb210c80702b7aa10031bf392d&imgtype=0&src=http%3A%2F%2Ff.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F0e2442a7d933c8956c0e8eeadb1373f08202002a.jpg"
    )


    override fun initWidget() {
        super.initWidget()


        binding.run {
            bannerViewPager
                .setAutoPlay(true)
                .setAdapter(BannerAdapter())
                .setOnPageClickListener { clickedView, position ->
                    LogUtils.i("setOnPageClickListener:${position}")
                    ToastUtils.showShort("click:${position}")
                }
                .create(bannerImgs)



            tvContent.text = content
        }


    }


}
