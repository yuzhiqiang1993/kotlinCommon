package com.yzq.gao_de_map.ext

import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import com.yzq.application.AppManager
import com.yzq.application.isAppInstalled

private const val gaoDeMapPackageName = "com.autonavi.minimap"
fun ComponentActivity.openGaoDeNavi(
    dlat: String,
    dlon: String,
    dname: String,
) {
    kotlin.runCatching {
        val uriString: String
        val builder = StringBuilder("amapuri://route/plan?sourceApplication=maxuslife")

        builder.append("&dlat=").append(dlat)
            .append("&dlon=").append(dlon)
            .append("&dname=").append(dname)
            .append("&dev=0")
            .append("&t=0")
        uriString = builder.toString()
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setPackage(gaoDeMapPackageName)
        intent.data = Uri.parse(uriString)
        startActivity(intent)
    }


}


/**
 * 直接调起高德地图
 */
fun ComponentActivity.openGaoDeMap() {

    kotlin.runCatching {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        //直接打开地图
        val uri = Uri.parse("androidamap://rootmap?sourceApplication")
        intent.data = uri
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        //启动该页面即可
        startActivity(intent)
    }
}


fun ComponentActivity.gaoDeMapHasInstalled() = AppManager.isAppInstalled("com.autonavi.minimap")
