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

class HorizontalItemView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
        ConstraintLayout(context, attrs, defStyleAttr) {


    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    private var iconRes: Int
    private var title: String? = ""
    private var content: String? = ""
    private var showIcon: Boolean

    init {

        LayoutInflater.from(context).inflate(R.layout.view_horizontal_item_layout, this);


        var typeArr = context.obtainStyledAttributes(attrs, R.styleable.HorizontalItemView)

        try {
            iconRes = typeArr.getResourceId(R.styleable.HorizontalItemView_HI_icon, R.drawable.ic_placeholder_img)
            showIcon = typeArr.getBoolean(R.styleable.HorizontalItemView_HI_showIcon, true)

            title = typeArr.getString(R.styleable.HorizontalItemView_HI_title)
            content = typeArr.getString(R.styleable.HorizontalItemView_HI_content)

        } finally {
            typeArr.recycle()
        }

        if (showIcon) {
            iconIv.setImageResource(iconRes)
        } else {
            iconIv.visibility = View.GONE
        }

        titleTv.text = title
        contentTv.text = content

    }


    fun setContent(content: String) {
        contentTv.text = content
    }

    fun setTitle(title: String) {
        titleTv.text = title
    }

}