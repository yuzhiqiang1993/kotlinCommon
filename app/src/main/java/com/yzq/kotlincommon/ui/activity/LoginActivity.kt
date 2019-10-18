package com.yzq.kotlincommon.ui.activity

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


            LocalSpUtils.account = input_account.text.toString()
            LocalSpUtils.pwd = input_pwd.text.toString()

            vm.loginWithCoroutine()
            //   vm.login()

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
