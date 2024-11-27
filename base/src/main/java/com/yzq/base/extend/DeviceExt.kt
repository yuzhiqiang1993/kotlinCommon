package com.yzq.base.extend

import android.content.Context
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.DisplayMetrics
import com.yzq.application.AppContext
import java.util.Locale

/**
 * @description: 获取设备的Android版本号
 * @return String
 */
fun getAndroidVersion(): String {
    return android.os.Build.VERSION.RELEASE ?: "Unknown"
}

/**
 * @description: 获取设备的型号
 * @return String
 */
fun getDeviceModel(): String {
    return android.os.Build.MODEL ?: "Unknown"
}

/**
 * @description: 获取设备的制造商
 * @return String
 */
fun getDeviceManufacturer(): String {
    return android.os.Build.MANUFACTURER ?: "Unknown"
}

/**
 * @description: 获取设备的品牌
 * @return String
 */
fun getDeviceBrand(): String {
    return android.os.Build.BRAND ?: "Unknown"
}

/**
 * @description: 获取设备的序列号（仅限于API 28以下，28及以上获取需要权限）
 * @return String
 */
fun getDeviceSerial(): String {
    return if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.P) {
        android.os.Build.SERIAL ?: "Unknown"
    } else {
        "Unavailable" // API 28及以上需要特定权限
    }
}

/**
 * @description: 获取设备的硬件名称
 * @return String
 */
fun getDeviceHardware(): String {
    return android.os.Build.HARDWARE ?: "Unknown"
}

/**
 * @description: 获取设备ID
 * @return String
 */
fun getAndroidId(): String = runCatching {
    Settings.Secure.getString(AppContext.contentResolver, Settings.Secure.ANDROID_ID)
}.getOrDefault("Unknown")

/**
 * @description: 获取运营商名称
 * @return String
 */
fun getCarrierName(): String {
    val telephonyManager =
        AppContext.getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager
    return telephonyManager?.networkOperatorName ?: "Unknown"
}

/**
 * @description: 获取屏幕分辨率
 * @return String
 */
fun getScreenResolution(): String {
    val displayMetrics: DisplayMetrics = AppContext.resources.displayMetrics
    return "${displayMetrics.widthPixels}x${displayMetrics.heightPixels}"
}

/**
 * @description: 获取语言和地区
 * @return String
 */
fun getLocale(): String {
    val locale: Locale = Locale.getDefault()
    return "${locale.language}-${locale.country}"
}

/**
 * @description: 获取设备的主板名称
 * @return String
 */
fun getDeviceBoard(): String {
    return android.os.Build.BOARD ?: "Unknown"
}

/**
 * @description: 获取设备的产品名称
 * @return String
 */
fun getDeviceProduct(): String {
    return android.os.Build.PRODUCT ?: "Unknown"
}

/**
 * @description: 获取设备的显示名称（显示版本）
 * @return String
 */
fun getDeviceDisplay(): String {
    return android.os.Build.DISPLAY ?: "Unknown"
}

/**
 * @description: 获取完整的设备信息，用于请求头
 * @return Map<String, String>
 */
fun getDeviceInfo(): Map<String, String> {
    return mapOf(
        "AndroidVersion" to getAndroidVersion(),
        "DeviceModel" to getDeviceModel(),
        "DeviceManufacturer" to getDeviceManufacturer(),
        "DeviceBrand" to getDeviceBrand(),
        "DeviceSerial" to getDeviceSerial(),
        "DeviceHardware" to getDeviceHardware(),
        "AndroidId" to getAndroidId(),
        "CarrierName" to getCarrierName(),
        "ScreenResolution" to getScreenResolution(),
        "Locale" to getLocale(),
        "DeviceBoard" to getDeviceBoard(),
        "DeviceProduct" to getDeviceProduct(),
        "DeviceDisplay" to getDeviceDisplay()
    )
}