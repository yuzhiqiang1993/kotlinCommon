package com.yzq.common.utils

import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.SPUtils


/**
 * @description: 管理本地存储的sp
 * @author : yzq
 * @date   : 2018/11/6
 * @time   : 15:23
 *
 */

class LocalSpUtils {


    companion object {

        private val SP_NAME = AppUtils.getAppPackageName()
        /*用户名*/
        private val ACCOUNT = "account"
        /*密码*/
        private val PWD = "pwd"


        fun putAccount(account: String) {
            SPUtils.getInstance(SP_NAME).put(ACCOUNT, account)

        }

        fun getAccount(): String? {
            return SPUtils.getInstance(SP_NAME).getString(ACCOUNT)

        }

        fun putPwd(pwd: String) {
            SPUtils.getInstance(SP_NAME).put(ACCOUNT, pwd)

        }

        fun getPwd(): String? {
            return SPUtils.getInstance(SP_NAME).getString(PWD)

        }

    }


}