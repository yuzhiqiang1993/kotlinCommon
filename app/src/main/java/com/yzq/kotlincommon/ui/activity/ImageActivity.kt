package com.yzq.kotlincommon.ui.activity

import coil.load
import coil.transform.RoundedCornersTransformation
import com.therouter.router.Route
import com.yzq.baseui.BaseActivity
import com.yzq.binding.viewBinding
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.ActivityImageBinding
import com.yzq.router.RoutePath
import com.yzq.util.ext.initToolbar


/**
 * @description 图片加载
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 */
@Route(path = RoutePath.Main.IMAGE_LOAD)
class ImageActivity : BaseActivity() {

    private val binding by viewBinding(ActivityImageBinding::inflate)

    override fun initWidget() {

        with(binding) {
            initToolbar(layoutToolbar.toolbar, "图片加载")

            /*加载gif*/
            ivGif.load("https://s1.aigei.com/src/img/gif/a2/a23e1935964d4465bcfe1f17625fc8a6.gif?imageMogr2/auto-orient/thumbnail/!240x225r/gravity/Center/crop/240x225/quality/85/&e=1735488000&token=P7S2Xpzfz11vAkASLTkfHN7Fw-oOZBecqeJaxypL:jwsq63qB0OOfuMOr-KKnCye7ln4=")
            /*加载svg*/
            ivSvg.load(R.drawable.ic_add)
            /*圆角图片*/
            ivRound.load(R.drawable.ic_splash) {
                transformations(RoundedCornersTransformation(80f))
            }

        }

    }

}