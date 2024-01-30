package com.yzq.kotlincommon.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.FragmentActivity
import com.yzq.base.extend.setOnThrottleTimeClick
import com.yzq.dialog.BaseDialogFragment
import com.yzq.kotlincommon.databinding.FragmentCustomDialogBinding
import com.yzq.logger.Logger


/**
 * @description: 自定义的DialogFragment
 * @author : yuzhiqiang
 */

class CustomDialogFragment private constructor() : BaseDialogFragment() {


    companion object {
        @JvmStatic
        fun newInstance(activity: FragmentActivity) = CustomDialogFragment().apply {
            this.hostActivity = activity

        }
    }


    val binding: FragmentCustomDialogBinding by lazy {
        FragmentCustomDialogBinding.inflate(layoutInflater)
    }


    override fun initView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding.btnConfirm.setOnThrottleTimeClick {
            safeDismiss()
        }

        binding.btnCancel.setOnThrottleTimeClick {
            safeDismiss()
        }
        return binding.root
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        Logger.i("dialog dismiss")
    }

    override fun applyDialogWindow(window: Window) {
        // 获取屏幕尺寸
        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels
        // 计算对话框的宽度和高度
        dialogWidth = (screenWidth * 1).toInt() // 宽度
        dialogHeight = (screenHeight * 0.4).toInt() // 高度
//        dialogDimAmount = 0.0f
        dialogGravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
        Logger.i("dialogWidth:$dialogWidth,dialogHeight:$dialogHeight")
        dialogBgRes = com.yzq.dialog.R.drawable.shape_bg_dialog_custom
        super.applyDialogWindow(window)

    }


}