package com.yzq.login.widget

import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.yzq.login.R
import com.yzq.login.databinding.ViewAgreementCheckboxBinding


/**
 *
 * @description: 隐私协议, 用于显示带有链接的文本,以及复选框
 * @author : yuzhiqiang
 *
 */

class AgreementCheckboxView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    companion object {
        const val TAG = "AgreementCheckboxView"
    }

    private val binding: ViewAgreementCheckboxBinding =
        ViewAgreementCheckboxBinding.inflate(LayoutInflater.from(context), this, true)


    private var onAgreementChecked: ((Boolean) -> Unit)? = null
    private var onAgreementClick: ((String) -> Unit)? = null


    private var checkedRes = R.drawable.ic_checked
    private var uncheckedRes = R.drawable.ic_unchecked

    private var isChecked = false


    init {
        context.obtainStyledAttributes(
            attrs, R.styleable.AgreementCheckboxView, 0, 0
        ).apply {
            try {
                val showCheck = getBoolean(R.styleable.AgreementCheckboxView_show_check, false)
                val agreementText = getString(R.styleable.AgreementCheckboxView_agreement_text)
                val agreementLinks =
                    getResourceId(R.styleable.AgreementCheckboxView_agreement_links, 0)

                val linkArr = if (agreementLinks == 0) {
                    emptyArray<String>()
                } else {
                    context.resources.getStringArray(agreementLinks)
                }

                if (showCheck) {
                    binding.ivChecked.visibility = View.VISIBLE
                } else {
                    binding.ivChecked.visibility = View.GONE
                }

                if (!agreementText.isNullOrEmpty()) {
                    setAgreementText(agreementText, linkArr)
                }

            } finally {
                recycle()
            }

        }


        binding.ivChecked.run {
            setImageResource(getCurrentCheckRes())
            setOnClickListener {
                isChecked = !isChecked
                setImageResource(getCurrentCheckRes())

                onAgreementChecked?.invoke(isChecked)
            }


        }
    }

    private fun getCurrentCheckRes(): Int {
        return if (this.isChecked) checkedRes else uncheckedRes
    }


    fun updateAgreementText(@StringRes agreementText: Int, links: Array<String>) {
        val agreementText = context.getString(agreementText)
        setAgreementText(agreementText, links)
    }


    /**
     * 设置协议文本
     * @param fullText String 完整文本
     * @param linkTexts Array<String> 需要设置链接的文本
     */
    private fun setAgreementText(
        fullText: String,
        linkTexts: Array<String>,
    ) {
        val spannableString = SpannableString(fullText)

        //找出所有需要设置链接的文本，添加事件，更新样式
        for (linkText in linkTexts) {
            val start = fullText.indexOf(linkText)
            if (start != -1) {
                val clickableSpan = object : ClickableSpan() {
                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.isUnderlineText = false //不显示下划线
                        ds.color = resources.getColor(com.yzq.resource.R.color.color_primary, null)
                        ds.isFakeBoldText = true
                        ds.bgColor = Color.TRANSPARENT

                    }

                    override fun onClick(widget: View) {
                        onAgreementClick?.invoke(linkText)
                    }

                }

                spannableString.setSpan(
                    clickableSpan, start, start + linkText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        binding.tvAgreement.text = spannableString

        binding.tvAgreement.apply {
            text = spannableString
            movementMethod = LinkMovementMethod.getInstance()//设置超链接可点击
            highlightColor = Color.TRANSPARENT //设置点击后的颜色为透明
        }

    }


    fun onAgreementChecked(listener: (Boolean) -> Unit) {
        this.onAgreementChecked = listener
    }

    fun onAgreementClick(listener: (String) -> Unit) {
        this.onAgreementClick = listener
    }


    fun changeCheckState(checked: Boolean) {
        //状态相同不做处理
        if (this.isChecked == checked) {
            return
        }
        this.isChecked = checked
        binding.ivChecked.setImageResource(getCurrentCheckRes())
        onAgreementChecked?.invoke(this.isChecked)
    }


}