package com.example.nisfu.recyclerview.database

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE

@Dao

interface PersonDao {

    @Query("SELECT * FROM People")
    fun getAll(): MutableList<People>

    @Insert
    fun insert(people: People)

    @Update(onConflict = REPLACE)
    fun update(people: People)

    @Delete
    fun delete(people: People)


}