package com.example.list3

import kotlin.random.Random

class DataItem {
    var text_main: String = "Default text"
    var text2: String = "Default text"
    var item_value: Int = Random.nextInt(0, 5)
    var item_value2: Int = 0
    var item_type: Boolean = Random.nextBoolean()
    var item_checked: Boolean = Random.nextBoolean()
    constructor()
    constructor(num: Int) : this() {
        text_main = "Item name " + num.toString()
        text2 = "Item value = " + item_value.toString()
    }
}