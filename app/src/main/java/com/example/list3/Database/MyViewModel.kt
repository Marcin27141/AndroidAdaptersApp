package com.example.list3.Database

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras

class MyViewModel(context: Context) : ViewModel() {
    private var myRepo = MyRepository(context)
    fun getAllItems() : MutableList<DBItem> = myRepo.getAnimals()!!
    fun insertItem(item: DBItem) = myRepo.addAnimal(item)
    fun deleteItem(item: DBItem) = myRepo.deleteAnimal(item)
    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return MyViewModel(application.applicationContext) as T
            }
        }
    }
}