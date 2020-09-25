package com.e.vortex.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.e.vortex.model.Response

@Database(entities = [Response::class], version = 1)
abstract class VortexDatabase : RoomDatabase() {

    abstract fun vortexDao(): VortexDao
}