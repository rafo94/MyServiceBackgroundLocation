package com.rafo.myservicelocation.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Created by Rafik Gasparyan on 07/20/22
 */

@Entity(tableName = "location")
data class LocationEntity(
    @PrimaryKey var id: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "location_list")
    var locationList: List<Location>,
    var date: Date = Date()
)