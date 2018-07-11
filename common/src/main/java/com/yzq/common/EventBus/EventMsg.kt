package com.yzq.common.EventBus

import com.yzq.common.annotations.NoArgs


/**
 * @description: EventMsg数据类，用于EventBus数据传递
 * @author : yzq
 * @date   : 2018/7/9
 * @time   : 14:23
 *
 */

@NoArgs
data class EventMsg(var tag: String, var obj: Any)