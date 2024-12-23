package com.yzq.dialog.core

import android.view.Gravity
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.GravityInt
import androidx.annotation.StyleRes


/**
 * @description: Dialog的配置类，用于配置Dialog的一些属性，比如宽高，动画，背景等等
 * @author : yuzhiqiang
 */

class DialogConfig {

    var dialogCancelable: Boolean = false
    var dialogWidth: Int = ViewGroup.LayoutParams.WRAP_CONTENT
    var dialogHeight: Int = ViewGroup.LayoutParams.WRAP_CONTENT

    @GravityInt
    var dialogGravity: Int = Gravity.CENTER

    @StyleRes
    var dialogAnimStyle: Int = 0

    /*遮罩层的透明度*/
    var dialogDimAmount: Float = 0.5f

    /*dialog本身的透明度*/
    var dialogAlpha: Float = 1.0f

    /*dialog的背景资源*/
    @DrawableRes
    var dialogBgRes: Int = com.yzq.resource.R.color.transparent


    fun cancelable(cancelable: Boolean): DialogConfig {
        this.dialogCancelable = cancelable
        return this
    }

    fun width(width: Int): DialogConfig {
        this.dialogWidth = width
        return this
    }

    fun height(height: Int): DialogConfig {
        this.dialogHeight = height
        return this
    }

    fun gravity(@GravityInt gravity: Int): DialogConfig {
        this.dialogGravity = gravity
        return this
    }

    fun animStyle(@StyleRes animStyle: Int): DialogConfig {
        this.dialogAnimStyle = animStyle
        return this
    }

    fun dimAmount(dimAmount: Float): DialogConfig {
        this.dialogDimAmount = dimAmount
        return this
    }

    fun alpha(alpha: Float): DialogConfig {
        this.dialogAlpha = alpha
        return this
    }

    fun bgRes(@DrawableRes bgRes: Int): DialogConfig {
        this.dialogBgRes = bgRes
        return this
    }


}