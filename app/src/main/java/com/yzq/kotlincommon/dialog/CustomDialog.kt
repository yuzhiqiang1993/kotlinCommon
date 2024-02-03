package com.yzq.kotlincommon.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.yzq.base.extend.setOnThrottleTimeClick
import com.yzq.dialog.core.BaseDialogFragment
import com.yzq.dialog.core.DialogConfig
import com.yzq.kotlincommon.databinding.FragmentCustomDialogBinding
import com.yzq.logger.Logger


/**
 * @description: 自定义的DialogFragment
 * @author : yuzhiqiang
 */

class CustomDialog(activity: FragmentActivity) :
    BaseDialogFragment<CustomDialog>(activity) {


    private val binding: FragmentCustomDialogBinding by lazy {
        FragmentCustomDialogBinding.inflate(layoutInflater)
    }

    override fun initWidget(contentView: View) {
        binding.btnConfirm.setOnThrottleTimeClick {
            safeDismiss()
        }

        binding.btnCancel.setOnThrottleTimeClick {
            safeDismiss()
        }
    }

    override fun initView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }


    override fun dialogConfig(config: DialogConfig) {
        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels
        config.apply {
            dialogWidth = screenWidth
            dialogHeight = (screenHeight * 0.4).toInt()
            dialogGravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
            dialogBgRes = com.yzq.dialog.R.drawable.shape_bg_dialog_custom
        }

    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        Logger.i("dialog dismiss")
    }


}