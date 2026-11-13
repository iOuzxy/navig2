package com.example.navig2.iu.favorites

data class FavoriteItemUiModel(
    val id: Int,
    val name: String,
    val description: String
)
data class FavoritesUiState(

    val title: String = "Favoritos",
    val favorites: List<FavoriteItemUiModel> = emptyList()
)