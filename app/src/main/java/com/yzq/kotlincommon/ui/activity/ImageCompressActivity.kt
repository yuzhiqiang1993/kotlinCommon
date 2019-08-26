package com.yzq.kotlincommon.ui.activity

import android.annotation.SuppressLint
import androidx.appcompat.widget.Toolbar
import com.alibaba.android.arouter.facade.annotation.Route
import com.yzq.common.extend.load
import com.yzq.common.extend.openCamera
import com.yzq.common.extend.transform
import com.yzq.common.mvvm.model.CompressImgModel
import com.yzq.common.ui.BaseActivity
import com.yzq.data_constants.constants.RoutePath
import com.yzq.kotlincommon.R
import kotlinx.android.synthetic.main.activity_image_compress.*


/**
 * @description: 图片相关
 * @author : yzq
 * @date   : 2019/4/30
 * @time   : 13:38
 *
 */

@Route(path = RoutePath.Main.IMG_COMPRESS)
class ImageCompressActivity : BaseActivity() {


    val compressImgModel = CompressImgModel()

    override fun getContentLayoutId(): Int {

        return R.layout.activity_image_compress
    }


    private lateinit var imgPath: String

    @SuppressLint("AutoDispose")
    override fun initWidget() {
        super.initWidget()

        val toolbar = this.findViewById<Toolbar>(R.id.toolbar)
        initToolbar(toolbar, "图片")
        fab_camera.setOnClickListener {

            openCamera().subscribe { file ->
                compressImgModel.compressImgWithWatermark(file.path)
                        .transform(this)
                        .subscribe { path ->
                            imgPath = path
                            iv_img.load(imgPath)
                        }
            }
        }


        iv_img.setOnClickListener {
            preViewImg(imgPath, it)
        }


    }

}