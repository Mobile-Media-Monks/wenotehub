package com.example.wenotehub.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.wenotehub.presentation.settings.SettingsViewModel

@Composable
fun SettingsScreen(
    navController: NavHostController,
    viewModel: SettingsViewModel = hiltViewModel(),
) {


    Box(Modifier.fillMaxSize()) {
        Column {
            Button(onClick = { viewModel.onCreateBackup() }) {
                Text(text = "Guardar")
            }
            Button(onClick = { viewModel.onRestoreBackup() }) {
                Text(text = "Restaurar")
            }
        }
    }
}