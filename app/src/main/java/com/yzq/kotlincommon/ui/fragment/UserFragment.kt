package com.yzq.kotlincommon.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.yzq.base.ui.fragment.BaseViewBindingFragment
import com.yzq.img.openAlbum
import com.yzq.kotlincommon.databinding.FragmentUserBinding
import com.yzq.materialdialog.showCallbackDialog
import com.yzq.permission.getPermissions

class UserFragment : BaseViewBindingFragment<FragmentUserBinding>() {


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentUserBinding
        get() = FragmentUserBinding::inflate

    companion object {
        fun newInstance() = UserFragment()
    }


    override fun initWidget() {

        LogUtils.i("UserFragment")


        binding.btnShowDialog.setOnClickListener {

            showCallbackDialog(
                message = "这是在Fragment中调的弹窗",
                positiveCallback = {
                    ToastUtils.showShort("点击了确定")
                },
                negativeCallback = {
                    ToastUtils.showShort("点击了取消")
                }
            )
        }

        binding.btnSelectImg.setOnClickListener {

            openAlbum {

                ToastUtils.showShort("选择的图片:$it")
            }
        }


        binding.btnPermission.setOnClickListener {

            getPermissions(PermissionConstants.STORAGE) {
                ToastUtils.showShort("获取了权限：$it")
            }

        }


    }

}
