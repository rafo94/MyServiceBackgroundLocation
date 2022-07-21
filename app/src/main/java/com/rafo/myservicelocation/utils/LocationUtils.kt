package com.rafo.myservicelocation.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import com.google.android.gms.maps.model.LatLng

/**
 * Created by Rafik Gasparyan on 07/20/22
 */

fun Context.locationEnabled() : Boolean {
    val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
}

fun Context.checkLocationPermission(): Boolean {
    val permission = Manifest.permission.ACCESS_FINE_LOCATION
    val res: Int = checkCallingOrSelfPermission(permission)
    return res == PackageManager.PERMISSION_GRANTED
}

fun midPoint(lat1: Double, long1: Double, lat2: Double, long2: Double): LatLng {
    return LatLng((lat1 + lat2) / 2, (long1 + long2) / 2)
}