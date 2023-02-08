package pt.andre.rijksmuseum.presentation.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NavigationRoute(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {

    object Overview : NavigationRoute(route = "overview")

    object Details : NavigationRoute(
        route = "details/{id}",
        arguments = listOf(
            navArgument("id") { type = NavType.StringType }
        )
    ) {
        fun create(id: String): String = "details/$id"
    }
}
