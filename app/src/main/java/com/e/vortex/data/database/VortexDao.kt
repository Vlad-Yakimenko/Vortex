package com.e.vortex.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.e.vortex.model.Response

@Dao
interface VortexDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertResponse(response: Response)

    @Query("SELECT * FROM response WHERE id = 1")
    fun getResponse(): LiveData<Response>
}