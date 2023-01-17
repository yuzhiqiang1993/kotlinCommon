package com.yzq.permission

import android.content.Context
import com.hjq.permissions.XXPermissions

/**
 * 是否已经有权限
 *
 * @param permissions
 */
fun Context.permissionsGranted(vararg permissions: String) =
    XXPermissions.isGranted(this, permissions)