package com.example.navig2.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberDecoratedNavEntries
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.navig2.iu.favorites.detail.FavoriteDetailRoute
import com.example.navig2.iu.favorites.FavoritesRoute
import com.example.navig2.iu.home.HomeRoute
import com.example.navig2.iu.profile.ProfileRoute

@Composable
fun AppNavigation() {
    // 1. Destinos principales de la aplicación
    val destinations: List<AppDestination> = listOf(
        AppDestination.Home,
        AppDestination.Favorites,
        AppDestination.Profile
    )

    // 2. Destino seleccionado en la NavigationBar
    var selectedIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    // 3. Una pila independiente para cada destino principal
    val homeBackStack = rememberNavBackStack(AppDestination.Home)
    val favoritesBackStack = rememberNavBackStack(AppDestination.Favorites)
    val profileBackStack = rememberNavBackStack(AppDestination.Profile)

    // 4. EntryProvider: Define qué pantalla corresponde a cada AppDestination
    val appEntryProvider =
        entryProvider<NavKey> {
/*
* HOME
*/
            entry<AppDestination.Home> {
                HomeRoute()
            }
            /*
            * FAVORITES
            */
            entry<AppDestination.Favorites> {
                FavoritesRoute(

                    onFavoriteClick = { favoriteId ->
                        favoritesBackStack.add(
                            AppDestination.FavoriteDetail(
                                favoriteId = favoriteId
                            )
                        )
                    }
                )
            }
            /*
            * FAVORITE DETAIL
            */
            entry<AppDestination.FavoriteDetail> { destination ->
                FavoriteDetailRoute(
                    favoriteId =
                        destination.favoriteId,
                    onBack = {
                        favoritesBackStack
                            .removeLastOrNull()
                    }
                )
            }
            /*
            * PROFILE
            */
            entry<AppDestination.Profile> {
                ProfileRoute()
            }
        }

    // 5. Decorar CADA back stack
    val homeEntries = rememberDecoratedNavEntries(
        backStack = homeBackStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = appEntryProvider
    )

    val favoritesEntries = rememberDecoratedNavEntries(
        backStack = favoritesBackStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = appEntryProvider
    )

    val profileEntries = rememberDecoratedNavEntries(
        backStack = profileBackStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = appEntryProvider
    )

    // 6. Determinar qué pila está activa
    val currentBackStack = when (selectedIndex) {
        0 -> homeBackStack
        1 -> favoritesBackStack
        else -> profileBackStack
    }

    // 7. Obtener las entradas decoradas de la pila activa
    val currentEntries = when (selectedIndex) {
        0 -> homeEntries
        1 -> favoritesEntries
        else -> profileEntries
    }

    // 8. Comportamiento del botón Back
    BackHandler(
        enabled = selectedIndex != 0 && currentBackStack.size == 1
    ) {
        selectedIndex = 0
    }

    // 9. Interfaz principal
    Scaffold(
        bottomBar = {
            NavigationBar {
                destinations.forEachIndexed { index, destination ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                        },
                        icon = {
                            when (destination) {
                                AppDestination.Home -> {
                                    Icon(
                                        imageVector = Icons.Default.Home,
                                        contentDescription = "Inicio"
                                    )
                                }
                                AppDestination.Favorites -> {
                                    Icon(
                                        imageVector = Icons.Default.Favorite,
                                        contentDescription = "Favoritos"
                                    )
                                }
                                AppDestination.Profile -> {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = "Perfil"
                                    )
                                }
                                else -> {}
                            }
                        },
                        label = {
                            Text(
                                text = when (destination) {
                                    AppDestination.Home -> "Inicio"
                                    AppDestination.Favorites -> "Favoritos"
                                    AppDestination.Profile -> "Perfil"
                                    else -> ""
                                }
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        // 10. Mostrar las entradas de la pila activa
        NavDisplay(
            entries = currentEntries,
            modifier =
                Modifier.padding(innerPadding),
            onBack = {
                currentBackStack
                    .removeLastOrNull()
            }
        )
    }
}