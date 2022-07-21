package com.rafo.myservicelocation.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import java.util.*

/**
 * Created by Rafik Gasparyan on 07/20/22
 */
@Dao
interface LocationDao {
    @Query("SELECT * FROM location")
    fun getLocations(): LiveData<List<LocationEntity>>

    @Query("SELECT * FROM location WHERE ID=(:id)")
    fun getLocation(id: UUID): LiveData<LocationEntity>

    @Insert
    fun addLocation(locationEntity: LocationEntity)

    @Insert
    fun addLocationList(list: List<LocationEntity>)
}