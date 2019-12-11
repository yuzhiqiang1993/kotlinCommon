package com.yzq.kotlincommon.ui.activity

import android.text.TextUtils
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.BarUtils
import com.yzq.common.constants.RoutePath
import com.yzq.common.utils.LocalSpUtils
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.mvvm.view_model.LoginViewModel
import com.yzq.lib_base.extend.navFinish
import com.yzq.lib_base.ui.BaseMvvmActivity
import kotlinx.android.synthetic.main.activity_login.*


/**
 * @description: SharedPreference相关
 * @author : yzq
 * @date   : 2019/4/30
 * @time   : 13:39
 *
 */

@Route(path = RoutePath.Main.LOGIN)
class LoginActivity : BaseMvvmActivity<LoginViewModel>() {


    override fun getViewModelClass(): Class<LoginViewModel> = LoginViewModel::class.java

    override fun getContentLayoutId(): Int {

        return R.layout.activity_login
    }


    override fun initWidget() {
        super.initWidget()

        BarUtils.setStatusBarVisibility(this, false)

        input_account.setText(LocalSpUtils.account)
        input_pwd.setText(LocalSpUtils.pwd)



        btn_login.setOnClickListener {

            val account = input_account.text?.trim().toString()
            val pwd = input_pwd.text?.trim().toString()

            input_layout_account.isErrorEnabled = false
            input_layout_pwd.isErrorEnabled = false

            if (TextUtils.isEmpty(account)) {
                input_layout_account.isErrorEnabled = true
                input_layout_account.error = "账号不能为空，请检查"
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(account)) {
                input_layout_pwd.isErrorEnabled = true
                input_layout_pwd.error = "密码不能为空，请检查"
                return@setOnClickListener
            }




            LocalSpUtils.account = input_account.text.toString()
            LocalSpUtils.pwd = input_pwd.text.toString()

            vm.login()

        }


    }


    override fun observeViewModel() {

        vm.loginData.observe(this, Observer<Boolean> {
            ARouter.getInstance()
                .build(RoutePath.Main.MAIN)
                .navFinish(this@LoginActivity)

        })


    }


}
