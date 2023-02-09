package pt.andre.rijksmuseum.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pt.andre.rijksmuseum.presentation.details.DetailsScreen
import pt.andre.rijksmuseum.presentation.navigation.NavigationRoute.Details
import pt.andre.rijksmuseum.presentation.navigation.NavigationRoute.Overview
import pt.andre.rijksmuseum.presentation.overview.OverviewScreen

@Composable
internal fun RijksmuseumNavigation() {
    val controller = rememberNavController()

    NavHost(
        navController = controller,
        startDestination = Overview.route
    ) {
        composable(
            route = Overview.route,
            arguments = Overview.arguments
        ) {
            OverviewScreen(
                onItemClick = { id -> controller.navigate(Details.create(id)) }
            )
        }

        composable(
            route = Details.route,
            arguments = Details.arguments
        ) {
            DetailsScreen()
        }
    }
}
