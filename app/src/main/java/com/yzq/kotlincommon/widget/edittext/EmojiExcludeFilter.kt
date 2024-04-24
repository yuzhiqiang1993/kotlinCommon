package com.yzq.kotlincommon.widget.edittext

import android.text.InputFilter
import android.text.Spanned
import com.hjq.toast.Toaster
import java.util.regex.Pattern


/**
 * @description 表情过滤器
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 */

class EmojiExcludeFilter : InputFilter {

    private val emojiPattern = Pattern.compile(
        "[\\uD83C-\\uDBFF\\uDC00-\\uDFFF]|[\u0000-\u001f]|[\u007f-\u009f]|[\u2000-\u206F]|[\u20A0-\u20CF]|[\u2100-\u214F]|[\u2190-\u21FF]|[\u2200-\u22FF]|[\u2300-\u23FF]|[\u2460-\u24FF]|[\u2500-\u257F]|[\u2600-\u27BF]",
        Pattern.UNICODE_CASE or Pattern.CASE_INSENSITIVE
    )

    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val emojiMatcher = emojiPattern.matcher(source)
        return if (emojiMatcher.find()) {
            Toaster.showShort("不允许输入表情")
            ""
        } else {
            null
        }
    }
}
