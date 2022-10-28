package com.yzq.gao_de_map.utils

import android.content.Context
import android.content.Intent
import android.net.Uri


/**
 * @description: 高德导航
 * @author : yzq
 * @date   : 2019/3/28
 * @time   : 18:19
 *
 */

object GaoDeNavUtil {


    private const val packageName = "com.autonavi.minimap"


    /**
     * 调高德地图并导航
     * @param context Context
     * @param dlat String
     * @param dlon String
     * @param dname String
     */
    fun openGaoDeNavi(
        context: Context,
        dlat: String,
        dlon: String,
        dname: String
    ) {
        val uriString: String
        val builder = StringBuilder("amapuri://route/plan?sourceApplication=maxuslife")

        builder.append("&dlat=").append(dlat)
            .append("&dlon=").append(dlon)
            .append("&dname=").append(dname)
            .append("&dev=0")
            .append("&t=2")
        uriString = builder.toString()
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setPackage(packageName)
        intent.data = Uri.parse(uriString)
        context.startActivity(intent)

    }


    /**
     * 直接调起高德地图
     * @param context Context
     */
    fun openGaoDeMap(context: Context) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.addCategory(Intent.CATEGORY_DEFAULT)

        /*直接打开地图*/
        val uri = Uri.parse("androidamap://rootmap?sourceApplication")
        intent.data = uri
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        //启动该页面即可
        context.startActivity(intent)
    }
}