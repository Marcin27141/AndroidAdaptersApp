package com.example.list3.Database

import android.content.Context
import com.example.list3.AnimalItem

class MyRepository(context: Context) {
    private var dataList: MutableList<DBItem>? = null
    private var myDao: MyDao
    private var db: MyDB

    fun getAnimals() : MutableList<DBItem>? {
        dataList = myDao.getAllAnimals()
        return dataList
    }

    fun addAnimal(animal: DBItem) : Boolean {
        return myDao.insert(animal)>= 0
    }

    fun deleteAnimal(animal: DBItem) : Boolean {
        return myDao.delete(animal) > 0
    }


    companion object {
        private var R_INSTANCE: MyRepository? = null
        fun getInstance(context: Context): MyRepository {
            if (R_INSTANCE == null) {
                R_INSTANCE = MyRepository(context)
            }
            return R_INSTANCE as MyRepository
        }
    }

    init {
        db = MyDB.getDatabase(context)!!
        myDao = db.myDao()!!

        val initialAnimals = listOf(
            DBItem(1, "Trumpeter swan", "Cygnus buccinator", DBItem.AnimalType.BIRD, 5, 3, false),
            DBItem(2, "Tiger mosquito", "Aedes albopictus", DBItem.AnimalType.INSECT, 1, 5, true),
            DBItem(3, "Eurasian beaver", "Castor fiber", DBItem.AnimalType.RODENT, 3, 2, false),
            DBItem(4, "Shoebill", "Balaeniceps rex", DBItem.AnimalType.BIRD, 4, 2, false),
            DBItem(5, "Housefly ", "Musca domestica", DBItem.AnimalType.INSECT, 2, 2, false),
            DBItem(6, "Snow leopard", "Panthera uncia", DBItem.AnimalType.PREDATOR, 4, 5, true),
            DBItem(7, "Silverfish", "Lepisma saccharinum", DBItem.AnimalType.INSECT, 3, 1, false),
            DBItem(8, "Tasmanian devil", "Sarcophilus harrisii", DBItem.AnimalType.PREDATOR, 3, 2, false),
            DBItem(9, "Common Vole", "Microtus arvalis", DBItem.AnimalType.RODENT, 2, 2, false),
            DBItem(10, "Bonelli's eagle", "Aquila fasciata", DBItem.AnimalType.BIRD, 4, 4, true),
            DBItem(11, "Lion", "Panthera leo", DBItem.AnimalType.PREDATOR, 4, 5, true),
            DBItem(12, "European mantis", "Mantis religiosa", DBItem.AnimalType.INSECT, 3, 3, false),
            DBItem(13, "Black Widow Spider", "Latrodectus mactans", DBItem.AnimalType.INSECT, 2, 5, true),
            DBItem(14, "Great Horned Owl", "Bubo virginianus", DBItem.AnimalType.BIRD, 3, 4, true)
        )

        myDao.deleteAll()
        for (animal: DBItem in initialAnimals) {
            myDao.insert(animal)
        }
//        addAnimal(DBItem("Housefly ", "Musca domestica", DBItem.AnimalType.INSECT, 2, 2, false))
    }
}