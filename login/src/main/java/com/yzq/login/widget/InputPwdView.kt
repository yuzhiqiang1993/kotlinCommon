package com.yzq.login.widget

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.core.widget.doAfterTextChanged
import com.yzq.login.R
import com.yzq.login.databinding.LayoutPwdIconBinding
import com.yzq.widget.input.InputWithCustomEndView


/**
 *
 * @description: 密码输入框
 * @author : yuzhiqiang
 *
 */

class InputPwdView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : InputWithCustomEndView(context, attrs, defStyleAttr) {

    private val iconClearText: Int = R.drawable.ic_view_pwd//明文
    private val iconCiphertext: Int = R.drawable.ic_hide_pwd//密文
    private var currentPwdIconRes = iconCiphertext

    companion object {
        const val TAG = "InputPwdView"
    }

    private var iconBinding: LayoutPwdIconBinding =
        LayoutPwdIconBinding.inflate(LayoutInflater.from(context), this, false)

    init {

        editTv.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        iconBinding.run {
            ivClear.visibility = View.INVISIBLE
            ivPwd.visibility = View.VISIBLE
            ivPwd.setImageResource(currentPwdIconRes)

            ivClear.setOnClickListener {
                editTv.setText("")
            }

            ivPwd.setOnClickListener {
                switchPwdIcon()
            }
        }
        setCustomView(iconBinding.root)

        editTv.doAfterTextChanged {
            if (it.toString().isNotEmpty()) {
                iconBinding.ivClear.visibility = View.VISIBLE
            } else {
                iconBinding.ivClear.visibility = View.INVISIBLE
            }

            onContentChange?.invoke(it.toString())
        }
    }

    private fun switchPwdIcon() {
        val selectionStart = editTv.selectionStart
        if (currentPwdIconRes == iconCiphertext) {
            //切换到明文
            currentPwdIconRes = iconClearText
            editTv.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            currentPwdIconRes = iconCiphertext
            editTv.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        iconBinding.ivPwd.setImageResource(currentPwdIconRes)
        editTv.setSelection(selectionStart)

    }

}

