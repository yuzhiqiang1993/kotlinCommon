package com.yzq.widget.drop_down_menu

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.content.withStyledAttributes
import com.blankj.utilcode.util.LogUtils
import com.yzq.widget.R
import com.yzq.widget.drop_down_menu.DeviceUtils.getScreenHeight

/**
 * @description 下拉菜单
 * @author yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date 2022/11/11
 * @time 18:26
 */

class DropDownMenu @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) :
    LinearLayout(
        context,
        attrs,
        defStyleAttr
    ) {
    // 顶部菜单布局
    private var tabMenuContainer: LinearLayout

    // 底部容器，包含popupMenuViews，maskView
    private var contentContainer: FrameLayout

    // 弹出菜单父布局
    private lateinit var popupMenuContainer: FrameLayout

    // 遮罩半透明View，点击可关闭DropDownMenu
    private lateinit var maskView: View

    // tabMenuView里面选中的tab位置，-1表示未选中
    private var currentTabPosition = -1

    // tab选中时文字的颜色
    private var textSelectedColor = Color.BLACK

    // tab未选中颜色
    private var textUnselectedColor = Color.DKGRAY

    // 遮罩颜色
    private var maskColor = Color.LTGRAY

    // tab字体大小
    private var menuTextSize = 14

    // tab选中图标
    private var menuSelectedIcon = 0

    // tab未选中图标
    private var menuUnselectedIcon = 0

    /*内容高度百分比*/
    private var menuHeightPercent = 0.5f

    /*menu的背景颜色*/
    private var menuBackgroundColor = Color.WHITE

    init {
        /*垂直排列*/
        orientation = VERTICAL
        context.withStyledAttributes(attrs, R.styleable.DropDownMenu) {
            textSelectedColor =
                getColor(R.styleable.DropDownMenu_textSelectedColor, textSelectedColor)
            textUnselectedColor =
                getColor(R.styleable.DropDownMenu_textUnselectedColor, textUnselectedColor)
            menuBackgroundColor =
                getColor(R.styleable.DropDownMenu_menuBackgroundColor, menuBackgroundColor)
            maskColor = getColor(R.styleable.DropDownMenu_maskColor, maskColor)
            menuTextSize =
                getDimensionPixelSize(R.styleable.DropDownMenu_menuTextSize, menuTextSize)
            menuSelectedIcon =
                getResourceId(R.styleable.DropDownMenu_menuSelectedIcon, menuSelectedIcon)
            menuUnselectedIcon =
                getResourceId(R.styleable.DropDownMenu_menuUnselectedIcon, menuUnselectedIcon)
            menuHeightPercent =
                getFloat(R.styleable.DropDownMenu_menuMenuHeightPercent, menuHeightPercent)
        }

        // 创建一个Linnerlayout存放tabItem
        tabMenuContainer = LinearLayout(context)
        val params =
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        tabMenuContainer.run {
            orientation = HORIZONTAL
            setBackgroundColor(menuBackgroundColor)
            layoutParams = params
        }
        addView(tabMenuContainer, 0)
        /*创建一个FrameLayout用来存放内容*/
        contentContainer = FrameLayout(context)
        contentContainer.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        /*放在tabContainer下面*/
        addView(contentContainer, 1)
    }

    /**
     * 初始化DropDownMenu
     *
     * @param tabTexts tabItem显示的标题
     * @param popupViews  对应要显示浮在上层的的view
     * @param contentView  底部的内容view
     */
    fun setDropDownMenu(tabTexts: List<String>, popupViews: List<View>, contentView: View) {
        require(tabTexts.size == popupViews.size) { "params not match, tabTexts.size() should be equal popupViews.size()" }

        /*添加item*/
        tabTexts.forEach {
            addTabItem(it)
        }
        /*先添加内容view,让它在地底部*/
        contentContainer.addView(contentView, 0)
        /*创建遮罩view*/
        maskView = View(context)
        /*配置属性*/
        maskView.run {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            setBackgroundColor(maskColor)
            setOnClickListener { v: View? -> closeMenu() }
            visibility = GONE
        }
        /*添加到内容布局中 让它浮在内部上方*/
        contentContainer.addView(maskView, 1)

        /*先移除遮罩上方的view*/
        if (contentContainer.getChildAt(2) != null) {
            contentContainer.removeViewAt(2)
        }
        /*最上方的弹出菜单容器*/
        popupMenuContainer = FrameLayout(context)

        LogUtils.i("contentContainer.height:${contentContainer.height}")
        /*高度设置为指定的百分比  底部就会显示出下面的遮罩view*/
        popupMenuContainer.layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            (getScreenHeight(context) * menuHeightPercent).toInt()
        )
        /*先隐藏*/
        popupMenuContainer.visibility = GONE
        /*添加到内容布局中  层级在最上方*/
        contentContainer.addView(popupMenuContainer, 2)

        /*设置弹出菜单里的内容view,依次添加进去*/
        popupViews.forEach {
            it.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            popupMenuContainer.addView(it)
        }
    }

    private fun addTabItem(titleStr: String) {
        val tabItem = TabItem(context)
        tabItem.setTitle(titleStr)
        tabItem.layoutParams = LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f)
        tabItem.setOnClickListener { switchMenu(tabItem) }
        tabMenuContainer.addView(tabItem)
    }

    /**
     * 改变tab文字
     *
     * @param text
     */
    fun setTabText(text: String?) {
        if (currentTabPosition != -1) {
            val tabItem = tabMenuContainer.getChildAt(currentTabPosition) as TabItem
            tabItem.setTitle(text!!)
        }
    }

    fun setTabClickable(clickable: Boolean) {
        var i = 0
        while (i < tabMenuContainer.childCount) {
            tabMenuContainer.getChildAt(i).isClickable = clickable
            i += 2
        }
    }

    /**
     * 关闭菜单
     */
    fun closeMenu() {
        if (currentTabPosition != -1) {
            val tabItem = tabMenuContainer.getChildAt(currentTabPosition) as TabItem
            tabItem.setTitleColor(textUnselectedColor)
            tabItem.setIcon(menuUnselectedIcon, textUnselectedColor)
            popupMenuContainer.visibility = GONE
            popupMenuContainer.animation = AnimationUtils.loadAnimation(
                context,
                R.anim.dd_menu_out
            )
            maskView.visibility = GONE
            maskView.animation = AnimationUtils.loadAnimation(
                context,
                R.anim.dd_mask_out
            )
            currentTabPosition = -1
        }
    }

    /**
     * DropDownMenu是否处于可见状态
     *
     * @return
     */
    val isShowing: Boolean
        get() = currentTabPosition != -1

    /**
     * 切换菜单
     *
     * @param target
     */
    private fun switchMenu(target: View) {
        /*先隐藏其他选项*/
        for (i in 0 until tabMenuContainer.childCount) {
            val currentTabItem = tabMenuContainer.getChildAt(i) as TabItem
            if (target === tabMenuContainer.getChildAt(i)) {

                /*点击的是已经选中的item*/
                if (currentTabPosition == i) {
                    closeMenu()
                } else {
                    /*点击的是其他item*/
                    if (currentTabPosition == -1) {
                        /*当下拉菜单处于关闭状态时 显示*/
                        popupMenuContainer.visibility = VISIBLE
                        popupMenuContainer.animation = AnimationUtils.loadAnimation(
                            context,
                            R.anim.dd_menu_in
                        )
                        /*遮罩层执行动画显示*/
                        maskView.visibility = VISIBLE
                        maskView.animation = AnimationUtils.loadAnimation(
                            context,
                            R.anim.dd_mask_in
                        )
                    }
                    /*显示下拉菜单里的对应内容*/
                    popupMenuContainer.getChildAt(i).visibility = VISIBLE
                    currentTabItem.setTitleColor(textSelectedColor)
                    currentTabItem.setIcon(menuSelectedIcon, textSelectedColor)
                    currentTabPosition = i
                }
            } else {
                /*隐藏其他的popupMenuViews*/
                popupMenuContainer.getChildAt(i).visibility = GONE
                currentTabItem.setTitleColor(textUnselectedColor)
                currentTabItem.setIcon(menuUnselectedIcon, textUnselectedColor)
            }
        }
    }
}
