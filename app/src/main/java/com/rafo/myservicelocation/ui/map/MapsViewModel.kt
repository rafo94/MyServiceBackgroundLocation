package com.rafo.myservicelocation.ui.map

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rafo.myservicelocation.data.LocationRepository
import com.rafo.myservicelocation.data.db.LocationEntity
import java.util.*
import java.util.concurrent.Executors

/**
 * Created by Rafik Gasparyan on 07/20/22
 */
class MapsViewModel(application: Application) : AndroidViewModel(application) {

    private val locationRepository = LocationRepository.getInstance(
        context = application.applicationContext,
        executor = Executors.newSingleThreadExecutor()
    )

    var currentLocation: LiveData<LocationEntity> = MutableLiveData()

    fun getLocationList(id: UUID) {
        currentLocation = locationRepository.getLocation(id)
    }
}