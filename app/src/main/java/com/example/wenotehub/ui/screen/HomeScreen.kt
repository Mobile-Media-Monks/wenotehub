package com.example.wenotehub.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.wenotehub.presentation.home.HomeViewModel
import com.example.wenotehub.ui.components.NoteCard
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.shouldShowRationale


@Composable
fun HomeScreen(
    navController: NavHostController, viewModel: HomeViewModel = hiltViewModel(),
) {

    val noteList by viewModel.notes.observeAsState(listOf())


    Box(
        Modifier
            .fillMaxSize()
    ) {
        Column {
             LazyColumn(
                 verticalArrangement = Arrangement.spacedBy(24.dp),
                 contentPadding = PaddingValues(vertical = 50.dp)
             ) {
                 item(1){
                     Text(
                         text = "Notes",
                         style = MaterialTheme.typography.displaySmall,
                         fontWeight = FontWeight.ExtraBold,
                         modifier = Modifier.padding(top= 30.dp)
                     )
                 }
                 items(noteList) { note ->
                     NoteCard(note)
                 }
             }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
fun PermissionState.isPermanentlyDenied(): Boolean {
    return !status.shouldShowRationale && !status.isGranted
}







