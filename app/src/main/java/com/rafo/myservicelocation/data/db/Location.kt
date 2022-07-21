package com.rafo.myservicelocation.data.db

import androidx.room.PrimaryKey
import java.util.*

/**
 * Created by Rafik Gasparyan on 07/20/22
 */
data class Location(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val date: Date
)
