package com.yzq.kotlincommon.ui.fragment

import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.yzq.kotlincommon.R
import com.yzq.lib_base.ui.fragment.BaseFragment
import com.yzq.lib_img.openAlbum
import com.yzq.lib_materialdialog.showCallbackDialog
import com.yzq.lib_permission.getPermissions
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : BaseFragment() {
    override fun getContentLayoutId(): Int = R.layout.fragment_user

    companion object {
        fun newInstance() = UserFragment()
    }


    override fun initWidget() {

        LogUtils.i("UserFragment")


        btn_show_dialog.setOnClickListener {

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

        btn_select_img.setOnClickListener {

            openAlbum {

                ToastUtils.showShort("选择的图片:$it")
            }
        }


        btn_permission.setOnClickListener {

            getPermissions(PermissionConstants.STORAGE) {
                ToastUtils.showShort("获取了权限：$it")
            }

        }


    }
}
