package com.rafo.myservicelocation.data

import android.content.Context
import androidx.lifecycle.LiveData
import com.rafo.myservicelocation.data.db.LocationDatabase
import com.rafo.myservicelocation.data.db.LocationEntity
import java.util.*
import java.util.concurrent.ExecutorService

/**
 * Created by Rafik Gasparyan on 07/20/22
 */
class LocationRepository private constructor(
    private val database: LocationDatabase,
    private val executor: ExecutorService
) {

    /**
     * Database related methods
     * */
    private val locationDao = database.locationDao()


    /**
     * Return location list
     * */

    fun getLocationList(): LiveData<List<LocationEntity>> = locationDao.getLocations()


    /**
     * Return location by id
     * */

    fun getLocation(id: UUID): LiveData<LocationEntity> = locationDao.getLocation(id)


    /**
     * Insert location list
     * */
    fun setLocationList(locationList: List<LocationEntity>) {
        executor.execute { locationDao.addLocationList(locationList) }
    }


    /**
     * Insert location
     * */
    fun setLocation(location: LocationEntity) {
        executor.execute { locationDao.addLocation(location) }
    }

    companion object {
        @Volatile
        private var INSTANCE: LocationRepository? = null

        fun getInstance(context: Context, executor: ExecutorService): LocationRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: LocationRepository(
                    database = LocationDatabase.getDatabase(context),
                    executor = executor
                ).also { INSTANCE = it }
            }
    }
}