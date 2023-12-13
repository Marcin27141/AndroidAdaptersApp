package com.example.list3.Database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.flow.Flow

class MyViewModel(context: Context) : ViewModel() {
    private var myRepo = MyRepository(context)
    fun getAllItems() : MutableList<DBItem> = myRepo.getAnimals()!!
    fun getAllItemsLive() : LiveData<MutableList<DBItem>> = myRepo.getAnimalsLive()

    fun getAllItemsFlow() : Flow<MutableList<DBItem>> = myRepo.getAnimalsFlow()

    fun addItem(item: DBItem) = myRepo.addAnimal(item)
    fun deleteItem(item: DBItem) = myRepo.deleteAnimal(item)
    fun getAnimalById(animalItemId: Int): DBItem? = myRepo.getAnimalById(animalItemId)
    fun updateAnimal(id: Int, animal: DBItem) = myRepo.updateAnimal(id, animal)

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return MyViewModel(application.applicationContext) as T
            }
        }
    }
}