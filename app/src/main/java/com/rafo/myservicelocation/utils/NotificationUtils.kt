package com.rafo.myservicelocation.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import com.rafo.myservicelocation.R

/**
 * Created by Rafik Gasparyan on 07/20/22
 */

@RequiresApi(Build.VERSION_CODES.O)
fun Context.createNotifyChannel(): String {
    val id = getString(R.string.channel_id)
    val name = getString(R.string.channel_name)
    val chan = NotificationChannel(id, name, NotificationManager.IMPORTANCE_NONE)
    chan.lightColor = Color.BLUE
    chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
    val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    service.createNotificationChannel(chan)
    return id
}