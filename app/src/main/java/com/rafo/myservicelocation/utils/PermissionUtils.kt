package com.rafo.myservicelocation.utils

import android.Manifest
import android.app.Activity
import android.os.Build
import androidx.core.app.ActivityCompat

/**
 * Created by Rafik Gasparyan on 07/20/22
 */

private const val PERMISSION_REQUEST_CODE = 34

fun requestPermission(activity: Activity) {
    ActivityCompat.requestPermissions(
        activity,
        arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION),
        PERMISSION_REQUEST_CODE
    )
}

fun shouldShowRationale(activity: Activity): Boolean {
    return ActivityCompat.shouldShowRequestPermissionRationale(
        activity, Manifest.permission.ACCESS_BACKGROUND_LOCATION
    )
}

fun isBackgroundLocationPermissionGranted(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
}