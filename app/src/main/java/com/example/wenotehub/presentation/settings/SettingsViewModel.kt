package com.example.wenotehub.presentation.settings

import android.content.Context
import android.content.Intent
import android.os.Environment
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.wenotehub.MainActivity
import com.example.wenotehub.core.RoomBackupManajer
import com.example.wenotehub.data.local.AppDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import de.raphaelebner.roomdatabasebackup.core.RoomBackup
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val roomManager: RoomBackupManajer,
    private val appDatabase: AppDatabase,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val extStorageDirectory = Environment.getExternalStorageDirectory()
        .toString() + "/" + Environment.DIRECTORY_DOCUMENTS

    private val backupDir = File(extStorageDirectory, "backup.sql3")

    fun onCreateBackup(){
        backupDir.createNewFile()
        roomManager
            .database(appDatabase)
            .enableLogDebug(true)
            .backupIsEncrypted(false)
            .backupLocation(RoomBackupManajer.BACKUP_FILE_LOCATION_EXTERNAL)
            .maxFileCount(5)
            .apply {
                onCompleteListener { success, message, exitCode ->
                    Log.d("GUARDO", "success: $success, message: $message, exitCode: $exitCode")
                    if (success) restartApp(Intent(context, MainActivity::class.java))
                }
            }
            .backup()
    }


    fun onRestoreBackup(){
        roomManager
            .database(appDatabase)
            .enableLogDebug(true)
            .backupIsEncrypted(false)
            .backupLocation(RoomBackup.BACKUP_FILE_LOCATION_CUSTOM_FILE)
            .backupLocationCustomFile(backupDir)
            .maxFileCount(5)
            .apply {
                onCompleteListener { success, message, exitCode ->
                    Log.d("GUARDO", "success: $success, message: $message, exitCode: $exitCode")
                    if (success) restartApp(Intent(context, MainActivity::class.java))
                }
            }
            .restore()
    }
}