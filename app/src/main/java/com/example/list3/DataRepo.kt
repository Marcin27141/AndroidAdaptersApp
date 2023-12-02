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