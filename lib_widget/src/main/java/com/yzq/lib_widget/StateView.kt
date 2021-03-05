package com.yzq.lib_widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.yzq.lib_widget.databinding.LayoutStateViewBinding


/**
 * @description: 自定义的状态布局
 * @author : yzq
 * @date   : 2018/7/13
 * @time   : 13:43
 *
 */


typealias RetryListener = () -> Unit

class StateView(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
) :
        LinearLayout(context, attrs, defStyleAttr) {


    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)


    /*无网络图片*/
    private var noNetImgRes = R.drawable.ic_no_net

    /*无数据图片*/
    private var noDataImgRes = R.drawable.ic_no_data

    /*出现错误图片*/
    private var errorImgRes = R.drawable.ic_error

    private val binding: LayoutStateViewBinding

    init {

        binding = LayoutStateViewBinding.inflate(LayoutInflater.from(context), this, true)
        val typeArr = context.obtainStyledAttributes(attrs, R.styleable.StateView)

        try {

            noNetImgRes =
                    typeArr.getResourceId(R.styleable.StateView_no_net_img_res, R.drawable.ic_no_net)
            noDataImgRes =
                    typeArr.getResourceId(R.styleable.StateView_no_data_img_res, R.drawable.ic_no_data)
            errorImgRes =
                    typeArr.getResourceId(R.styleable.StateView_error_img_res, R.drawable.ic_error)

        } finally {
            typeArr.recycle()
        }




        showLoading()
    }


    fun showLoading() {


        binding.root.visibility = View.VISIBLE

        binding.loadingLayout.visibility = View.VISIBLE


        binding.abnormaLayout.visibility = View.GONE
    }


    fun showNoData() {

        binding.root.visibility = View.VISIBLE
        binding.loadingLayout.visibility = View.GONE
        binding.abnormaLayout.visibility = View.VISIBLE

        binding.ivHint.setImageResource(noDataImgRes)

        binding.tvHint.text = resources.getString(R.string.no_data)
    }

    fun showNoNet() {
        binding.root.visibility = View.VISIBLE
        binding.loadingLayout.visibility = View.GONE
        binding.abnormaLayout.visibility = View.VISIBLE
        binding.ivHint.setImageResource(noNetImgRes)
        // ImageLoader.getInstance().load(context, R.drawable.ic_no_net, hintImg);
        binding.tvHint.text = resources.getString(R.string.no_net)
    }

    fun showError(errorMsg: String) {
        binding.root.visibility = View.VISIBLE
        binding.loadingLayout.visibility = View.GONE
        binding.abnormaLayout.visibility = View.VISIBLE

        binding.ivHint.setImageResource(errorImgRes)

        binding.tvHint.text = errorMsg
    }

    fun hideRetryBtn() {
        binding.btnRetry.visibility = View.GONE
    }

    fun hide() {
        binding.root.visibility = View.GONE
    }


    fun retry(retry: RetryListener) {
        binding.btnRetry.setOnClickListener {
            showLoading()
            retry()
        }

    }

}