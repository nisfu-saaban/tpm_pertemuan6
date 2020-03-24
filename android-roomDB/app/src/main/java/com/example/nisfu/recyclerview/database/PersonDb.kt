package com.example.nisfu.recyclerview.database

import android.annotation.SuppressLint
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.os.AsyncTask
import android.util.Log

@Database(entities = arrayOf(People::class), version = 1)
abstract class PersonDb : RoomDatabase() {
    abstract fun personDao(): PersonDao

    companion object {
        var INSTANCE: PersonDb? = null

        fun getInstance(context: Context): PersonDb {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                        PersonDb::class.java,
                        "People.db").build()
            }

            return INSTANCE as PersonDb
        }

        @SuppressLint("StaticFieldLeak")
        fun insertData(personDb: PersonDb, people: People) {

            object : AsyncTask<Void, Void, Void>() {
                override fun doInBackground(vararg params: Void?): Void? {
                    personDb.personDao().insert(people)
                    return null
                }
            }.execute()
        }

        @SuppressLint("StaticFieldLeak")
        fun getAllData(personDb: PersonDb): MutableList<People> {
            lateinit var lists: MutableList<People>

            return object : AsyncTask<Void, Void, MutableList<People>>() {
                override fun doInBackground(vararg params: Void?): MutableList<People>? {
                    lists = personDb.personDao().getAll()
                    return lists
                }
            }.execute().get()
        }

        @SuppressLint("StaticFieldLeak")
        fun updateData(personDb: PersonDb, people: People){
            object : AsyncTask<Void, Void, Void>() {
                override fun doInBackground(vararg params: Void?): Void? {
                    personDb.personDao().update(people)
                    Log.d("HHHHHH", "update")
                    return null
                }

                override fun onPostExecute(result: Void?) {
                    Log.d("HHHHHH", "Sukses update")
                }
            }.execute()
        }

        @SuppressLint("StaticFieldLeak")
        fun deleteData(personDb: PersonDb, people: People) {

            object : AsyncTask<Void, Void, Void>() {
                override fun doInBackground(vararg params: Void?): Void? {
                    personDb.personDao().delete(people)
                    return null
                }
            }.execute()
        }
    }
}