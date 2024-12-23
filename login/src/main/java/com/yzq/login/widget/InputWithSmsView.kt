package com.yzq.login.widget

import android.annotation.SuppressLint
import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.core.widget.doAfterTextChanged
import com.yzq.logger.Logger
import com.yzq.login.R
import com.yzq.login.databinding.LayoutInputSmsCodeEndBinding
import com.yzq.widget.input.InputWithCustomEndView


/**
 * @description: 带有发送短信按钮的输入框
 * @author : yuzhiqiang
 */

class InputWithSmsView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : InputWithCustomEndView(context, attrs, defStyleAttr) {

    companion object {
        private const val TAG = "InputWithSmsView"
    }

    private var countDownTimer: CountDownTimer? = null

    //是否正在倒计时
    private var isCounting = false

    //按钮是否可用
    private var smsBtnEnable = false

    //短信按钮点击监听器
    private var smsBtnClick: OnClickListener? = null

    private var endViewBinding: LayoutInputSmsCodeEndBinding =
        LayoutInputSmsCodeEndBinding.inflate(LayoutInflater.from(context), this, false)

    init {
        context.obtainStyledAttributes(attrs, R.styleable.InputWithSmsView, 0, 0).apply {
            val buttonText = getString(R.styleable.InputWithSmsView_sms_button_text) ?: "获取验证码"
            val buttonTextColor = getColor(
                R.styleable.InputWithSmsView_sms_button_text_color,
                ContextCompat.getColor(context, com.yzq.resource.R.color.white)
            )
            val buttonTextSize = getDimension(
                R.styleable.InputWithSmsView_sms_button_text_size, 12f
            )
//            val buttonWidth = getDimensionPixelSize(
//                R.styleable.InputWithSmsView_sms_button_width, LayoutParams.WRAP_CONTENT
//            )
//            val buttonHeight = getDimensionPixelSize(
//                R.styleable.InputWithSmsView_sms_button_height, LayoutParams.WRAP_CONTENT
//            )
            val buttonPadding = getDimensionPixelSize(
                R.styleable.InputWithSmsView_sms_button_padding, 0
            )

            endViewBinding.run {
                ivClear.visibility = View.INVISIBLE
                ivClear.setOnClickListener {
                    editTv.setText("")
                }
                btnSmsCode.run {
                    text = buttonText
                    setTextColor(buttonTextColor)
                    setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, buttonTextSize)
//                    layoutParams = LayoutParams(buttonWidth, buttonHeight).apply {
//                        setGravity(Gravity.CENTER)
//                    }
                    setPadding(buttonPadding)
                    setBackgroundResource(R.drawable.sms_btn_selector)
                    isEnabled = smsBtnEnable
                    setOnClickListener {
                        smsBtnClick?.onClick(it)
                    }
                }
            }

            setCustomView(endViewBinding.root)

            editTv.doAfterTextChanged {
                if (it.toString().isNotEmpty()) {
                    endViewBinding.ivClear.visibility = View.VISIBLE
                } else {
                    endViewBinding.ivClear.visibility = View.INVISIBLE
                }
                this@InputWithSmsView.onContentChange?.invoke(it.toString())
            }
            updateBtnEnable()

        }.recycle()
    }

    /**
     * 开始倒计时
     */
    fun startCountdown() {
        if (isCounting) {
            return
        }
        countDownTimer = object : CountDownTimer(1000 * 60, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                endViewBinding.btnSmsCode.text =
                    "已发送(${millisUntilFinished / 1000 + 1}s)"
            }

            override fun onFinish() {
                endViewBinding.btnSmsCode.text = context.resources.getString(R.string.get_sms_code)
                endViewBinding.btnSmsCode.setTextColor(
                    ContextCompat.getColor(
                        context,
                        com.yzq.resource.R.color.white
                    )
                )
                isCounting = false
                updateBtnEnable()
            }
        }.start()
        smsBtnEnable = false
        updateBtnEnable()
        isCounting = true


    }

    private fun updateBtnEnable() {
        if (isCounting) {
            return
        }
        Logger.it(TAG, "updateBtnEnable:$smsBtnEnable")
        endViewBinding.btnSmsCode.isEnabled = this.smsBtnEnable
        if (endViewBinding.btnSmsCode.isEnabled) {
            endViewBinding.btnSmsCode.setTextColor(
                ContextCompat.getColor(
                    context,
                    com.yzq.resource.R.color.color_primary
                )
            )
        } else {
            endViewBinding.btnSmsCode.setTextColor(
                ContextCompat.getColor(
                    context,
                    com.yzq.resource.R.color.icon_secondary
                )
            )
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        countDownTimer?.cancel()
        isCounting = false
    }


    fun onSmsBtnClick(listener: OnClickListener) {
        this.smsBtnClick = listener
    }


    /**
     * 更改发送按钮的状态
     * @param enable Boolean
     */
    fun changeSmsBtnEnable(enable: Boolean) {
        //如果不在倒计时中，可以改变状态
        this.smsBtnEnable = enable
        updateBtnEnable()
    }

}