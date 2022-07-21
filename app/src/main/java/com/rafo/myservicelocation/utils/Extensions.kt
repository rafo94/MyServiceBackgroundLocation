package com.rafo.myservicelocation.utils

import android.app.ActivityManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.view.View
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Rafik Gasparyan on 07/20/22
 */

fun Context.startMyService(intent: Intent) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        startForegroundService(intent)
    } else {
        startService(intent)
    }
}

fun Date.dateToString(format: String): String {
    val dateFormatter = SimpleDateFormat(format, Locale.getDefault())
    return dateFormatter.format(this)
}

fun Context.isServiceRunning(serviceClass: Class<*>): Boolean {
    val manager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
    for (service in manager.getRunningServices(Int.MAX_VALUE)) {
        if (serviceClass.name.equals(service.service.className)) {
            return true
        }
    }
    return false
}

internal fun View.longSnackBar(message: String, action: (Snackbar.() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let { snackbar.it() }
    snackbar.show()
}

internal fun Snackbar.action(message: String, action: (View) -> Unit) {
    this.setAction(message, action)
}

fun Context.alert(dialogBuilder: MaterialAlertDialogBuilder.() -> Unit) {
    MaterialAlertDialogBuilder(this).apply {
        setCancelable(false)
        dialogBuilder()
        create()
        show()
    }
}

fun MaterialAlertDialogBuilder.negativeButton(
    text: String = "No",
    handleClick: (dialogInterface: DialogInterface) -> Unit = { it.dismiss() }
) {
    this.setNegativeButton(text) { dialogInterface, _ -> handleClick(dialogInterface) }
}

fun MaterialAlertDialogBuilder.positiveButton(
    text: String = "Yes",
    handleClick: (dialogInterface: DialogInterface) -> Unit = { it.dismiss() }
) {
    this.setPositiveButton(text) { dialogInterface, _ -> handleClick(dialogInterface) }
}