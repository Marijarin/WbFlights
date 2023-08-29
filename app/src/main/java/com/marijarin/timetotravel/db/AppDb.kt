package com.marijarin.timetotravel.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.marijarin.timetotravel.dao.FlightDao
import com.marijarin.timetotravel.entity.FlightEntity

@Database(entities = [FlightEntity::class], version = 1, exportSchema = false)

abstract class AppDb: RoomDatabase() {
    abstract fun flightDao(): FlightDao
}