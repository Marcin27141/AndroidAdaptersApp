package com.example.list3

import kotlin.random.Random

class AnimalItem(
    var name: String,
    var latinName: String,
    var animalType: AnimalType,
    var health: Int,
    var strength: Int,
    var isDeadly: Boolean
) {

    enum class AnimalType {
        BIRD, INSECT, RODENT, PREDATOR
    }
}