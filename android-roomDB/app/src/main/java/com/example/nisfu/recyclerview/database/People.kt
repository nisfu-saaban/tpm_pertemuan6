package com.example.nisfu.recyclerview.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class People(var name: String, var address: String, var number: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
        get() = field
        set(value) {
            field = value
        }


}