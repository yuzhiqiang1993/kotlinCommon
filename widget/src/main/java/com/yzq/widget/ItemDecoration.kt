package com.yzq.widget

import android.content.Context
import androidx.core.content.ContextCompat


/**
 * @description: 灰色高度为1dp的常用分割线
 * @author : yzq
 * @date   : 2018/8/20
 * @time   : 10:44
 *
 */

object ItemDecoration {

    fun baseItemDecoration(context: Context): SuperDividerItemDecoration {
        return SuperDividerItemDecoration.Builder(context)
            .setDividerColor(ContextCompat.getColor(context, com.yzq.resource.R.color.gray_300))
            .setDividerWidth(1)
            .build()

    }


}