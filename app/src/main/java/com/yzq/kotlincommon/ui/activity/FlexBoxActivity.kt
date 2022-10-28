package com.yzq.kotlincommon.ui.activity

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.adapter.TagAdapter
import com.yzq.kotlincommon.databinding.ActivityFlexBoxBinding


/**
 * @description: 弹性布局
 * @author : yzq
 * @date   : 2019/4/30
 * @time   : 13:38
 *
 */

@Route(path = RoutePath.Main.FLEX_BOX)
class FlexBoxActivity : BaseActivity<ActivityFlexBoxBinding>(), OnItemClickListener {

    private lateinit var tagAdapter: TagAdapter

    override fun createBinding() = ActivityFlexBoxBinding.inflate(layoutInflater)


    override fun initWidget() {
        super.initWidget()

        initToolbar(binding.layoutToolbar.toolbar, "FlexBox")

        val flexManager = FlexboxLayoutManager(this)
        flexManager.flexWrap = FlexWrap.WRAP
        flexManager.alignItems = AlignItems.FLEX_START
        binding.recy.layoutManager = flexManager


    }


    private lateinit var tags: ArrayList<String>

    override fun initData() {
        super.initData()

        tags =
            arrayListOf("java", "kotlin", "javascript", "php", "android", "go", "python", "flutter")
        tagAdapter = TagAdapter(R.layout.item_tag_layout, tags)
        tagAdapter.setOnItemClickListener(this)
        binding.recy.adapter = tagAdapter
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {

        ToastUtils.showShort(tags[position])


    }


}
