package com.yzq.kotlincommon.ui.activity

import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.blankj.utilcode.util.UriUtils
import com.hjq.permissions.Permission
import com.therouter.router.Route
import com.yzq.base.R
import com.yzq.base.extend.initToolbar
import com.yzq.base.ui.ImgPreviewActivity
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewbind
import com.yzq.common.constants.AppStorage
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.databinding.ActivityImageCompressBinding
import com.yzq.kotlincommon.view_model.CompressImgViewModel
import com.yzq.permission.getPermissions
import java.io.File
import java.util.*

/**
 * @description: 图片相关
 * @author : yzq
 * @date : 2019/4/30
 * @time : 13:38
 *
 */

@Route(path = RoutePath.Main.IMG_COMPRESS)
class ImageCompressActivity : BaseActivity() {

    private var takePhotoResult: ActivityResultLauncher<Uri>? = null
    private var takePhotoUri: Uri? = null

    private val binding by viewbind(ActivityImageCompressBinding::inflate)
    private val vm: CompressImgViewModel by viewModels()

    private lateinit var compressImgViewModel: CompressImgViewModel


    private lateinit var imgPath: String

    override fun initWidget() {
        super.initWidget()

        compressImgViewModel = ViewModelProvider(this).get(CompressImgViewModel::class.java)

        takePhotoResult =
            registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
                if (success) {
                    takePhotoUri?.also { uri ->
                        UriUtils.uri2FileNoCacheCopy(uri)?.also { file ->
                            compressImgViewModel.compressImg(file.absolutePath)
                        }

                    }
                }
            }

        initToolbar(binding.layoutToolbar.toolbar, "图片")
        binding.fabCamera.setOnClickListener {

            getPermissions(Permission.CAMERA, Permission.READ_MEDIA_IMAGES) {

                /*创建要保存的文件*/
                val file = File(
                    AppStorage.External.Private.picturesPath,
                    "${Calendar.getInstance().timeInMillis}.png"
                )
                /*获得uri*/
                takePhotoUri = FileProvider.getUriForFile(
                    this,
                    applicationContext.packageName + ".provider",
                    file
                )
                takePhotoResult?.launch(takePhotoUri)
            }

        }

        binding.fabAlbum.setOnClickListener {

            getPermissions(Permission.READ_MEDIA_IMAGES) {
                /*暂时没有适配Android13的相册选择器*/

            }
        }

        binding.ivImg.setOnClickListener {
            val intent = Intent(this, ImgPreviewActivity::class.java)
            intent.putExtra(ImgPreviewActivity.IMG_PATH, imgPath)

            val options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this,
                    it,
                    getString(R.string.img_transition)
                )
            startActivity(intent, options.toBundle())
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
