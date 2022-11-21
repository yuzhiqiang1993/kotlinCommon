package com.yzq.kotlincommon.ui.activity

import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.huantansheng.easyphotos.models.album.entity.Photo
import com.yzq.base.extend.initToolbar
import com.yzq.base.ui.activity.BaseVmActivity
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.img.load
import com.yzq.img.openAlbum
import com.yzq.img.openCamera
import com.yzq.kotlincommon.databinding.ActivityImageCompressBinding
import com.yzq.kotlincommon.view_model.CompressImgViewModel

/**
 * @description: 图片相关
 * @author : yzq
 * @date : 2019/4/30
 * @time : 13:38
 *
 */

@Route(path = RoutePath.Main.IMG_COMPRESS)
class ImageCompressActivity :
    BaseVmActivity<CompressImgViewModel>() {

    private val binding by viewbind(ActivityImageCompressBinding::inflate)

    private lateinit var compressImgViewModel: CompressImgViewModel

    private var selectedPhotos = arrayListOf<Photo>()

    private lateinit var imgPath: String

    override fun getViewModelClass() = CompressImgViewModel::class.java

    override fun initWidget() {
        super.initWidget()

        compressImgViewModel = ViewModelProvider(this).get(CompressImgViewModel::class.java)

        initToolbar(binding.layoutToolbar.toolbar, "图片")
        binding.fabCamera.setOnClickListener {
            openCamera {

                compressImgViewModel.compressImg(it[0].path)
            }
        }

        binding.fabAlbum.setOnClickListener {

            openAlbum(count = 5, selectedPhotos = selectedPhotos) {
                selectedPhotos.clear()
                selectedPhotos.addAll(it)
                compressImgViewModel.compressImg(selectedPhotos[0].path)

                LogUtils.i("选择的图片是$selectedPhotos")
            }
        }

        binding.ivImg.setOnClickListener {
            preViewImg(imgPath, it)
        }
    }

    override fun observeViewModel() {
        vm.run {
            compressedImgPathLiveData.observe(this@ImageCompressActivity) {
                imgPath = it
                binding.ivImg.load(imgPath)
            }
        }
    }
}
