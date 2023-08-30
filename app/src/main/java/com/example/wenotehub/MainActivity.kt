package com.example.wenotehub

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.wenotehub.ui.screen.MainScreen
import com.example.wenotehub.ui.theme.WeNoteHubTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {






    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        setContent {
            WeNoteHubTheme {



                MainScreen()
            }
        }

    }



   /* @RequiresApi(Build.VERSION_CODES.R)
    private fun Context.openAppSetting() {
        Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
            data = Uri.parse("package:$packageName")
        }.let(::startActivity)
    }*/

}





