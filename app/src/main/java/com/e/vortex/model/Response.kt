package com.e.vortex.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Response(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val link: String,
    val home: String
) : Serializable {
    override fun toString(): String {
        return "Response(id=$id, link='$link', home='$home')"
    }
}