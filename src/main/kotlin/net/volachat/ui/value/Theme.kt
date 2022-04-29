package net.volachat.ui.value

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Color set
val LightTheme = lightColors() // TODO :
val DarkTheme = darkColors(
    primary = R.color.PictonBlue,
    onPrimary = Color.White,
    secondary = R.color.Elephant,
    onSecondary = Color.White,
    surface = R.color.BigStone,
    error = R.color.WildWatermelon
)

@Composable
fun volachatTheme(
    isDark: Boolean = true,
    content: @Composable ColumnScope.() -> Unit,
) {
    MaterialTheme(
        colors = if (isDark) DarkTheme else LightTheme,
        typography = volachatTypography
    ) {
        Surface {
            Column {
                content()
            }
        }
    }
}