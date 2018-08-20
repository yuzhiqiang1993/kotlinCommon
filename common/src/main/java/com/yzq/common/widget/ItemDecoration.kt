package com.yzq.common.widget

import android.content.Context
import android.support.v4.content.ContextCompat
import com.yzq.common.R


/**
 * @description: 统一的列表分割线
 * @author : yzq
 * @date   : 2018/8/20
 * @time   : 10:44
 *
 */

object ItemDecoration {

    fun baseItemDecoration(context: Context): SuperDividerItemDecoration {
        return SuperDividerItemDecoration.Builder(context)
                .setDividerColor(ContextCompat.getColor(context, R.color.gray_300))
                .setDividerWidth(1)
                .build()

    }


}