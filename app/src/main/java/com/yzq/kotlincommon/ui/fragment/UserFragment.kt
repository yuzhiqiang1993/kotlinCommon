package com.yzq.kotlincommon.ui.fragment

import androidx.activity.OnBackPressedCallback
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.hjq.permissions.Permission
import com.yzq.base.ui.fragment.BaseFragment
import com.yzq.binding.viewbind
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.FragmentUserBinding
import com.yzq.materialdialog.showCallbackDialog
import com.yzq.materialdialog.showPositiveCallbackDialog
import com.yzq.permission.getPermissions

class UserFragment : BaseFragment(R.layout.fragment_user) {

    private val binding by viewbind(FragmentUserBinding::bind)


    private val backCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {

            requireActivity().showPositiveCallbackDialog("确认退出?", "确认退出已填写的数据将会丢失!") {
                /*调OnBackPressedCallback的setEnabled控制callback是否生效*/
                isEnabled = false
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }

    }

    companion object {
        fun newInstance() = UserFragment()
    }


    override fun initVariable() {
        /*返回处理*/
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backCallback)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        LogUtils.i("onHiddenChanged：${hidden}")
        /*保证只在当前fragment中生效*/
        backCallback.isEnabled = !hidden
    }


    override fun initWidget() {

        LogUtils.i("UserFragment")

        binding.btnShowDialog.setOnClickListener {
            requireActivity().showCallbackDialog(
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
//            openAlbum {
//                ToastUtils.showShort("选择的图片:$it")
//            }
        }

        binding.btnPermission.setOnClickListener {
            requireActivity().getPermissions(Permission.NOTIFICATION_SERVICE) {
                ToastUtils.showShort("权限获取成功")
            }
        }
    }
}
