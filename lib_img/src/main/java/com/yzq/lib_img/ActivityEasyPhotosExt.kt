package com.yzq.lib_img

import androidx.appcompat.app.AppCompatActivity
import com.huantansheng.easyphotos.EasyPhotos
import com.huantansheng.easyphotos.callback.SelectCallback
import com.huantansheng.easyphotos.models.album.entity.Photo
import com.huantansheng.easyphotos.setting.Setting


fun AppCompatActivity.openCamera(imageSelected: ImageSelected) {
    EasyPhotos.createCamera(this)
        .setFileProviderAuthority("$packageName.provider")
        .start(object : SelectCallback() {
            override fun onResult(photos: ArrayList<Photo>, isOriginal: Boolean) {
                imageSelected(photos)
            }
        })
}


fun AppCompatActivity.openAlbum(
    count: Int = 1,
    showCamera: Boolean = false,
    minFileSize: Long = 1,
    showGif: Boolean = false,
    showPuzzle: Boolean = false,
    showClearMenu: Boolean = false,
    selectedPhotos: ArrayList<Photo> = arrayListOf(),
    imageSelected: ImageSelected

) {
    EasyPhotos.createAlbum(this, showCamera, GlideEngine)
        .setCount(count)
        .setMinFileSize(minFileSize)
        .setFileProviderAuthority("$packageName.provider")
        .setGif(showGif)
        .setPuzzleMenu(showPuzzle)
        .setCleanMenu(showClearMenu)
        .setCameraLocation(Setting.LIST_FIRST)
        .setSelectedPhotos(selectedPhotos)
        .start(object : SelectCallback() {
            override fun onResult(photos: ArrayList<Photo>, isOriginal: Boolean) {
                imageSelected(photos)
            }

        })


}

