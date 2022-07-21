package com.rafo.myservicelocation.ui.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.rafo.myservicelocation.data.LocationRepository
import java.util.concurrent.Executors

/**
 * Created by Rafik Gasparyan on 07/20/22
 */
class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val locationRepo = LocationRepository.getInstance(
        context = application.applicationContext,
        executor = Executors.newSingleThreadExecutor()
    )

    val getAllData = locationRepo.getLocationList()
}