package com.example.list3

class DataRepo {
    val LIST_SIZE = 15
    val item_text_list = Array(LIST_SIZE) { i -> "Item name " + i }
    private var complexDataList: MutableList<DataItem>

    companion object {
        private var INSTANCE: DataRepo? = null
        fun getInstance(): DataRepo {
            if (INSTANCE == null)
                INSTANCE = DataRepo()
            return INSTANCE as DataRepo
        }
    }

    fun getAnimalsData() : MutableList<AnimalItem> {
        return listOf(
            AnimalItem("Trumpeter swan", "Cygnus buccinator", AnimalItem.AnimalType.BIRD, 5, 3, false),
            AnimalItem("Tiger mosquito", "Aedes albopictus", AnimalItem.AnimalType.INSECT, 1, 5, true),
            AnimalItem("Eurasian beaver", "Castor fiber", AnimalItem.AnimalType.RODENT, 3, 2, false),
            AnimalItem("Shoebill", "Balaeniceps rex", AnimalItem.AnimalType.BIRD, 4, 2, false),
            AnimalItem("Housefly ", "Musca domestica", AnimalItem.AnimalType.INSECT, 2, 2, false),
            AnimalItem("Snow leopard", "Panthera uncia", AnimalItem.AnimalType.PREDATOR, 4, 5, true),
            AnimalItem("Silverfish", "Lepisma saccharinum", AnimalItem.AnimalType.INSECT, 3, 1, false),
            AnimalItem("Tasmanian devil", "Sarcophilus harrisii", AnimalItem.AnimalType.PREDATOR, 3, 2, false),
            AnimalItem("Common Vole", "Microtus arvalis", AnimalItem.AnimalType.RODENT, 2, 2, false),
            AnimalItem("Bonelli's eagle", "Aquila fasciata", AnimalItem.AnimalType.BIRD, 4, 4, true),
            AnimalItem("Lion", "Panthera leo", AnimalItem.AnimalType.PREDATOR, 4, 5, true),
            AnimalItem("European mantis", "Mantis religiosa", AnimalItem.AnimalType.INSECT, 3, 3, false),
            AnimalItem("Black Widow Spider", "Latrodectus mactans", AnimalItem.AnimalType.INSECT, 2, 5, true),
            AnimalItem("Ferret", "Mustela furo", AnimalItem.AnimalType.RODENT, 2, 1, false),
            AnimalItem("Great Horned Owl", "Bubo virginianus", AnimalItem.AnimalType.BIRD, 3, 4, true)
        ).toMutableList();
    }

    fun getComplexData() : MutableList<DataItem> {
        return complexDataList
    }

    fun addComplexItem(item: DataItem) {
        complexDataList.add(item)
    }

    fun deleteComplexItem(position: Int) : Boolean {
        return try {
            complexDataList.removeAt(position)
            true
        } catch (e: IndexOutOfBoundsException) {
            false
        }
    }

    init {
        complexDataList = MutableList(LIST_SIZE) { i -> DataItem(i) }
    }
}