package com.yzq.common.constants

/**
 * @description: 路由path
 * @author : yzq
 * @date   : 2018/10/30
 * @time   : 14:05
 *
 */

object RoutePath {

    object Main {
        const val TASK = "/main/task"
        const val MAIN = "/main/main"
        const val MOVIES = "/main/movies"
        const val IMG_COMPRESS = "/main/imgCompress"
        const val DIALOG = "/main/dialog"
        const val ZXING = "/main/zxing"
        const val LOGIN = "/main/login"
        const val DROP_DOWN_MENU = "/main/dropDownMenu"
        const val EMAS = "/main/emas"
        const val FLOW = "/main/flow"
        const val MOSHI = "/main/moshi"
        const val NETWORK = "/main/network"
        const val FLEX_BOX = "/main/flexBox"
        const val IMG_LIST = "/main/imgList"
        const val FRAGMENT = "/main/fragment"
        const val COROUTINE = "/main/coroutine"
        const val BS_DIFF = "/main/bsDiff"
        const val BUGLY = "/main/bugly"
        const val ROOM = "/main/room"
        const val DOWNLOAD = "/main/download"
        const val VIEW_PAGER: String = "/main/viewPager"
        const val WEB_VIEW: String = "/main/webView"
        const val DATA_BINDING: String = "/main/dataBinding"
        const val VIEW_BINDING: String = "/main/viewBinding"
        const val VIEW_BINDING_DELEGATE: String = "/main/viewBindingDelegate"
        const val API_CALL: String = "/main/apiCall"
        const val THREAD_POOL: String = "/main/threadPool"
        const val SERVICE: String = "/main/service"
        const val STORAGE: String = "/main/storage"
        const val LOTTIE: String = "/main/lottie"
        const val ASR: String = "/main/asr"
        const val IMAGE_LOAD: String = "/main/imageLoad"
        const val BLUETOOTH: String = "/main/bluetooth"
        const val REACT_NATIVE: String = "/main/reactNative"
        const val JAVA_ACTIVITY: String = "/main/javaActivity"
        const val COMPOSE: String = "/main/compose"
        const val USER_AUTH: String = "/main/userAuth"

    }


    object Login {
        //一键登录
        const val ONE_KEY_LOGIN = "/login/onekeyLogin"

        //免密登录
        const val EXEMPT_LOGIN = "/login/exemptLogin"

        //密码登录
        const val LOGIN_BY_PWD = "/login/loginByPwd"

        //短信登录
        const val LOGIN_BY_SMS = "/login/loginBySms"

        //找回密码
        const val RETIREVE_PWD = "/login/retirevePwd"

        //设置新密码
        const val SET_NEW_PWD = "/login/setNewPwd"

        //完善注册信息
        const val COMPLETE_REGISTER_INFO = "/login/completeRegisterInfo"

        //一键登录(popup)
        const val ONE_KEY_LOGIN_POPUP = "/login/onekeyLoginPopup"

        //免密登录(popup)
        const val EXEMPT_LOGIN_POPUP = "/login/exemptLoginPopup"

        //密码登录(popup)
        const val LOGIN_BY_PWD_POPUP = "/login/loginByPwdPopup"

        //短信登录(popup)
        const val LOGIN_BY_SMS_POPUP = "/login/loginBySmsPopup"

        //找回密码(popup)
        const val RETIREVE_PWD_POPUP = "/login/retirevePwdPopup"

        //设置新密码(popup)
        const val SET_NEW_PWD_POPUP = "/login/setNewPwdPopup"

        //完善注册信息(popup)
        const val COMPLETE_REGISTER_INFO_POPUP = "/login/completeRegisterInfoPopup"


    }

    object GaoDe {
        const val GAO_DE = "/gaoDe/gaoDeMap"
    }

    object Player {
        const val PLAYER = "/player/player"
    }


    object Emas {
        const val CRASH = "/emas/crash"
    }

}