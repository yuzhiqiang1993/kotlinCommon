package com.yzq.base.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


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
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content,
    )
}