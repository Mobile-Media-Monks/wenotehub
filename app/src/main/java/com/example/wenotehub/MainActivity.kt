package com.example.wenotehub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.*
import androidx.room.InvalidationTracker
import com.example.wenotehub.presentation.home.HomeViewModel
import com.example.wenotehub.ui.components.WeScaffold
import com.example.wenotehub.ui.theme.WeNoteHubTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            var color by rememberSaveable {
                mutableStateOf(false)
            }

            WeNoteHubTheme(darkTheme = color) {
                WeScaffold {

                }
            }
        }
    }
}
