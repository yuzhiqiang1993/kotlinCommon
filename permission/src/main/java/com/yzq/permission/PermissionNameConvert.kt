package com.yzq.permission

import android.content.Context
import android.os.Build
import com.hjq.permissions.Permission


/**
 * @description 权限名称映射
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2023/1/10
 * @time    15:19
 */

object PermissionNameConvert {
    /**
     * 获取权限名称
     */
    fun getPermissionString(context: Context, permissions: List<String>): String {
        return listToString(context, permissionsToNames(context, permissions))
    }

    /**
     * 将权限列表拼接成字符串
     */
    private fun listToString(context: Context, hints: List<String>): String {
        if (hints.isEmpty()) {
            return context.getString(R.string.common_permission_unknown)
        }
        val builder = StringBuilder()
        for (text in hints) {
            if (builder.isEmpty()) {
                builder.append(text)
            } else {
                builder.append("、")
                    .append(text)
            }
        }
        return builder.toString()
    }

    /**
     * 将权限列表转换成对应名称列表
     */
    private fun permissionsToNames(context: Context, permissions: List<String>): List<String> {
        val permissionNames: MutableList<String> = mutableListOf()
        for (permission in permissions) {
            when (permission) {
                Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE -> {
                    val hint = context.getString(R.string.common_permission_storage)
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint)
                    }
                }
                Permission.READ_MEDIA_IMAGES, Permission.READ_MEDIA_VIDEO -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        val hint = context.getString(R.string.common_permission_image_and_video)
                        if (!permissionNames.contains(hint)) {
                            permissionNames.add(hint)
                        }
                    }
                }
                Permission.READ_MEDIA_AUDIO -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        val hint = context.getString(R.string.common_permission_music_and_audio)
                        if (!permissionNames.contains(hint)) {
                            permissionNames.add(hint)
                        }
                    }
                }
                Permission.CAMERA -> {
                    val hint = context.getString(R.string.common_permission_camera)
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint)
                    }
                }
                Permission.RECORD_AUDIO -> {
                    val hint = context.getString(R.string.common_permission_microphone)
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint)
                    }
                }
                Permission.ACCESS_FINE_LOCATION, Permission.ACCESS_COARSE_LOCATION, Permission.ACCESS_BACKGROUND_LOCATION -> {
                    var hint: String
                    hint = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
                        !permissions.contains(Permission.ACCESS_FINE_LOCATION) &&
                        !permissions.contains(Permission.ACCESS_COARSE_LOCATION)
                    ) {
                        context.getString(R.string.common_permission_location_background)
                    } else {
                        context.getString(R.string.common_permission_location)
                    }
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint)
                    }
                }
                Permission.BODY_SENSORS, Permission.BODY_SENSORS_BACKGROUND -> {
                    var hint: String
                    hint = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                        !permissions.contains(Permission.BODY_SENSORS)
                    ) {
                        context.getString(R.string.common_permission_body_sensors_background)
                    } else {
                        context.getString(R.string.common_permission_body_sensors)
                    }
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint)
                    }
                }
                Permission.BLUETOOTH_SCAN, Permission.BLUETOOTH_CONNECT, Permission.BLUETOOTH_ADVERTISE -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        val hint = context.getString(R.string.common_permission_nearby_devices)
                        if (!permissionNames.contains(hint)) {
                            permissionNames.add(hint)
                        }
                    }
                }
                Permission.NEARBY_WIFI_DEVICES -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        val hint = context.getString(R.string.common_permission_nearby_devices)
                        if (!permissionNames.contains(hint)) {
                            permissionNames.add(hint)
                        }
                    }
                }
                Permission.READ_PHONE_STATE, Permission.CALL_PHONE, Permission.ADD_VOICEMAIL, Permission.USE_SIP, Permission.READ_PHONE_NUMBERS, Permission.ANSWER_PHONE_CALLS -> {
                    val hint = context.getString(R.string.common_permission_phone)
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint)
                    }
                }
                Permission.GET_ACCOUNTS, Permission.READ_CONTACTS, Permission.WRITE_CONTACTS -> {
                    val hint = context.getString(R.string.common_permission_contacts)
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint)
                    }
                }
                Permission.READ_CALENDAR, Permission.WRITE_CALENDAR -> {
                    val hint = context.getString(R.string.common_permission_calendar)
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint)
                    }
                }
                Permission.READ_CALL_LOG, Permission.WRITE_CALL_LOG, Permission.PROCESS_OUTGOING_CALLS -> {
                    val hint =
                        context.getString(if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) R.string.common_permission_call_logs else R.string.common_permission_phone)
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint)
                    }
                }
                Permission.ACTIVITY_RECOGNITION -> {
                    val hint =
                        context.getString(if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) R.string.common_permission_activity_recognition_api30 else R.string.common_permission_activity_recognition_api29)
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint)
                    }
                }
                Permission.ACCESS_MEDIA_LOCATION -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        val hint =
                            context.getString(R.string.common_permission_access_media_location)
                        if (!permissionNames.contains(hint)) {
                            permissionNames.add(hint)
                        }
                    }
                }
                Permission.SEND_SMS, Permission.RECEIVE_SMS, Permission.READ_SMS, Permission.RECEIVE_WAP_PUSH, Permission.RECEIVE_MMS -> {
                    val hint = context.getString(R.string.common_permission_sms)
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint)
                    }
                }
                Permission.MANAGE_EXTERNAL_STORAGE -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        val hint = context.getString(R.string.common_permission_all_file_access)
                        if (!permissionNames.contains(hint)) {
                            permissionNames.add(hint)
                        }
                    }
                }
                Permission.REQUEST_INSTALL_PACKAGES -> {
                    val hint = context.getString(R.string.common_permission_install_unknown_apps)
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint)
                    }
                }
                Permission.SYSTEM_ALERT_WINDOW -> {
                    val hint = context.getString(R.string.common_permission_display_over_other_apps)
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint)
                    }
                }
                Permission.WRITE_SETTINGS -> {
                    val hint = context.getString(R.string.common_permission_modify_system_settings)
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint)
                    }
                }
                Permission.NOTIFICATION_SERVICE -> {
                    val hint = context.getString(R.string.common_permission_allow_notifications)
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint)
                    }
                }
                Permission.POST_NOTIFICATIONS -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        val hint = context.getString(R.string.common_permission_post_notifications)
                        if (!permissionNames.contains(hint)) {
                            permissionNames.add(hint)
                        }
                    }
                }
                Permission.BIND_NOTIFICATION_LISTENER_SERVICE -> {
                    val hint =
                        context.getString(R.string.common_permission_allow_notifications_access)
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint)
                    }
                }
                Permission.PACKAGE_USAGE_STATS -> {
                    val hint = context.getString(R.string.common_permission_apps_with_usage_access)
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint)
                    }
                }
                Permission.SCHEDULE_EXACT_ALARM -> {
                    val hint = context.getString(R.string.common_permission_alarms_reminders)
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint)
                    }
                }
                Permission.ACCESS_NOTIFICATION_POLICY -> {
                    val hint = context.getString(R.string.common_permission_do_not_disturb_access)
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint)
                    }
                }
                Permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS -> {
                    val hint = context.getString(R.string.common_permission_ignore_battery_optimize)
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint)
                    }
                }
                Permission.BIND_VPN_SERVICE -> {
                    val hint = context.getString(R.string.common_permission_vpn)
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint)
                    }
                }
                Permission.PICTURE_IN_PICTURE -> {
                    val hint = context.getString(R.string.common_permission_picture_in_picture)
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint)
                    }
                }
                Permission.GET_INSTALLED_APPS -> {
                    val hint = context.getString(R.string.common_permission_get_installed_apps)
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint)
                    }
                }
                else -> {}
            }
        }
        return permissionNames
    }
}