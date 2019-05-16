package com.yzq.kotlincommon.ui

import androidx.appcompat.widget.Toolbar
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.BarUtils
import com.yzq.common.constants.RoutePath
import com.yzq.common.ui.BaseActivity
import com.yzq.common.utils.LocalSpUtils
import com.yzq.kotlincommon.R
import kotlinx.android.synthetic.main.activity_login.*


/**
 * @description: SharedPreference相关
 * @author : yzq
 * @date   : 2019/4/30
 * @time   : 13:39
 *
 */

@Route(path = RoutePath.Main.LOGIN)
class LoginActivity : BaseActivity() {

    override fun getContentLayoutId(): Int {

        return R.layout.activity_login
    }


    override fun initWidget() {
        super.initWidget()

        BarUtils.setStatusBarVisibility(this,false)

        input_account.setText(LocalSpUtils.account)
        input_pwd.setText(LocalSpUtils.pwd)


        btn_login.setOnClickListener {

            LocalSpUtils.account = input_account.text.toString()
            LocalSpUtils.pwd = input_pwd.text.toString()

        }
    }


}
