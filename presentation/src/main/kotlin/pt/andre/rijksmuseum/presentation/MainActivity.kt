package pt.andre.rijksmuseum.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import pt.andre.rijksmuseum.presentation.navigation.RijksmuseumNavigation
import pt.andre.rijksmuseum.presentation.ui.RijksmuseumTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RijksmuseumTheme {
                RijksmuseumNavigation()
            }
        }
    }
}
