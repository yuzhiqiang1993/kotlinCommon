package com.yzq.kotlincommon.ui.activity

import android.content.ContentUris
import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.UriUtils
import com.hjq.permissions.Permission
import com.therouter.router.Route
import com.yzq.base.extend.initToolbar
import com.yzq.base.extend.setOnThrottleTimeClick
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewbind
import com.yzq.common.constants.AppStorage
import com.yzq.common.constants.RoutePath
import com.yzq.coroutine.safety_coroutine.launchSafety
import com.yzq.coroutine.safety_coroutine.withIO
import com.yzq.img.load
import com.yzq.kotlincommon.databinding.ActivityStorageBinding
import com.yzq.permission.getPermissions
import java.io.BufferedOutputStream
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

    private var takePhotoActivityResult: ActivityResultLauncher<Uri>? = null
    private val binding by viewbind(ActivityStorageBinding::inflate)


    private var photoUri: Uri? = null


    init {
        /**
         * 打印存储的目录
         */
        AppStorage.logPathInfo()
    }

    override fun initVariable() {


    }

    override fun initListener() {
        super.initListener()

        takePhotoActivityResult =
            registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
                LogUtils.i("success:${success}")
                if (success) {
                    saveImg()
                }
            }
    }

    private fun saveImg() {

        lifecycleScope.launchSafety {

            val path = withIO {
                return@withIO photoUri?.run {
                    LogUtils.i("uri = ${path}")
                    val file = UriUtils.uri2File(photoUri)

                    val contentValues = ContentValues().apply {
                        put(MediaStore.MediaColumns.DISPLAY_NAME, file.name)
                        put(MediaStore.MediaColumns.MIME_TYPE, "jpg")
                    }


                    val path: String

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        path =
                            "${Environment.DIRECTORY_PICTURES}${File.separator}KotlinCommon${File.separator}"
                        LogUtils.i("path:${path}")
                        contentValues.put(
                            MediaStore.MediaColumns.RELATIVE_PATH,
                            path
                        )
                    } else {
                        path =
                            "${Environment.getExternalStorageDirectory().path}/${Environment.DIRECTORY_PICTURES}/KotlinCommon/"
                        LogUtils.i("path:${path}")
                        contentValues.put(
                            MediaStore.MediaColumns.DATA,
                            "${path}${file.name}"
                        )
                    }
                    val uri = contentResolver.insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        contentValues
                    )
                    if (uri != null) {
                        val bis = file.inputStream().buffered()
                        val outputStream = contentResolver.openOutputStream(uri)
                        if (outputStream != null) {
                            val bos = BufferedOutputStream(outputStream)
                            val buffer = ByteArray(1024)
                            var bytes = bis.read(buffer)
                            while (bytes >= 0) {
                                bos.write(buffer, 0, bytes)
                                bos.flush()
                                bytes = bis.read(buffer)
                            }
                            bos.close()
                        }
                        bis.close()
                    }

                    path
                }
            }


            ToastUtils.showShort("图片已保存，路径为:${path}")
        }

    }

    override fun initWidget() {
        initToolbar(binding.includedToolbar.toolbar, "存储")

        binding.run {

            btnAddImg.setOnThrottleTimeClick {
                getPermissions(Permission.CAMERA, Permission.READ_MEDIA_IMAGES) {
                    takePhoto()
                }

            }

            btnShowImg.setOnThrottleTimeClick {
                getPermissions(Permission.READ_MEDIA_IMAGES) {
                    showImg()
                }

            }


        }
    }


    private fun takePhoto() {
        /*拍一张照片*/
        photoUri = FileProvider.getUriForFile(
            this,
            "${packageName}.provider",
            File("${AppStorage.External.Private.picturesPath}${System.currentTimeMillis()}.jpg")
        )
        takePhotoActivityResult?.launch(photoUri)

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


        LogUtils.i("pictureUriList:${pictureUriList.size}")

        if (pictureUriList.size > 0) {
            binding.ivImg.load(pictureUriList.get(0))
        }

    }

}
