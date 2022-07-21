package com.rafo.myservicelocation.services

import android.annotation.SuppressLint
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.rafo.myservicelocation.R
import com.rafo.myservicelocation.data.LocationRepository
import com.rafo.myservicelocation.data.db.Location
import com.rafo.myservicelocation.data.db.LocationEntity
import com.rafo.myservicelocation.ui.MainActivity
import com.rafo.myservicelocation.utils.createNotifyChannel
import java.util.*
import java.util.concurrent.Executors

/**
 * Created by Rafik Gasparyan on 07/20/22
 */

class LocationService : Service() {

    private val locationList = mutableListOf<Location>()

    private val baseListener = LocationListener { location ->
        locationList.add(
            Location(
                latitude = location.latitude,
                longitude = location.longitude,
                date = Date(location.time)
            )
        )
    }
    private var locationManager: LocationManager? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        startNotifyForeground()
    }

    private fun startNotifyForeground() {
        val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotifyChannel()
        } else {
            getString(R.string.channel_id)
        }

        val resultIntent = Intent(this, MainActivity::class.java)
        val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(resultIntent)
            getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }

        NotificationCompat.Builder(this, channelId).apply {
            setOngoing(true)
            setContentTitle(getString(R.string.location))
            setContentText(getString(R.string.start_tracking))
            setSmallIcon(R.drawable.ic_location_notify)
            setContentIntent(resultPendingIntent)
            priority = NotificationCompat.PRIORITY_MIN
            setCategory(Notification.CATEGORY_SERVICE)
        }.also { startForeground(101, it.build()) }
    }

    @SuppressLint("MissingPermission")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager?.let { manager ->
            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    10000L,
                    0.0f,
                    baseListener
                )
            }
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        if (locationList.isNotEmpty()) {
            val locationEntity = LocationEntity(
                locationList = locationList,
                date = Date()
            )
            LocationRepository.getInstance(
                context = applicationContext,
                executor = Executors.newSingleThreadExecutor()
            ).setLocation(locationEntity)
        }
        locationManager?.removeUpdates(baseListener)
    }
}