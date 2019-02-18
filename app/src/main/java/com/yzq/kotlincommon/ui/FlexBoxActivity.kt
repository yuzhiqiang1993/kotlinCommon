package com.yzq.kotlincommon.ui

import android.view.View
import androidx.appcompat.widget.Toolbar
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.yzq.common.constants.RoutePath
import com.yzq.common.ui.BaseActivity
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.adapter.TagAdapter
import kotlinx.android.synthetic.main.activity_flex_box.*

@Route(path = RoutePath.Main.FLEX_BOX)
class FlexBoxActivity : BaseActivity(), BaseQuickAdapter.OnItemClickListener {

    private lateinit var tagAdapter: TagAdapter
    override fun getContentLayoutId(): Int {
        return R.layout.activity_flex_box
    }


    override fun initWidget() {
        super.initWidget()
        val toolbar = this.findViewById<Toolbar>(R.id.toolbar)
        initToolbar(toolbar, "FlexBox")

        val flexManager = FlexboxLayoutManager(this)
        flexManager.flexWrap = FlexWrap.WRAP
        flexManager.alignItems = AlignItems.FLEX_START
        recy.layoutManager = flexManager


    }


    private lateinit var tags: ArrayList<String>

    override fun initData() {
        super.initData()

        tags = arrayListOf("java", "kotlin", "javascript", "php", "android", "go", "python", "flutter")
        tagAdapter = TagAdapter(R.layout.item_tag_layout, tags)
        tagAdapter.setOnItemClickListener(this)
        recy.adapter = tagAdapter
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {

        ToastUtils.showShort(tags[position])


    }


}
