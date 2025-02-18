package com.yzq.kotlincommon.ui.activity

import com.drake.brv.utils.setup
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.hjq.toast.Toaster
import com.therouter.router.Route
import com.yzq.base.extend.initToolbar
import com.yzq.baseui.BaseActivity
import com.yzq.binding.viewBinding
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.ActivityFlexBoxBinding
import com.yzq.kotlincommon.databinding.ItemTagLayoutBinding
import com.yzq.router.RoutePath

/**
 * @description: 弹性布局
 * @author : yzq
 * @date : 2019/4/30
 * @time : 13:38
 *
 */

@Route(path = RoutePath.Main.FLEX_BOX)
class FlexBoxActivity : BaseActivity() {

    private val binding by viewBinding(ActivityFlexBoxBinding::inflate)

    override fun initWidget() {
        super.initWidget()

        initToolbar(binding.layoutToolbar.toolbar, "FlexBox")

        val flexManager = FlexboxLayoutManager(this)
        flexManager.flexWrap = FlexWrap.WRAP
        flexManager.alignItems = AlignItems.FLEX_START
        binding.recy.layoutManager = flexManager
    }

    override fun initData() {
        super.initData()

        binding.recy
            .setup {
                addType<String>(R.layout.item_tag_layout)
                onBind {
                    val itemBinding = getBinding<ItemTagLayoutBinding>()
                    itemBinding.tvTagName.text = getModel<String>()
                }

                R.id.tv_tag_name.onClick {
                    Toaster.showShort(getModel<String>())
                }
            }.models =
            arrayListOf("java", "kotlin", "javascript", "php", "android", "go", "python", "flutter")
    }
}
