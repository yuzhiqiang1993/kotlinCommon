package com.yzq.kotlincommon.ui

import android.support.v7.widget.Toolbar
import com.alibaba.android.arouter.facade.annotation.Route
import com.yzq.common.constants.RoutePath
import com.yzq.common.ui.BaseActivity
import com.yzq.common.utils.LocalSpUtils
import com.yzq.kotlincommon.R
import kotlinx.android.synthetic.main.activity_login.*


@Route(path = RoutePath.Main.LOGIN)
class LoginActivity : BaseActivity() {
    override fun getContentLayoutId(): Int {
        return R.layout.activity_login

    }

    override fun initWidget() {
        super.initWidget()
        var toolbar = findViewById<Toolbar>(R.id.toolbar)

        accountEt.setText(LocalSpUtils.account)
        pwdEt.setText(LocalSpUtils.pwd)
        initToolbar(toolbar, "登录")

        loginBtn.setOnClickListener {

            LocalSpUtils.account = accountEt.text.toString()
            LocalSpUtils.pwd = pwdEt.text.toString()

        }
    }


}
