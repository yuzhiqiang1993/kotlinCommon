package com.yzq.widget.input

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.LinearLayoutCompat
import com.yzq.widget.R
import com.yzq.widget.databinding.ViewInputCustomEndBinding


/**
 * @description: 自定义输入框，带有自定义的右侧视图
 * @author : yuzhiqiang
 */

open class InputWithCustomEndView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr) {

    private var binding: ViewInputCustomEndBinding =
        ViewInputCustomEndBinding.inflate(LayoutInflater.from(context), this, true)


    protected var onContentChange: ((content: String) -> Unit)? = null


    init {
        context.obtainStyledAttributes(
            attrs, R.styleable.InputWithCustomEndView, 0, 0
        ).apply {
            try {
                // 设置 input_enable (EditText 是否可编辑)
                binding.et.isEnabled =
                    getBoolean(R.styleable.InputWithCustomEndView_input_enable, true)

                // 设置 input_hint (EditText 的提示)
                binding.et.hint = getString(R.styleable.InputWithCustomEndView_input_hint)


            } finally {
                recycle()
            }
        }
    }


    // 动态设置 input_content
    fun setInputContent(content: String) {
        binding.et.setText(content)
        binding.et.setSelection(content.length)
    }

    // 动态设置 input_enable
    fun setInputEnable(enable: Boolean) {
        binding.et.isEnabled = enable
    }

    // 动态设置 input_hint
    fun setInputHint(hint: String) {
        binding.et.hint = hint
    }


    // 添加自定义视图到 ll_custom_view 中
    fun setCustomView(view: View, layoutParams: LayoutParams? = null) {
        kotlin.runCatching {
            binding.layoutCustomView.removeAllViews()
            if (layoutParams != null) {
                binding.layoutCustomView.addView(view, layoutParams)
                return
            }
            binding.layoutCustomView.addView(view)
        }
    }


    fun onContentChange(listener: (content: String) -> Unit) {
        this.onContentChange = listener
    }

    interface OnContentChangeListener {
        fun onChange(content: String)
    }

    val editTv: AppCompatEditText
        get() {
            return binding.et
        }


    open fun getContent(): String {
        return editTv.text.toString().trim()
    }

}