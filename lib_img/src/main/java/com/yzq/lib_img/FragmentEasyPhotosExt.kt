package com.yzq.lib_img

import androidx.fragment.app.Fragment
import com.huantansheng.easyphotos.EasyPhotos
import com.huantansheng.easyphotos.callback.SelectCallback
import com.huantansheng.easyphotos.models.album.entity.Photo
import com.huantansheng.easyphotos.setting.Setting


/**
 * 打开相机
 * @receiver AppCompatActivity
 * @param imageSelected Function1<ArrayList<Photo>, Unit>  回调函数
 */
fun Fragment.openCamera(imageSelected: ImageSelected) {
    EasyPhotos.createCamera(this, false)
        .setFileProviderAuthority("${requireActivity().packageName}.provider")
        .start(object : SelectCallback() {
            override fun onResult(photos: ArrayList<Photo>, isOriginal: Boolean) {
                imageSelected(photos)
            }

            override fun onCancel() {


            }
        })
}

/**
 * 打开相册
 * @receiver AppCompatActivity
 * @param count Int  最大选取数量
 * @param showCamera Boolean  是否显示相机
 * @param minFileSize Long  最小显示文件的大小
 * @param showGif Boolean  是否显示gif
 * @param showPuzzle Boolean 是否显示拼图按钮
 * @param showClearMenu Boolean 是否显示清空按钮
 * @param selectedPhotos ArrayList<Photo>  已选择的图片
 * @param imageSelected Function1<ArrayList<Photo>, Unit>   回调函数
 */
fun Fragment.openAlbum(
    count: Int = 1,
    showCamera: Boolean = false,
    minFileSize: Long = 1,
    showGif: Boolean = false,
    showPuzzle: Boolean = false,
    showClearMenu: Boolean = false,
    selectedPhotos: ArrayList<Photo> = arrayListOf(),
    imageSelected: ImageSelected

) {
    EasyPhotos.createAlbum(this, showCamera, false, GlideEngine)
        .setCount(count)
        .setMinFileSize(minFileSize)
        .setFileProviderAuthority("${requireActivity().packageName}.provider")
        .setGif(showGif)
        .setPuzzleMenu(showPuzzle)
        .setCleanMenu(showClearMenu)
        .setCameraLocation(Setting.LIST_FIRST)
        .setSelectedPhotos(selectedPhotos)
        .start(object : SelectCallback() {
            override fun onResult(photos: ArrayList<Photo>, isOriginal: Boolean) {
                imageSelected(photos)
            }

            override fun onCancel() {


            }

        })


}



