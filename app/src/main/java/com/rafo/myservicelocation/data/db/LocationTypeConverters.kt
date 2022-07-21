package com.rafo.myservicelocation.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import java.util.*

/**
 * Created by Rafik Gasparyan on 07/20/22
 */
class LocationTypeConverters {

    @TypeConverter
    fun fromDate(date: Date?): Long? = date?.time

    @TypeConverter
    fun toDate(millisecond: Long?): Date? = millisecond?.let { Date(it) }

    @TypeConverter
    fun fromUUID(id: UUID?): String? = id?.toString()

    @TypeConverter
    fun toUUID(uuid: String?): UUID? = UUID.fromString(uuid)

    @TypeConverter
    fun listToJsonString(value: List<Location>): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToList(value: String) =
        Gson().fromJson(value, Array<Location>::class.java).toList()
}