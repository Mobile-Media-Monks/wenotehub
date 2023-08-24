package com.example.wenotehub.core

import androidx.activity.result.contract.ActivityResultContracts
import com.example.wenotehub.MainActivity

class PermissionRequest(
    activity: MainActivity,
    private val permissions: List<String>,
    onRationale: () -> Unit = {},
    onDenied: () -> Unit = {}
) {

    private var onGranted: () -> Unit = {}

    private val permissionLauncher =
        activity.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissionsMap ->
            val allGranted = permissionsMap.all { it.value }
            when {
                allGranted -> onGranted()
                permissions.any { activity.shouldShowRequestPermissionRationale(it) } -> onRationale()
                else -> onDenied()
            }
        }

    fun runWithPermission(body: () -> Unit) {
        onGranted = body
        permissionLauncher.launch(permissions.toTypedArray())
    }
}