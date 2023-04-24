package com.yzq.kotlincommon.ui.activity

import com.blankj.utilcode.util.ToastUtils
import com.drake.brv.utils.setup
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.therouter.router.Route
import com.yzq.base.extend.initToolbar
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.ActivityFlexBoxBinding
import com.yzq.kotlincommon.databinding.ItemTagLayoutBinding

/**
 * @description: 弹性布局
 * @author : yzq
 * @date : 2019/4/30
 * @time : 13:38
 *
 */

@Route(path = RoutePath.Main.FLEX_BOX)
class FlexBoxActivity : BaseActivity() {

    private val binding by viewbind(ActivityFlexBoxBinding::inflate)

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
                    ToastUtils.showShort(getModel<String>())
                }
            }.models =
            arrayListOf("java", "kotlin", "javascript", "php", "android", "go", "python", "flutter")
    }
}
