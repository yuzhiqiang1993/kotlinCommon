package com.yzq.kotlincommon.ui.activity

import ando.file.core.FileGlobal.OVER_LIMIT_EXCEPT_OVERFLOW
import ando.file.core.FileUtils
import ando.file.selector.FileSelectCallBack
import ando.file.selector.FileSelectCondition
import ando.file.selector.FileSelectResult
import ando.file.selector.FileSelector
import ando.file.selector.FileType
import ando.file.selector.IFileType
import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.hjq.permissions.Permission
import com.therouter.router.Route
import com.yzq.application.AppStorage
import com.yzq.base.R
import com.yzq.base.extend.initToolbar
import com.yzq.base.ui.ImgPreviewActivity
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.databinding.ActivityImageCompressBinding
import com.yzq.kotlincommon.view_model.CompressImgViewModel
import com.yzq.logger.Logger
import com.yzq.permission.getPermissions
import java.io.File
import java.util.Calendar

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

    companion object {
        const val TAG = "ImageCompressActivity"
    }

    override fun initWidget() {
        super.initWidget()

        compressImgViewModel = ViewModelProvider(this).get(CompressImgViewModel::class.java)

        takePhotoResult =
            registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
                if (success) {
                    takePhotoUri?.also { uri ->
                        //将uri转为file
                        compressImgViewModel.compressImg(uri)
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
                takePhotoUri?.also {
                    /*启动相机*/
                    takePhotoResult?.launch(it)
                }
            }

        }

        binding.fabAlbum.setOnClickListener {

            getPermissions(Permission.READ_MEDIA_IMAGES) {
                selectImg()
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

    private fun selectImg() {

        FileSelector
            .with(this)
            .setRequestCode(11)
            .setTypeMismatchTip("文件类型不匹配 !") //File type mismatch
            .setMinCount(1, "至少选择一个文件 !") //Choose at least one file
            .setOverLimitStrategy(OVER_LIMIT_EXCEPT_OVERFLOW)
            .setExtraMimeTypes("image/*") //默认不做文件类型约束为"*/*",不同类型系统提供的选择UI不一样 eg:"video/*","audio/*","image/*"
            .filter(object : FileSelectCondition {
                override fun accept(fileType: IFileType, uri: Uri?): Boolean {
                    return when (fileType) {
                        FileType.IMAGE -> (uri != null && !uri.path.isNullOrBlank() && !FileUtils.isGif(
                            uri
                        ))

                        FileType.VIDEO -> false
                        FileType.AUDIO -> false
                        else -> false
                    }
                }
            })
            .callback(object : FileSelectCallBack {
                override fun onSuccess(results: List<FileSelectResult>?) {
                    Logger.it(TAG, "onSuccess: ${results?.size}")
                    compressImgViewModel.compressImg(results?.get(0)?.uri!!)
                }

                override fun onError(e: Throwable?) {
                    Logger.et(TAG, "onError: ${e?.message}")
                }
            })
            .choose()

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
