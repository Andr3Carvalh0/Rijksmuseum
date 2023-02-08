package pt.andre.rijksmuseum.presentation.ui

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.core.view.WindowCompat
import pt.andre.rijksmuseum.presentation.R

@Composable
internal fun RijksmuseumTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = lightColorScheme(
        primary = colorResource(id = R.color.rijksmuseum_red),
        secondary = colorResource(id = R.color.rijksmuseum_red),
        surface = colorResource(id = R.color.white),
        primaryContainer = colorResource(id = R.color.rijksmuseum_blue),
        onPrimaryContainer = colorResource(id = R.color.white)
    )

    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            window.navigationBarColor = colorScheme.surface.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
