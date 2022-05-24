package ru.mobileup.core.theme

import android.annotation.SuppressLint
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import ru.mobileup.core.message.ui.LocalMessageOffsets

@SuppressLint("UnrememberedMutableState")
@Composable
fun AppTheme(
    themeType: ThemeType = ThemeType.Default,
    content: @Composable () -> Unit
) {
    val materialColors = if (themeType == ThemeType.LightTheme) {
        getMaterialLightColors()
    } else {
        getMaterialDarkColors()
    }

    val messageOffsets = remember { mutableStateMapOf<Int, Int>() }

    CompositionLocalProvider(
        LocalThemeType provides themeType,
        LocalMessageOffsets provides messageOffsets
    ) {
        MaterialTheme(
            colors = materialColors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

val LocalThemeType = staticCompositionLocalOf {
    ThemeType.Default
}

val themeType: ThemeType
    @Composable
    @ReadOnlyComposable
    get() = LocalThemeType.current