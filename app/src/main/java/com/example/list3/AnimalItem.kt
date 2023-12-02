package com.example.list3

import androidx.fragment.app.FragmentActivity
import java.io.Serializable
import kotlin.random.Random

class AnimalItem(
    var name: String,
    var latinName: String,
    var animalType: AnimalType,
    var health: Int,
    var strength: Int,
    var isDeadly: Boolean
): Serializable {

    enum class AnimalType {
        BIRD, INSECT, RODENT, PREDATOR
    }

    companion object {
        @JvmStatic
        fun getAnimalIconId(animalItem: AnimalItem) : Int {
            return when (animalItem.animalType) {
                AnimalItem.AnimalType.BIRD -> R.drawable.bird_icon
                AnimalItem.AnimalType.PREDATOR -> R.drawable.predator_icon
                AnimalItem.AnimalType.INSECT -> R.drawable.insect_icon
                AnimalItem.AnimalType.RODENT -> R.drawable.rodent_icon
            }
        }
    }


}