package com.yzq.login.manager

import com.yzq.login.ui.BaseLoginActivity
import com.yzq.login.ui.BasePopupActivity
import java.util.Stack


/**
 * @description: 页面管理类
 * @author : yuzhiqiang
 */

internal object PageManager {

    const val TAG = "PageManager"

    //用于保存页面的栈
    private val pageStack = Stack<BaseLoginActivity>()


    /**
     * 获取栈顶页面
     * @return BaseActivity?
     */
    fun getTopPage(): BaseLoginActivity? {
        return if (pageStack.isEmpty()) {
            null
        } else {
            pageStack.lastElement()
        }
    }

    //是否有上级页面
    fun hasTopPage(): Boolean {
        return pageStack.size > 1
    }


    fun pushPage(page: BaseLoginActivity) {
        pageStack.add(page)
    }

    fun popPage(page: BaseLoginActivity) {
        pageStack.remove(page)
    }

    /**
     * 结束所有页面，包括当前页面
     */
    fun finishAll() {
        //遍历栈中的页面，从栈底开始关闭效果好一些
        for (i in pageStack.size - 1 downTo 0) {
            //如果是最后一个页面，需要动画
            if (i == 0 && (pageStack[i] is BasePopupActivity)) {
                (pageStack[i] as BasePopupActivity).animateFinish()
            } else {
                pageStack[i].finish()
            }
        }
    }

}