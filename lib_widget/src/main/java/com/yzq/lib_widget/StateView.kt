package com.yzq.lib_widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.layout_state_view.view.*


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


    private var view: View

    /*无网络图片*/
    private var noNetImgRes = R.drawable.ic_no_net
    /*无数据图片*/
    private var noDataImgRes = R.drawable.ic_no_data
    /*出现错误图片*/
    private var errorImgRes = R.drawable.ic_error

    init {
        view = LayoutInflater.from(context).inflate(R.layout.layout_state_view, this)

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

        view.visibility = View.VISIBLE
        loading_layout.visibility = View.VISIBLE

        abnorma_layout.visibility = View.GONE
    }


    fun showNoData() {

        view.visibility = View.VISIBLE
        loading_layout.visibility = View.GONE
        abnorma_layout.visibility = View.VISIBLE
        iv_hint.setImageResource(noDataImgRes)
        tv_hint.text = resources.getString(R.string.no_data)
    }

    fun showNoNet() {
        view.visibility = View.VISIBLE
        loading_layout.visibility = View.GONE
        abnorma_layout.visibility = View.VISIBLE
        iv_hint.setImageResource(noNetImgRes)
        // ImageLoader.getInstance().load(context, R.drawable.ic_no_net, hintImg);
        tv_hint.text = resources.getString(R.string.no_net)
    }

    fun showError(errorMsg: String) {
        view.visibility = View.VISIBLE
        loading_layout.visibility = View.GONE
        abnorma_layout.visibility = View.VISIBLE

        iv_hint.setImageResource(errorImgRes)

        tv_hint.text = errorMsg
    }

    fun hideRetryBtn() {
        btn_retry.visibility = View.GONE
    }

    fun hide() {
        view.visibility = View.GONE
    }


    fun retry(retry: RetryListener) {
        btn_retry.setOnClickListener {
            showLoading()
            retry()
        }

    }

}