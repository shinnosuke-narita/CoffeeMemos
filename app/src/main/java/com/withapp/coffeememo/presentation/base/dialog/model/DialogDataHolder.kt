package com.withapp.coffeememo.presentation.base.dialog.model


class DialogDataHolder(private var _currentIndex: Int, private var nameList: Array<String>)  {
    val currentIndex: Int
        get() = _currentIndex

    val dialogData: List<DialogData>

    init {
        dialogData = createProcessList()
    }

    private fun createProcessList(): List<DialogData> {
        val result = mutableListOf<DialogData>()

        for ((index, name) in nameList.withIndex()) {
            val isSelected = index == _currentIndex

            result.add(DialogData(name, isSelected))
        }

        return result
    }

    fun updateProcessList(processIndex: Int) {
        _currentIndex = processIndex

        for ((index, data) in dialogData.withIndex()) {
            data.isSelected = index == _currentIndex
        }
    }

    data class DialogData(val name: String, var isSelected: Boolean)
}