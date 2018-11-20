package com.yzq.kotlincommon.ui

import android.provider.MediaStore
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.UriUtils
import com.qingmei2.rximagepicker.core.RxImagePicker
import com.yzq.common.extend.transform
import com.yzq.common.img.ImageLoader
import com.yzq.common.ui.BaseActivity
import com.yzq.common.widget.Dialog
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.dagger.DaggerMainComponent
import kotlinx.android.synthetic.main.activity_image.*


class ImageActivity : BaseActivity() {


    override fun getContentLayoutId(): Int {

        return R.layout.activity_image
    }


    override fun initInject() {
        super.initInject()

        DaggerMainComponent.builder().build().inject(this)

    }


    private lateinit var imgPath: String

    override fun initWidget() {
        super.initWidget()


        imgFab.setOnClickListener {

            RxImagePicker.create().openCamera(this).subscribe({
                var file = UriUtils.uri2File(it.uri, MediaStore.Images.Media.DATA)

                LogUtils.i("file.path=${file.path}")
                LogUtils.i("file.name=${file.name}")
                LogUtils.i("file.length=${file.length()}")

                //  compressImgPresenter.compressImg(file.path)
                compressImgModel.compressImgWithWatermark(file.path)
                        .transform(this)
                        .subscribe {
                            imgPath = it
                            ImageLoader.loadCenterCrop(imgPath, imgIv)
                        }
            })


        }


        imgIv.setOnClickListener {
            preViewImg("拍照", imgPath)
        }


    }


    override fun onBackPressed() {

        Dialog.showBackHintDialog()
                .subscribe {
                    finish()
                }


    }
}
