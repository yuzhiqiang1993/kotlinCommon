package com.yzq.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RawRes
import androidx.appcompat.app.AppCompatActivity
import com.yzq.dialog.core.BaseDialogFragment
import com.yzq.dialog.databinding.LayoutLottieDialogBinding


/**
 * @description: 加载弹窗，一般用于网络请求时的加载提示，不可取消，只能等待请求完成后关闭，否则会导致内存泄漏
 * @author : yuzhiqiang
 */
class LottieDialog(activity: AppCompatActivity) : BaseDialogFragment<LottieDialog>(activity) {


    //本地的加载动画文件,兜底用
    private var lottieRes: Int = R.raw.chicky_loading
    private var lottieUrl: String? = null


    private val binding: LayoutLottieDialogBinding by lazy {
        LayoutLottieDialogBinding.inflate(layoutInflater)
    }

    override fun initView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }


    override fun initWidget(contentView: View) {
        if (lottieUrl != null) {
            binding.lottieView.setAnimationFromUrl(lottieUrl)
        } else {
            binding.lottieView.setAnimation(lottieRes)
        }

    }


    fun loadLottieRes(@RawRes res: Int): LottieDialog {
        this.lottieRes = res
        return this
    }

    fun lottieUrl(url: String): LottieDialog {
        this.lottieUrl = url
        return this
    }


}