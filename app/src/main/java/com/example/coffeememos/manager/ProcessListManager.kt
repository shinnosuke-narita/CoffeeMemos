package com.example.coffeememos.manager

import com.example.coffeememos.Constants

class ProcessListManager(private var _currentProcess: Int) {
    val currentProcess: Int
        get() = _currentProcess

    val processList: List<Process>

    init {
        processList = createProcessList()
    }

    fun createProcessList(): List<Process> {
        val result = mutableListOf<Process>()

        for ((index, process) in Constants.processList.withIndex()) {
            val isSelected = index == _currentProcess

            result.add(Process(process, isSelected))
        }

        return result
    }

    fun updateProcessList(processIndex: Int) {
        _currentProcess = processIndex

        for ((index, process) in processList.withIndex()) {
            process.isSelected = index == _currentProcess
        }
    }

    data class Process(val name: String, var isSelected: Boolean)
}