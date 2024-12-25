package com.yzq.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.yzq.dialog.core.BaseDialogFragment
import com.yzq.dialog.core.DialogConfig
import com.yzq.dialog.databinding.LayoutPromptDialogBinding


/**
 * @description: 弹窗
 * @author : yuzhiqiang
 */

class PromptDialog(activity: FragmentActivity) : BaseDialogFragment<PromptDialog>(activity) {

    private var title: String = ""
    private var content: String = ""
    private var positiveText: String = "确定"
    private var negativeText: String = "取消"
    private var singlePositiveBtn: Boolean = false
    private var positiveListener: View.OnClickListener? = null
    private var negativeListener: View.OnClickListener? = null


    private val binding: LayoutPromptDialogBinding by lazy {
        LayoutPromptDialogBinding.inflate(layoutInflater)
    }

    override fun initWidget(contentView: View) {

        binding.tvTitle.text = title
        if (title.isEmpty()) {
            binding.tvTitle.visibility = View.GONE
        } else {
            binding.tvTitle.visibility = View.VISIBLE
        }

        binding.tvContent.text = content
        binding.btnPositive.text = positiveText
        binding.btnPositive.setOnClickListener {
            positiveListener?.onClick(it)
            safeDismiss()
        }

        if (singlePositiveBtn) {
            binding.btnNegative.visibility = View.GONE
        } else {
            binding.btnNegative.text = negativeText
            binding.btnNegative.setOnClickListener {
                negativeListener?.onClick(it)
                safeDismiss()
            }
        }

    }


    override fun initView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }


    override fun dialogConfig(config: DialogConfig) {
        super.dialogConfig(config)
        config.cancelable(false)
        config.bgRes(R.drawable.dialog_background)
        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        config.dialogWidth = (screenWidth * 0.8).toInt()

//        binding?.root?.layoutParams = LayoutParams(config.dialogWidth, LayoutParams.WRAP_CONTENT)
    }


    fun title(title: String): PromptDialog {
        this.title = title
        return this
    }


    fun content(content: String): PromptDialog {
        this.content = content
        return this
    }

    fun positiveBtn(text: String = "确定", listener: View.OnClickListener): PromptDialog {
        this.positiveText = text
        this.positiveListener = listener
        return this
    }

    fun negativeBtn(text: String = "取消", listener: View.OnClickListener? = null): PromptDialog {
        this.negativeText = text
        this.negativeListener = listener
        return this
    }

    fun singlePositiveBtn(text: String = "确定", listener: View.OnClickListener): PromptDialog {
        singlePositiveBtn = true
        this.positiveText = text
        this.positiveListener = listener
        return this
    }


}