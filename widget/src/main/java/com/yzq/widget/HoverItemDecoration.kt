package com.yzq.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

/**
 * @author : yzq
 * @description: 分组悬停item
 * @date : 2019/3/22
 * @time : 17:49
 */
class HoverItemDecoration(
    private val context: Context,
    private val itemText: (Int) -> String
) : RecyclerView.ItemDecoration() {
    private val width: Int

    /**
     * 分组item的高度
     */
    private val itemHeight: Int

    /**
     * 分割线的高度
     */
    private val itemDivideHeight: Int

    /**
     * 分组text距离左边的距离
     */
    private val itemTextPaddingLeft: Int

    /**
     * 分组item的画笔
     */
    private val itemPaint: Paint

    /**
     * 悬停item的画笔
     */
    private val itemHoverPaint: Paint

    /**
     * 文字的画笔
     */
    private val textPaint: Paint

    /**
     * 绘制文字的矩形边框
     */
    private val textRect = Rect()

    init {
        width = context.resources.displayMetrics.widthPixels
        itemPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        /*
          分组item的颜色
         */
        val itemHoverPaintColor = ContextCompat.getColor(context, R.color.gray_300)
        itemPaint.color = itemHoverPaintColor
        itemHoverPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        itemHoverPaint.color = itemHoverPaintColor
        itemHeight = dp2px(30)
        itemTextPaddingLeft = dp2px(20)
        itemDivideHeight = dp2px(1)
        textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        /*
          分组文字的颜色
         */
        val textPaintColor = ContextCompat.getColor(context, R.color.black)
        textPaint.color = textPaintColor
        textPaint.textSize = sp2px().toFloat()
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val count = parent.childCount
        for (i in 0 until count) {
            val view = parent.getChildAt(i)
            //分组item的顶部和底部
            val itemTop = view.top - itemHeight
            val itemBottom = view.top

            //可见item在adapter中真实的位置
            val position = parent.getChildAdapterPosition(view)

            //获取回调的分组文字（一般是字母）
            val text = itemText(position)

            //如果是一组中第一个的话绘制出分组的item和文字，否则绘制分割线
            if (isFirstInGroup(position)) {
                c.drawRect(0f, itemTop.toFloat(), width.toFloat(), itemBottom.toFloat(), itemPaint)
                drawText(c, itemTop, itemBottom, text)
            } else {
                c.drawRect(
                    0f,
                    (view.top - itemDivideHeight).toFloat(),
                    width.toFloat(),
                    view.top.toFloat(),
                    itemHoverPaint
                )
            }
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        //绘制悬停的view
        val count = parent.childCount
        if (count > 0) {
            //悬停只是第一个位置悬停，所以只取第一个view进行设置
            val firstView = parent.getChildAt(0)
            val position = parent.getChildAdapterPosition(firstView)
            val text = itemText(position)

            //如果悬停view的底部小于悬停布局的高度说明正在上滑，就让他随着滑动逐渐滑进去，否则就固定悬停位置不边
            //isFirstInGroup(position+1)是下一个item是另外分组第一个的时候当前item才滚动上去
            if (firstView.bottom <= itemHeight && isFirstInGroup(position + 1)) {
                c.drawRect(0f, 0f, width.toFloat(), firstView.bottom.toFloat(), itemHoverPaint)
                drawText(c, firstView.bottom - itemHeight, firstView.bottom, text)
            } else {
                c.drawRect(0f, 0f, width.toFloat(), itemHeight.toFloat(), itemHoverPaint)
                drawText(c, 0, itemHeight, text)
            }
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        //如果是分组第一个就留出绘制item的高度
        if (isFirstInGroup(position)) {
            outRect.top = itemHeight
        } else {
            outRect.top = itemDivideHeight
        }
    }

    /**
     * 绘制文字
     *
     * @param canvas 画布
     */
    private fun drawText(canvas: Canvas, itemTop: Int, itemBottom: Int, textString: String) {
        textRect.left = itemTextPaddingLeft
        textRect.top = itemTop
        textRect.right = textString.length
        textRect.bottom = itemBottom
        val fontMetrics = textPaint.fontMetricsInt
        val baseline = (textRect.bottom + textRect.top - fontMetrics.bottom - fontMetrics.top) / 2
        //文字绘制到整个布局的中心位置
        canvas.drawText(textString, textRect.left.toFloat(), baseline.toFloat(), textPaint)
    }

    private fun isFirstInGroup(position: Int): Boolean {
        return if (position == 0) {
            true
        } else {
            val prevItemText = itemText(position - 1)
            val currentItemText = itemText(position)
            //上一个和当前位置的值一样说明是同一个组的否则就是新的一组
            prevItemText != currentItemText
        }
    }

    private fun dp2px(dpVal: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpVal.toFloat(), context.resources.displayMetrics
        ).toInt()
    }

    private fun sp2px(): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            15f,
            context.resources.displayMetrics
        ).toInt()
    }

    interface BindItemTextCallback {
        fun getItemText(position: Int): String
    }
}