package com.yzq.base.extend

import android.provider.Settings
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.yzq.application.AppContext

/**
 * @description: 获取设备的Android版本号
 * @return String
 */
fun getAndroidVersion(): String {
    return android.os.Build.VERSION.RELEASE
}

/**
 * @description: 获取设备的型号
 * @return String
 */
fun getDeviceModel(): String {
    return android.os.Build.MODEL
}

/**
 * @description: 获取设备的制造商
 * @return String
 */
fun getDeviceManufacturer(): String {
    return android.os.Build.MANUFACTURER
}

/**
 * @description: 获取设备的品牌
 * @return String
 */
fun getDeviceBrand(): String {
    return android.os.Build.BRAND
}

/**
 * @description: 获取设备的序列号
 * @return String
 */
fun getDeviceSerial(): String {
    return android.os.Build.SERIAL
}

/**
 * @description: 获取设备的硬件名称
 * @return String
 */

fun getDeviceHardware(): String {
    return android.os.Build.HARDWARE
}

/**
 * @description: 获取广告id
 * @return String
 */
fun getAdvertisingId(): String = runCatching {
    val info = AdvertisingIdClient.getAdvertisingIdInfo(AppContext)
    info.id.toString()
}.getOrDefault("")

/**
 * @description: 获取设备id
 * @return String
 */
fun getDeviceId(): String = runCatching {
    Settings.Secure.getString(AppContext.contentResolver, Settings.Secure.ANDROID_ID)
}.getOrDefault("")

