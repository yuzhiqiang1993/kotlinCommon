package com.yzq.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.yzq.dialog.core.BaseDialogFragment
import com.yzq.dialog.core.DialogConfig
import com.yzq.dialog.databinding.LayoutProgressDialogBinding


/**
 * @description: 弹窗
 * @author : yuzhiqiang
 */

class ProgressDialog(activity: FragmentActivity) : BaseDialogFragment<ProgressDialog>(activity) {


    private var title: String = ""

    private var currentProgress: Int = 0

    private val binding: LayoutProgressDialogBinding by lazy {
        LayoutProgressDialogBinding.inflate(layoutInflater)
    }

    override fun initWidget(contentView: View) {
        binding.tvTitle.text = this.title
        binding.progressHorizontal.progress = currentProgress
        binding.currentProgressTv.text = "${currentProgress}"
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

    }


    fun changeProgress(progress: Int): ProgressDialog {
        currentProgress = progress
        runCatching {
            binding.progressHorizontal.progress = progress
            binding.currentProgressTv.text = "$progress"
        }

        return this
    }

    fun changeTitle(title: String): ProgressDialog {
        this.title = title
        runCatching {
            binding.tvTitle.text = title
        }
        return this


    }


}