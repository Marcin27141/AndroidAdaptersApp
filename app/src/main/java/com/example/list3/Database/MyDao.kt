package com.example.list3.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MyDao {
    @Query("SELECT * FROM animals ORDER BY id ASC")
    fun getAllAnimals(): MutableList<DBItem>

    @Query("SELECT * FROM animals ORDER BY id ASC")
    fun getAllAnimalsLive(): LiveData<MutableList<DBItem>>

    @Query("SELECT * FROM animals ORDER BY id ASC")
    fun getAllAnimalsFlow(): Flow<MutableList<DBItem>>


    @Query("SELECT * FROM animals WHERE id = :id")
    fun getAnimalById(id: Int): DBItem

    @Query("DELETE FROM animals")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(animal: DBItem): Long

    @Delete
    fun delete(animal: DBItem) : Int

    @Update
    suspend fun updateAnimal(animal: DBItem)
}