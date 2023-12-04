package com.example.list3.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MyDao {
    @Query("SELECT * FROM animals ORDER BY id ASC")
    fun getAllAnimals(): MutableList<DBItem>

    @Query("DELETE FROM animals")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(animal: DBItem): Long

    @Delete
    fun delete(animal: DBItem) : Int
}