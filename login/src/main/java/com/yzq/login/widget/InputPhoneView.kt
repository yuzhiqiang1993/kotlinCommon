package com.yzq.login.widget

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.yzq.logger.Logger
import com.yzq.widget.input.InputWithEndIconView

/**
 *
 * @description: 手机号输入框
 * @author : yuzhiqiang
 *
 */

class InputPhoneView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : InputWithEndIconView(context, attrs, defStyleAttr) {

    companion object {
        const val TAG = "InputPhoneView"
        private const val SPACE_CHAR = ' '
    }

    private var formattedPhone: String = ""


    private val phoneTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(
            charSequence: CharSequence?, start: Int, count: Int, after: Int
        ) {
        }

        override fun onTextChanged(
            charSequence: CharSequence?,
            start: Int,
            before: Int,
            count: Int
        ) {
        }

        override fun afterTextChanged(s: Editable?) {
            s?.let {
                if (it.length > 13) {
                    revertToFormattedPhone()
                } else {
                    formatToPhone(it)
                }

            }
        }

    }


    init {
        editTv.inputType = android.text.InputType.TYPE_CLASS_NUMBER

        endIcon?.run {
//            hideIcon()
            setOnClickListener {
                editTv.setText("")
            }
        }


        editTv.addTextChangedListener(phoneTextWatcher)
    }


    private fun revertToFormattedPhone() {
        val selectionStart = editTv.selectionStart
        editTv.removeTextChangedListener(phoneTextWatcher)
        editTv.setText(formattedPhone)
        editTv.setSelection(if (selectionStart > formattedPhone.length) formattedPhone.length else selectionStart - 1)
        editTv.addTextChangedListener(phoneTextWatcher)
    }

    private fun formatToPhone(s: Editable) {
        try {
            val inputFilters = s.filters
            s.filters = emptyArray()

            //先移除监听
            editTv.removeTextChangedListener(phoneTextWatcher)
            if (s.isNotEmpty()) {
                showIcon()
            } else {
                hideIcon()
            }
            if (s.length < 4) {
                onContentChange?.invoke(getContent())
                return
            }
            //先清除格式化
            clearFormatting(s)

            //格式化
            applyFormatting(s)

            s.filters = inputFilters
            //保存下本次格式化后的手机号
            formattedPhone = s.toString()

            //返回处理后的手机号
            onContentChange?.invoke(getContent())

        } catch (e: Exception) {
            Logger.et(TAG, e)
        } finally {
            editTv.addTextChangedListener(phoneTextWatcher)

        }


    }

    private fun applyFormatting(s: Editable) {
        if (s.length > 7) {
            s.insert(3, SPACE_CHAR.toString())
            if (editTv.selectionStart == 4) {
                editTv.setSelection(3)
            }

            s.insert(8, SPACE_CHAR.toString())
            if (editTv.selectionStart == 9) {
                editTv.setSelection(8)
            }
        } else {
            s.insert(3, SPACE_CHAR.toString())
            if (editTv.selectionStart == 4) {
                editTv.setSelection(3)
            }
        }
    }

    /**
     * 清除格式化
     * @param s Editable
     */
    private fun clearFormatting(s: Editable) {
        var index = 0
        editTv.text?.let {
            while (index < it.length) {
                if (it[index] == SPACE_CHAR) {
                    s.delete(index, index + 1)
                } else {
                    index++
                }
            }
        }
    }


    override fun getContent(): String {
        return restorePhone(editTv.text)
    }


    /**
     * 恢复手机号,返回真实的手机号
     * @param editable Editable?
     * @return String
     */
    private fun restorePhone(editable: Editable?): String =
        kotlin.runCatching { editable.toString().replace(SPACE_CHAR.toString(), "") }
            .getOrDefault(editable.toString())


}

