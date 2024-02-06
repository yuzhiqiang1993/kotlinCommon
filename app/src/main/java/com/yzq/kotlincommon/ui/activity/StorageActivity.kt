package com.yzq.kotlincommon.ui.activity

import android.content.ContentUris
import android.content.ContentValues
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import coil.load
import com.blankj.utilcode.util.ToastUtils
import com.hjq.permissions.Permission
import com.therouter.router.Route
import com.yzq.application.AppStorage
import com.yzq.base.extend.initToolbar
import com.yzq.base.extend.setOnThrottleTimeClick
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.coroutine.safety_coroutine.launchSafety
import com.yzq.coroutine.safety_coroutine.withIO
import com.yzq.kotlincommon.databinding.ActivityStorageBinding
import com.yzq.logger.Logger
import com.yzq.permission.getPermissions
import java.io.File


/**
 * @description 存储相关 主要是Android 10分区存储的适配
 * https://guolin.blog.csdn.net/article/details/105419420
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2023/2/7
 * @time    14:28
 */

@Route(path = RoutePath.Main.STORAGE)
class StorageActivity : BaseActivity() {

    private var takePhotoActivityResult: ActivityResultLauncher<Void?>? = null

    private val binding by viewbind(ActivityStorageBinding::inflate)


    init {
        /**
         * 打印存储的目录
         */
        Logger.i(AppStorage.logPathInfo)
    }

    override fun initVariable() {


    }

    override fun initListener() {
        super.initListener()

        takePhotoActivityResult =
            registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
                it?.run { saveImg(this) }
            }

    }


    /**
     * Save img  将拍摄的照片保存到公共文件夹中
     *
     * @param bitmap
     */
    private fun saveImg(bitmap: Bitmap) {

        lifecycleScope.launchSafety {
            withIO {

                val displayName = "${System.currentTimeMillis()}.jpg"
                val mimeType = "image/jpeg"
                val compressFormat = Bitmap.CompressFormat.JPEG
                val values = ContentValues()
                values.put(MediaStore.MediaColumns.DISPLAY_NAME, displayName)
                values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType)

                val path: String

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    path = "${Environment.DIRECTORY_PICTURES}${File.separator}KotlinCommon"
                    values.put(
                        MediaStore.MediaColumns.RELATIVE_PATH,
                        path
                    )
                } else {
                    path =
                        "${Environment.getExternalStorageDirectory().path}/${Environment.DIRECTORY_PICTURES}${File.separator}KotlinCommon"
                    values.put(
                        MediaStore.MediaColumns.DATA,
                        "$path/$displayName"
                    )
                }

                val uri =
                    contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                if (uri != null) {
                    val outputStream = contentResolver.openOutputStream(uri)
                    if (outputStream != null) {
                        bitmap.compress(compressFormat, 100, outputStream)
                        outputStream.close()
                    }
                    Logger.i("path = ${path}")
                    Logger.i("uri.path = ${uri.path}")
                    ToastUtils.showShort("图片已保存至 ${path}")

                    /*通知相册更新数据*/
                    MediaScannerConnection.scanFile(
                        this@StorageActivity, arrayOf(uri.path), arrayOf(mimeType)
                    ) { path, uri ->
                        Logger.i("scanFile path:$path")
                        Logger.i("scanFile uri:$uri")

                    }
                }


            }
            showImg()
        }

    }

    override fun initWidget() {
        initToolbar(binding.includedToolbar.toolbar, "存储")

        binding.run {

            btnAddImg.setOnThrottleTimeClick {
                getPermissions(Permission.CAMERA, Permission.READ_MEDIA_IMAGES) {
                    takePhotoActivityResult?.launch(null)
                }

            }

            btnShowImg.setOnThrottleTimeClick {
                getPermissions(Permission.READ_MEDIA_IMAGES) {
                    showImg()
                }

            }


        }
    }

    private fun showImg() {
        val pictureUriList = mutableListOf<Uri>()

        /**
         * 不管什么版本的系统，都推荐使用MediaStore的Api读取文件，不存在兼容问题
         */
        val cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            "${MediaStore.MediaColumns.DATE_ADDED} desc"
        )
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val id =
                    cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID))
                /*根据id获取uri*/
                val uri =
                    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                println("image uri is $uri")
                pictureUriList.add(uri)
            }
            cursor.close()
        }


        Logger.i("pictureUriList:${pictureUriList.size}")

        if (pictureUriList.size > 0) {
            println(pictureUriList.get(0))
            binding.ivImg.load(pictureUriList.get(0))
        }

    }

}
