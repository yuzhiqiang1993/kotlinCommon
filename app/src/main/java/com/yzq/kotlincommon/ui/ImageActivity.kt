package com.yzq.kotlincommon.ui

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.afollestad.materialdialogs.MaterialDialog
import com.blankj.utilcode.util.LogUtils
import com.qingmei2.rximagepicker.core.RxImagePicker
import com.qingmei2.rximagepicker.ui.DefaultImagePicker
import com.yzq.common.img.ImageLoader
import com.yzq.common.ui.BaseActivity
import com.yzq.common.widget.Dialog
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.dagger.DaggerMainComponent
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_image.*


class ImageActivity : BaseActivity() {


    lateinit var imgPath: String

    override fun getContentLayoutId(): Int {

        return R.layout.activity_image
    }


    override fun initInject() {
        super.initInject()

        DaggerMainComponent.builder().build().inject(this)

    }

    private lateinit var imgPicker: DefaultImagePicker


    override fun initWidget() {
        super.initWidget()


        initCompressImgPresenter()

        imgPicker = RxImagePicker.Builder().with(this)
                .build().create()

        imgFab.setOnClickListener {
            imgPicker.openGallery().subscribe(Consumer {
                LogUtils.i("图片路径：" + getRealFilePath(this, it.uri))
                imgPath = getRealFilePath(this, it.uri)!!
                compressImgPresenter.compressImg(imgPath)
            })

        }


        imgIv.setOnClickListener {
            preViewImg("拍照", imgPath)
        }


    }

    override fun compressImgSuccess(path: String) {
        super.compressImgSuccess(path)

        ImageLoader.loadCenterCrop(imgPath, imgIv)
    }

    fun getRealFilePath(context: Context, uri: Uri): String? {

        val scheme = uri!!.getScheme()
        var data: String? = null
        if (scheme == null)
            data = uri!!.getPath()
        else if (ContentResolver.SCHEME_FILE == scheme) {
            data = uri!!.getPath()
        } else if (ContentResolver.SCHEME_CONTENT == scheme) {
            val cursor = context.getContentResolver().query(uri, arrayOf(MediaStore.Images.ImageColumns.DATA), null, null, null)
            if (null != cursor) {
                if (cursor!!.moveToFirst()) {
                    val index = cursor!!.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                    if (index > -1) {
                        data = cursor!!.getString(index)
                    }
                }
                cursor!!.close()
            }
        }
        return data
    }


    override fun onBackPressed() {
        Dialog.showBackHintDialog(positiveCallback = MaterialDialog.SingleButtonCallback { dialog, which ->

            finish()
        })


    }
}
