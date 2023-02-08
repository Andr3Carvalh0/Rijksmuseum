package pt.andre.rijksmuseum.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import pt.andre.rijksmuseum.presentation.navigation.RijksmuseumNavigation
import pt.andre.rijksmuseum.presentation.ui.RijksmuseumTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RijksmuseumTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    RijksmuseumNavigation()
                }
            }
        }
    }
}
