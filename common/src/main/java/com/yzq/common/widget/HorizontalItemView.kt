package com.yzq.common.widget

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.yzq.common.R
import kotlinx.android.synthetic.main.view_horizontal_item_layout.view.*


/**
 * @description: 常用的水平item view，包含左边图标、文字和右边图标
 * @author : yzq
 * @date   : 2018/9/1
 * @time   : 14:05
 *
 */

class HorizontalItemView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {


    private var mRootView: View

    private var iconRes: Int
    private var title: String
    private var showIcon: Boolean

    init {
        mRootView = LayoutInflater.from(context).inflate(R.layout.view_horizontal_item_layout, this);

        var typeArr = context.obtainStyledAttributes(attrs, R.styleable.HorizontalItemView)

        try {
            iconRes = typeArr.getResourceId(R.styleable.HorizontalItemView_icon, R.drawable.ic_placeholder_img)
            showIcon = typeArr.getBoolean(R.styleable.HorizontalItemView_showIcon, true)

            title = typeArr.getString(R.styleable.HorizontalItemView_title)

        } finally {
            typeArr.recycle()
        }

        if (showIcon) {
            iconIv.setImageResource(iconRes)
        } else {
            iconIv.visibility = View.GONE
        }

        titleTv.text = title


    }

}