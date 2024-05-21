package com.yzq.kotlincommon.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.yzq.logger.Logger

private val DarkColorScheme = darkColorScheme(
    primary = BlueGrey600,
    secondary = BlueGrey500,
    tertiary = BlueGrey100,
    background = Color.White,
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
)

private val LightColorScheme = lightColorScheme(
    //应用的主色调
    primary = LightBlue600,
    //应用的次要色调
    secondary = LightBlue500,
    //应用的第三色调
    tertiary = LightBlue100,
    //应用的背景色
    background = Color.White,
    //应用的表面色
    surface = Color(0xFFFFFBFE),
    //位于主颜色之上的文本或图标的颜色
    onPrimary = Color.White,
    //位于次要颜色之上的文本或图标的颜色
    onSecondary = Color.White,
    //位于第三色之上的文本或图标的颜色
    onTertiary = Color.White,
    //位于背景色之上的文本或图标的颜色
    onBackground = Color(0xFF1C1B1F),
    //位于表面色之上的文本或图标的颜色
    onSurface = Color(0xFF1C1B1F),
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    Logger.i("darkTheme:$darkTheme")
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            Logger.i("dynamicColor:$dynamicColor")
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }


        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}