package com.example.wenotehub.core

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.room.RoomDatabase
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.comparator.LastModifiedFileComparator
import de.raphaelebner.roomdatabasebackup.core.AESEncryptionHelper
import de.raphaelebner.roomdatabasebackup.core.AESEncryptionManager
import de.raphaelebner.roomdatabasebackup.core.OnCompleteListener
import kotlinx.coroutines.runBlocking
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Arrays
import java.util.Calendar
import java.util.Locale
import javax.crypto.BadPaddingException
import com.google.common.io.Files.copy
import javax.inject.Inject


class RoomBackupManajer@Inject constructor(private val context: Context) {

    companion object {
        private var TAG = "debug_RoomBackupTwo"
        private lateinit var INTERNAL_BACKUP_PATH: File
        private lateinit var TEMP_BACKUP_PATH: File
        private lateinit var TEMP_BACKUP_FILE: File
        private lateinit var EXTERNAL_BACKUP_PATH: File
        private lateinit var DATABASE_FILE: File

        private var currentProcess: Int? = null
        private const val PROCESS_BACKUP = 1
        private const val PROCESS_RESTORE = 2


        /**
         * Code for internal backup location, used for [backupLocation]
         */
        const val BACKUP_FILE_LOCATION_INTERNAL = 1

        /**
         * Code for external backup location, used for [backupLocation]
         */
        const val BACKUP_FILE_LOCATION_EXTERNAL = 2


        /**
         * Code for custom backup file location, used for [backupLocation]
         */
        const val BACKUP_FILE_LOCATION_CUSTOM_FILE = 4
    }

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var dbName: String

    private var roomDatabase: RoomDatabase? = null
    private var enableLogDebug: Boolean = false
    private var restartIntent: Intent? = null
    private var onCompleteListener: OnCompleteListener? = null
    private var customRestoreDialogTitle: String = "Choose file to restore"
    private var customBackupFileName: String? = null
    private var backupIsEncrypted: Boolean = false
    private var maxFileCount: Int? = null
    private var encryptPassword: String? = null
    private var backupLocation: Int = BACKUP_FILE_LOCATION_INTERNAL
    private var backupLocationCustomFile: File? = null

    /**
     * Set RoomDatabase instance
     *
     * @param roomDatabase RoomDatabase
     */
    fun database(roomDatabase: RoomDatabase): RoomBackupManajer {
        this.roomDatabase = roomDatabase
        return this
    }

    /**
     * Set LogDebug enabled / disabled
     *
     * @param enableLogDebug Boolean
     */
    fun enableLogDebug(enableLogDebug: Boolean): RoomBackupManajer {
        this.enableLogDebug = enableLogDebug
        return this
    }

    /**
     * Set Intent in which to boot after App restart
     *
     * @param restartIntent Intent
     */
    fun restartApp(restartIntent: Intent): RoomBackupManajer {
        this.restartIntent = restartIntent
        restartApp()
        return this
    }

    /**
     * Set onCompleteListener, to run code when tasks completed
     *
     * @param onCompleteListener OnCompleteListener
     */
    fun onCompleteListener(onCompleteListener: OnCompleteListener): RoomBackupManajer {
        this.onCompleteListener = onCompleteListener
        return this
    }

    /**
     * Set onCompleteListener, to run code when tasks completed
     *
     * @param listener (success: Boolean, message: String) -> Unit
     */
    fun onCompleteListener(listener: (success: Boolean, message: String, exitCode: Int) -> Unit): RoomBackupManajer {
        this.onCompleteListener = object : OnCompleteListener {
            override fun onComplete(success: Boolean, message: String, exitCode: Int) {
                listener(success, message, exitCode)
            }
        }
        return this
    }

    /**
     * Set custom log tag, for detailed debugging
     *
     * @param customLogTag String
     */
    fun customLogTag(customLogTag: String): RoomBackupManajer {
        TAG = customLogTag
        return this
    }

    /**
     * Set custom Restore Dialog Title, default = "Choose file to restore"
     *
     * @param customRestoreDialogTitle String
     */
    fun customRestoreDialogTitle(customRestoreDialogTitle: String): RoomBackupManajer {
        this.customRestoreDialogTitle = customRestoreDialogTitle
        return this
    }


    /**
     * Set you backup location. Available values see: [BACKUP_FILE_LOCATION_INTERNAL], [BACKUP_FILE_LOCATION_EXTERNAL], [BACKUP_FILE_LOCATION_CUSTOM_DIALOG] or [BACKUP_FILE_LOCATION_CUSTOM_FILE]
     *
     *
     * @param backupLocation Int, default = [BACKUP_FILE_LOCATION_INTERNAL]
     */
    fun backupLocation(backupLocation: Int): RoomBackupManajer {
        this.backupLocation = backupLocation
        return this
    }

    /**
     * Set a custom file where to save or restore a backup.
     * can be used for backup and restore
     *
     * Only available if [backupLocation] is set to [BACKUP_FILE_LOCATION_CUSTOM_FILE]
     *
     * @param backupLocationCustomFile File
     */
    fun backupLocationCustomFile(backupLocationCustomFile: File): RoomBackupManajer {
        this.backupLocationCustomFile = backupLocationCustomFile
        return this
    }

    /**
     * Set file encryption to true / false
     * can be used for backup and restore
     *
     *
     * @param backupIsEncrypted Boolean, default = false
     */
    fun backupIsEncrypted(backupIsEncrypted: Boolean): RoomBackupManajer {
        this.backupIsEncrypted = backupIsEncrypted
        return this
    }

    /**
     * Set max backup files count
     * if fileCount is > maxFileCount the oldest backup file will be deleted
     * is for both internal and external storage
     *
     *
     * @param maxFileCount Int, default = null
     */
    fun maxFileCount(maxFileCount: Int): RoomBackupManajer {
        this.maxFileCount = maxFileCount
        return this
    }


    /**
     * Init vars, and return true if no error occurred
     */
    private fun initRoomBackupTwo(): Boolean {
        if (roomDatabase == null) {
            if (enableLogDebug) Log.d(TAG, "roomDatabase is missing")
            onCompleteListener?.onComplete(
                false,
                "roomDatabase is missing",
                OnCompleteListener.EXIT_CODE_ERROR_ROOM_DATABASE_MISSING
            )
            //       throw IllegalArgumentException("roomDatabase is not initialized")
            return false
        }


        if (backupLocation !in listOf(
                BACKUP_FILE_LOCATION_INTERNAL,
                BACKUP_FILE_LOCATION_EXTERNAL,
                BACKUP_FILE_LOCATION_CUSTOM_FILE
            )
        ) {
            if (enableLogDebug) Log.d(TAG, "backupLocation is missing")
            onCompleteListener?.onComplete(
                false,
                "backupLocation is missing",
                OnCompleteListener.EXIT_CODE_ERROR_BACKUP_LOCATION_MISSING
            )
            return false
        }

        if (backupLocation == BACKUP_FILE_LOCATION_CUSTOM_FILE && backupLocationCustomFile == null) {
            if (enableLogDebug) Log.d(
                TAG,
                "backupLocation is set to custom backup file, but no file is defined"
            )
            onCompleteListener?.onComplete(
                false,
                "backupLocation is set to custom backup file, but no file is defined",
                OnCompleteListener.EXIT_CODE_ERROR_BACKUP_LOCATION_FILE_MISSING
            )
            return false
        }



        dbName = roomDatabase!!.openHelper.databaseName!!
        INTERNAL_BACKUP_PATH = File("${context.filesDir}/databasebackup/")
        TEMP_BACKUP_PATH = File("${context.filesDir}/databasebackup-temp/")
        TEMP_BACKUP_FILE = File("$TEMP_BACKUP_PATH/tempbackup.sqlite3")
        EXTERNAL_BACKUP_PATH = File(context.getExternalFilesDir("backup")!!.toURI())
        DATABASE_FILE = File(context.getDatabasePath(dbName).toURI())

        //Create internal and temp backup directory if does not exist
        try {
            INTERNAL_BACKUP_PATH.mkdirs()
            TEMP_BACKUP_PATH.mkdirs()
        } catch (e: FileAlreadyExistsException) {
        } catch (e: IOException) {
        }

        if (enableLogDebug) {
            Log.d(TAG, "DatabaseName: $dbName")
            Log.d(TAG, "Database Location: $DATABASE_FILE")
            Log.d(TAG, "INTERNAL_BACKUP_PATH: $INTERNAL_BACKUP_PATH")
            Log.d(TAG, "EXTERNAL_BACKUP_PATH: $EXTERNAL_BACKUP_PATH")
            if (backupLocationCustomFile != null) Log.d(
                TAG,
                "backupLocationCustomFile: $backupLocationCustomFile"
            )
        }
        return true

    }

    /**
     * restart App with custom Intent
     */
    private fun restartApp() {
        restartIntent!!.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(restartIntent)
        if (context is Activity) {
            (context as Activity).finish()
        }
        Runtime.getRuntime().exit(0)
    }

    /**
     * Start Backup process, and set onComplete Listener to success, if no error occurred, else onComplete Listener success is false
     * and error message is passed
     *
     * if custom storage ist selected, the [openBackupfileCreator] will be launched
     */
    fun backup() {
        if (enableLogDebug) Log.d(TAG, "Starting Backup ...")
        val success = initRoomBackupTwo()
        if (!success) return

        //Needed for storage permissions request
        currentProcess = PROCESS_BACKUP

        //Create name for backup file, if no custom name is set: Database name + currentTime + .sqlite3
        var filename =
            if (customBackupFileName == null) "$dbName-${getTime()}.sqlite3" else customBackupFileName as String
        //Add .aes extension to filename, if file is encrypted
        if (backupIsEncrypted) filename += ".aes"
        if (enableLogDebug) Log.d(TAG, "backupFilename: $filename")

        when (backupLocation) {
            BACKUP_FILE_LOCATION_INTERNAL -> {
                val backupFile = File("$INTERNAL_BACKUP_PATH/$filename")
                doBackup(backupFile)
            }

            BACKUP_FILE_LOCATION_EXTERNAL -> {
                val backupFile = File("$EXTERNAL_BACKUP_PATH/$filename")
                doBackup(backupFile)
            }


            BACKUP_FILE_LOCATION_CUSTOM_FILE -> {
                doBackup(backupLocationCustomFile!!)
            }

            else -> return
        }
    }

    /**
     * This method will do the backup action
     *
     * @param destination File
     */
    private fun doBackup(destination: File) {
        //Close the database
        roomDatabase!!.close()
        roomDatabase = null
        if (backupIsEncrypted) {
            val encryptedBytes = encryptBackup() ?: return
            val bos = BufferedOutputStream(FileOutputStream(destination, false))
            bos.write(encryptedBytes)
            bos.flush()
            bos.close()
        } else {
            //Copy current database to save location (/files dir)
            copy(DATABASE_FILE, destination)
        }

        //If maxFileCount is set and is reached, delete oldest file
        if (maxFileCount != null) {
            val deleted = deleteOldBackup()
            if (!deleted) return
        }

        if (enableLogDebug) Log.d(
            TAG,
            "Backup done, encrypted($backupIsEncrypted) and saved to $destination"
        )
        onCompleteListener?.onComplete(true, "success", OnCompleteListener.EXIT_CODE_SUCCESS)
    }


    /**
     * Encrypts the current Database and return it's content as ByteArray.
     * The original Database is not encrypted only a current copy of this database
     *
     * @return encrypted backup as ByteArray
     */
    private fun encryptBackup(): ByteArray? {
        try {
            //Copy database you want to backup to temp directory
            copy(DATABASE_FILE, TEMP_BACKUP_FILE)


            //encrypt temp file, and save it to backup location
            val encryptDecryptBackup = AESEncryptionHelper()
            val fileData = encryptDecryptBackup.readFile(TEMP_BACKUP_FILE)

            val aesEncryptionManager = AESEncryptionManager()
            val encryptedBytes =
                aesEncryptionManager.encryptData(sharedPreferences, encryptPassword, fileData)

            //Delete temp file
            TEMP_BACKUP_FILE.delete()

            return encryptedBytes

        } catch (e: Exception) {
            if (enableLogDebug) Log.d(TAG, "error during encryption: ${e.message}")
            onCompleteListener?.onComplete(
                false,
                "error during encryption",
                OnCompleteListener.EXIT_CODE_ERROR_ENCRYPTION_ERROR
            )
            return null
        }
    }

    /**
     * Start Restore process, and set onComplete Listener to success, if no error occurred, else onComplete Listener success is false and error message is passed
     *
     * if internal or external storage is selected, this function shows a list of all available backup files in a MaterialAlertDialog and
     * calls [restoreSelectedInternalExternalFile] to restore selected file
     *
     * if custom storage ist selected, the [openBackupfileChooser] will be launched
     */
    fun restore() {
        if (enableLogDebug) Log.d(TAG, "Starting Restore ...")
        val success = initRoomBackupTwo()
        if (!success) return

        //Needed for storage permissions request
        currentProcess = PROCESS_RESTORE

        //Path of Backup Directory
        val backupDirectory: File

        when (backupLocation) {
            BACKUP_FILE_LOCATION_INTERNAL -> {
                backupDirectory = INTERNAL_BACKUP_PATH
            }

            BACKUP_FILE_LOCATION_EXTERNAL -> {
                backupDirectory = File("$EXTERNAL_BACKUP_PATH/")
            }


            BACKUP_FILE_LOCATION_CUSTOM_FILE -> {
                Log.d(
                    TAG,
                    "backupLocationCustomFile!!.exists()? : ${backupLocationCustomFile!!.exists()}"
                )
                doRestore(backupLocationCustomFile!!)
                return
            }

            else -> return
        }

        //All Files in an Array of type File
        val arrayOfFiles = backupDirectory.listFiles()

        //If array is null or empty show "error" and return
        if (arrayOfFiles.isNullOrEmpty()) {
            if (enableLogDebug) Log.d(TAG, "No backups available to restore")
            onCompleteListener?.onComplete(
                false,
                "No backups available",
                OnCompleteListener.EXIT_CODE_ERROR_RESTORE_NO_BACKUPS_AVAILABLE
            )
            Toast.makeText(context, "No backups available to restore", Toast.LENGTH_SHORT).show()
            return
        }

        //Sort Array: lastModified
        Arrays.sort(arrayOfFiles, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR)

        //New empty MutableList of String
        val mutableListOfFilesAsString = mutableListOf<String>()

        //Add each filename to mutablelistOfFilesAsString
        runBlocking {
            for (i in arrayOfFiles.indices) {
                mutableListOfFilesAsString.add(arrayOfFiles[i].name)
            }
        }

        //Convert MutableList to Array
        val filesStringArray = mutableListOfFilesAsString.toTypedArray()

        //Show MaterialAlertDialog, with all available files, and on click Listener
        MaterialAlertDialogBuilder(context)
            .setTitle(customRestoreDialogTitle)
            .setItems(filesStringArray) { _, which ->
                restoreSelectedInternalExternalFile(filesStringArray[which])
            }
            .setOnCancelListener {
                if (enableLogDebug) Log.d(TAG, "Restore dialog canceled")
                onCompleteListener?.onComplete(
                    false,
                    "Restore dialog canceled",
                    OnCompleteListener.EXIT_CODE_ERROR_BY_USER_CANCELED
                )
            }
            .show()
    }

    /**
     * This method will do the restore action
     *
     * @param source File
     */
    private fun doRestore(source: File) {
        //Close the database
        roomDatabase!!.close()
        roomDatabase = null
        val fileExtension = source.extension
        if (backupIsEncrypted) {
            copy(source, TEMP_BACKUP_FILE)
            val decryptedBytes = decryptBackup() ?: return
            val bos = BufferedOutputStream(FileOutputStream(DATABASE_FILE, false))
            bos.write(decryptedBytes)
            bos.flush()
            bos.close()
        } else {
            if (fileExtension == "aes") {
                if (enableLogDebug) Log.d(
                    TAG,
                    "Cannot restore database, it is encrypted. Maybe you forgot to add the property .fileIsEncrypted(true)"
                )
                onCompleteListener?.onComplete(
                    false,
                    "cannot restore database, see Log for more details (if enabled)",
                    OnCompleteListener.EXIT_CODE_ERROR_RESTORE_BACKUP_IS_ENCRYPTED
                )
                return
            }
            //Copy back database and replace current database
            copy(source, DATABASE_FILE)
        }

        if (enableLogDebug) Log.d(
            TAG,
            "Restore done, decrypted($backupIsEncrypted) and restored from $source"
        )
        onCompleteListener?.onComplete(true, "success", OnCompleteListener.EXIT_CODE_SUCCESS)
    }


    /**
     * Restores the selected file from internal or external storage
     *
     * @param filename String
     */
    private fun restoreSelectedInternalExternalFile(filename: String) {
        if (enableLogDebug) Log.d(TAG, "Restore selected file...")

        when (backupLocation) {
            BACKUP_FILE_LOCATION_INTERNAL -> {
                doRestore(File("$INTERNAL_BACKUP_PATH/$filename"))
            }

            BACKUP_FILE_LOCATION_EXTERNAL -> {
                doRestore(File("$EXTERNAL_BACKUP_PATH/$filename"))
            }

            else -> return
        }
    }

    /**
     * Decrypts the [TEMP_BACKUP_FILE] and return it's content as ByteArray.
     * A valid encrypted backup file must be present on [TEMP_BACKUP_FILE]
     *
     * @return decrypted backup as ByteArray
     */
    private fun decryptBackup(): ByteArray? {
        try {
            //Decrypt temp file, and save it to database location
            val encryptDecryptBackup = AESEncryptionHelper()
            val fileData = encryptDecryptBackup.readFile(TEMP_BACKUP_FILE)

            val aesEncryptionManager = AESEncryptionManager()
            val decryptedBytes =
                aesEncryptionManager.decryptData(sharedPreferences, encryptPassword, fileData)

            //Delete tem file
            TEMP_BACKUP_FILE.delete()

            return decryptedBytes

        } catch (e: BadPaddingException) {
            if (enableLogDebug) Log.d(TAG, "error during decryption (wrong password): ${e.message}")
            onCompleteListener?.onComplete(
                false,
                "error during decryption (wrong password) see Log for more details (if enabled)",
                OnCompleteListener.EXIT_CODE_ERROR_WRONG_DECRYPTION_PASSWORD
            )
            return null
        } catch (e: Exception) {
            if (enableLogDebug) Log.d(TAG, "error during decryption: ${e.message}")
            onCompleteListener?.onComplete(
                false,
                "error during decryption see Log for more details (if enabled)",
                OnCompleteListener.EXIT_CODE_ERROR_DECRYPTION_ERROR
            )
            return null
        }
    }


    /**
     * @return current time formatted as String
     */
    private fun getTime(): String {

        val currentTime = Calendar.getInstance().time

        val sdf = if (Build.VERSION.SDK_INT <= 28) {
            SimpleDateFormat("yyyy-MM-dd-HH_mm_ss", Locale.getDefault())
        } else {
            SimpleDateFormat("yyyy-MM-dd-HH:mm:ss", Locale.getDefault())
        }

        return sdf.format(currentTime)

    }

    /**
     * If maxFileCount is set, and reached, all old files will be deleted. Only if [BACKUP_FILE_LOCATION_INTERNAL] or [BACKUP_FILE_LOCATION_EXTERNAL]
     *
     * @return true if old files deleted or nothing to do
     */
    private fun deleteOldBackup(): Boolean {
        //Path of Backup Directory

        val backupDirectory: File = when (backupLocation) {
            BACKUP_FILE_LOCATION_INTERNAL -> {
                INTERNAL_BACKUP_PATH
            }

            BACKUP_FILE_LOCATION_EXTERNAL -> {
                File("$EXTERNAL_BACKUP_PATH/")
            }

            else -> return true
        }

        //All Files in an Array of type File
        val arrayOfFiles = backupDirectory.listFiles()

        //If array is null or empty nothing to do and return
        if (arrayOfFiles.isNullOrEmpty()) {
            if (enableLogDebug) Log.d(TAG, "")
            onCompleteListener?.onComplete(
                false,
                "maxFileCount: Failed to get list of backups",
                OnCompleteListener.EXIT_CODE_ERROR
            )
            return false
        } else if (arrayOfFiles.size > maxFileCount!!) {
            //Sort Array: lastModified
            Arrays.sort(arrayOfFiles, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR)

            //Get count of files to delete
            val fileCountToDelete = arrayOfFiles.size - maxFileCount!!

            for (i in 1..fileCountToDelete) {
                //Delete all old files (i-1 because array starts a 0)
                arrayOfFiles[i - 1].delete()

                if (enableLogDebug) Log.d(
                    TAG,
                    "maxFileCount reached: ${arrayOfFiles[i - 1]} deleted"
                )
            }
        }
        return true
    }


}