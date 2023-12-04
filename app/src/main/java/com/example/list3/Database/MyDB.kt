package com.example.list3.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [DBItem::class], version = 1)
abstract class MyDB: RoomDatabase() {
    abstract fun myDao(): MyDao?
    companion object {
        private var DB_INSTANCE: MyDB? = null
        @Synchronized
        open fun getDatabase(context: Context) : MyDB? {
            if (DB_INSTANCE == null) {
                DB_INSTANCE = databaseBuilder(
                    context.applicationContext,
                    MyDB::class.java,
                    "animals_database")
                    .allowMainThreadQueries()
                    .build()
            }
            return DB_INSTANCE
        }
    }
}