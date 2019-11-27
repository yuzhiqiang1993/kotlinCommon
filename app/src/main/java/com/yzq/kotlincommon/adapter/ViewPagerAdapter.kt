package com.yzq.kotlincommon.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yzq.kotlincommon.R
import kotlinx.android.synthetic.main.item_view_pager.view.*


/**
 * @description: ViewPagerAdapter
 * @author : yzq
 * @date   : 2019/11/27
 * @time   : 13:38
 */

class ViewPagerAdapter(layoutResId: Int, data: MutableList<String>) :
    BaseQuickAdapter<String, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: String) {

        helper.setText(R.id.tv_pager_content, item)


    }
}