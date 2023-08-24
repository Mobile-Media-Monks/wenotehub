package com.example.wenotehub

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.wenotehub.core.PermissionRequest
import com.example.wenotehub.ui.screen.MainScreen
import com.example.wenotehub.ui.theme.WeNoteHubTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.R)
    private val getPermission =
        PermissionRequest(this,
            listOf(
                Manifest.permission.MANAGE_EXTERNAL_STORAGE,
            ),
            onDenied = {
                Toast.makeText(this, "Denied", Toast.LENGTH_SHORT).show()
            },
            onRationale = {
                Toast.makeText(this, "Rational", Toast.LENGTH_SHORT).show()
            })









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

    /*private fun newBackupDatabase(){
        backupDir.createNewFile()
        backup
            .database(appDatabase)
            .enableLogDebug(true)
            .backupIsEncrypted(false)
            .backupLocation(RoomBackupTwo.BACKUP_FILE_LOCATION_CUSTOM_FILE)
            .backupLocationCustomFile(backupDir)
            .maxFileCount(5)
            .apply {
                onCompleteListener { success, message, exitCode ->
                    Log.d("GUARDO", "success: $success, message: $message, exitCode: $exitCode")
                    if (success) restartApp(Intent(this@MainActivity, MainActivity::class.java))
                }
            }
            .backup()
    }*/

  /*  private fun restoreBackupDatabase(){
        backup
            .database(appDatabase)
            .enableLogDebug(true)
            .backupIsEncrypted(false)
            .backupLocation(RoomBackup.BACKUP_FILE_LOCATION_CUSTOM_FILE)
            .backupLocationCustomFile(backupDir)
            .maxFileCount(5)
            .apply {
                onCompleteListener { success, message, exitCode ->
                    Log.d("GUARDO", "success: $success, message: $message, exitCode: $exitCode")
                    if (success) restartApp(Intent(this@MainActivity, MainActivity::class.java))
                }
            }
            .restore()
    }*/
}





