package com.yzq.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RawRes
import androidx.fragment.app.FragmentActivity
import com.yzq.dialog.core.BaseDialogFragment
import com.yzq.dialog.core.DialogConfig
import com.yzq.dialog.databinding.LottieLoadingDialogLayoutBinding
import com.yzq.logger.Logger


/**
 * @description: 加载弹窗，一般用于网络请求时的加载提示，不可取消，只能等待请求完成后关闭，否则会导致内存泄漏
 * @author : yuzhiqiang
 */
class LottieLoadingDialog(activity: FragmentActivity) :
    BaseDialogFragment<LottieLoadingDialog>(activity) {


    //本地的加载动画文件,兜底用
    private var lottieRes: Int = R.raw.chicky_loading
    private var lottieUrl: String? = null


    private val binding: LottieLoadingDialogLayoutBinding by lazy {
        LottieLoadingDialogLayoutBinding.inflate(layoutInflater)
    }


    override fun initView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        Logger.i("initView")
//        val url = "https://assets7.lottiefiles.com/packages/lf20_5lTxAupekw.json"
//        binding.lottieView.setBackgroundColor(Color.parseColor("#3490dc"))
//        binding.lottieView.setAnimationFromUrl(url)

        //如果是本地文件

        if (lottieUrl != null) {
            binding.lottieView.setAnimationFromUrl(lottieUrl)
        } else {
            binding.lottieView.setAnimation(lottieRes)
        }
        return binding.root
    }

    override fun dialogConfig(config: DialogConfig) {
        super.dialogConfig(config)
    }


    fun loadLottieRes(@RawRes res: Int): LottieLoadingDialog {
        this.lottieRes = res
        return this
    }

    fun lottieUrl(url: String): LottieLoadingDialog {
        this.lottieUrl = url
        return this
    }


}