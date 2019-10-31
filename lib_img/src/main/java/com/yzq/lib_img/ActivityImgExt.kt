package com.yzq.lib_img

import androidx.appcompat.app.AppCompatActivity
import com.huantansheng.easyphotos.EasyPhotos
import com.huantansheng.easyphotos.callback.SelectCallback
import com.huantansheng.easyphotos.models.album.entity.Photo
import java.util.*


typealias ImageSingleSelected = (String) -> Unit


/*调相机拍照*/
fun AppCompatActivity.openCamera(imageSingleSelected: ImageSingleSelected) {
    EasyPhotos.createCamera(this)
        .setFileProviderAuthority("$packageName.provider")
        .start(object : SelectCallback() {
            override fun onResult(photos: ArrayList<Photo>, isOriginal: Boolean) {
                imageSingleSelected(photos[0].path)
            }
        })
}


fun AppCompatActivity.openAlbum(
    showCamera: Boolean = false,
    count: Int = 1,
    minFileSize: Long = 1,
    showGif: Boolean = false,
    showPuzzle: Boolean = false,
    callback: SelectCallback

) {

    EasyPhotos.createAlbum(this, showCamera, GlideEngine)
        .setCount(count)
        .setMinFileSize(minFileSize)
        .setFileProviderAuthority("$packageName.provider")
        .setGif(showGif)
        .setOriginalMenu(false, false, null)
        .setPuzzleMenu(showPuzzle)
        .start(callback)


}



