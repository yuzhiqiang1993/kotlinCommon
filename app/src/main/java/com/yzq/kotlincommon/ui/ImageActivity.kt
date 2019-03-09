package com.yzq.kotlincommon.ui

import android.annotation.SuppressLint
import androidx.appcompat.widget.Toolbar
import com.alibaba.android.arouter.facade.annotation.Route
import com.yzq.common.constants.RoutePath
import com.yzq.common.img.ImageLoader
import com.yzq.common.img.ImagePicker
import com.yzq.common.ui.BaseActivity
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.dagger.DaggerMainComponent
import kotlinx.android.synthetic.main.activity_image.*

@Route(path = RoutePath.Main.IMG)
class ImageActivity : BaseActivity() {


    override fun getContentLayoutId(): Int {

        return R.layout.activity_image
    }


    override fun initInject() {
        super.initInject()

        DaggerMainComponent.builder().build().inject(this)

    }


    private lateinit var imgPath: String

    @SuppressLint("AutoDispose")
    override fun initWidget() {
        super.initWidget()

        val toolbar = this.findViewById<Toolbar>(R.id.toolbar)
        initToolbar(toolbar, "图片")
        imgFab.setOnClickListener {

            ImagePicker.openCamera(this@ImageActivity)
                    .subscribe { file ->

                        compressImgModel.compressImgWithWatermark(file.path)
                                .subscribe {
                                    imgPath = it
                                    ImageLoader.loadCenterCrop(imgPath, imgIv)
                                }
                    }


        }


        imgIv.setOnClickListener {
            preViewImg("拍照", imgPath)
        }


    }

}
