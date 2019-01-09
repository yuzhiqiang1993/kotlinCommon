package com.yzq.common.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.blankj.utilcode.util.LogUtils
import com.yzq.common.R
import kotlinx.android.synthetic.main.layout_state_view.view.*


/**
 * @description: 自定义的状态布局
 * @author : yzq
 * @date   : 2018/7/13
 * @time   : 13:43
 *
 */

class StateView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : LinearLayout(context, attrs, defStyleAttr) {


    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?) : this(context, null)


    private var view: View
    private var retryListener: RetryListener? = null

    init {



        view = LayoutInflater.from(context).inflate(R.layout.layout_state_view, this)

        retryBtn.setOnClickListener({
            showLoading()
            if (retryListener == null) {
                showNoData()
            } else {
                retryListener?.retry()
            }

        })
        showLoading()

    }


    fun showLoading() {

        view.visibility = View.VISIBLE
        loaddingLayout.visibility = View.VISIBLE

        abnormalLayout.visibility = View.GONE
    }


    fun showNoData() {

        view.visibility = View.VISIBLE
        loaddingLayout.visibility = View.GONE
        abnormalLayout.visibility = View.VISIBLE
        hintImg.setImageResource(R.drawable.ic_no_data)
        hintTv.text = resources.getString(R.string.noData)
    }

    fun showNoNet() {
        view.visibility = View.VISIBLE
        loaddingLayout.visibility = View.GONE
        abnormalLayout.visibility = View.VISIBLE
        hintImg.setImageResource(R.drawable.ic_no_net)
        // ImageLoader.getInstance().load(context, R.drawable.ic_no_net, hintImg);
        hintTv.text = resources.getString(R.string.noNet)
    }

    fun showError(errorMsg: String) {
        view.visibility = View.VISIBLE
        loaddingLayout.visibility = View.GONE
        abnormalLayout.visibility = View.VISIBLE

        hintImg.setImageResource(R.drawable.ic_error)

        hintTv.text = errorMsg
    }

    fun hideRetryBtn() {
        retryBtn.setVisibility(View.GONE)
    }

    fun hide() {
        view.visibility = View.GONE
    }


    fun setRetryListener(retryListener: RetryListener) {
        this.retryListener = retryListener

    }


    interface RetryListener {

        fun retry()
    }
}