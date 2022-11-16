package com.yzq.img

import com.huantansheng.easyphotos.callback.SelectCallback
import com.huantansheng.easyphotos.models.album.entity.Photo

/*类型别名 回调函数*/
typealias ImageSelected = (ArrayList<Photo>) -> Unit

abstract class OnResultCallback : SelectCallback() {

    override fun onCancel() {
    }
}
