package com.example.list3.Database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.list3.AnimalItem
import java.io.Serializable

@Entity(tableName = "animals")
class DBItem constructor(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var name: String? = null,
    var latinName: String? = null,
    var animalType: AnimalType = AnimalType.BIRD,
    var health: Int = 0,
    var strength: Int = 0,
    var isDeadly: Boolean = false,
){

    enum class AnimalType {
        BIRD, INSECT, RODENT, PREDATOR
    }
}