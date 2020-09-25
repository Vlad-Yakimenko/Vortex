package com.e.vortex

import android.app.Application
import androidx.room.Room
import com.e.vortex.data.database.VortexDatabase

class App : Application() {

    lateinit var database: VortexDatabase

    override fun onCreate() {
        super.onCreate()

        database = Room
            .databaseBuilder(applicationContext, VortexDatabase::class.java, "vortex_database")
            .fallbackToDestructiveMigration()
            .build()
    }
}