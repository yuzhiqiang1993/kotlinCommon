package com.yzq.kotlincommon.ui.fragment

import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.yzq.kotlincommon.R
import com.yzq.lib_base.ui.BaseFragment
import com.yzq.lib_materialdialog.showCallbackDialog
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : BaseFragment() {
    override fun getContentLayoutId(): Int = R.layout.fragment_user

    companion object {
        fun newInstance() = UserFragment()
    }


    override fun initWidget() {

        LogUtils.i("UserFragment")
        btn_show_dialog.setOnClickListener {

            activity?.showCallbackDialog(
                message = "这是在Fragment中调Activity的弹窗",
                positiveCallback = {
                    ToastUtils.showShort("点击了确定")
                },
                negativeCallback = {
                    ToastUtils.showShort("点击了取消")
                }
            )
        }

    }
}
