package com.example.navig2.iu.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
@Composable
fun ProfileRoute(
    viewModel: ProfileViewModel = viewModel()
) {
    val uiState by
    viewModel.uiState.collectAsStateWithLifecycle()
    ProfileScreen(
        uiState = uiState
    )
}
@Composable
fun ProfileScreen(
    uiState: ProfileUiState
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Perfil"
        )
        Text(
            text = uiState.userName
        )
        Text(
            text = uiState.role
        )
    }
}