package com.example.coffeememos.manager

import com.example.coffeememos.Constants
import java.io.Serializable

class ProcessListManager(private var _currentProcess: Int) {
    val currentProcess: Int
        get() = _currentProcess


    fun createProcessList(): List<Process> {
        val result = mutableListOf<Process>()

        for ((index, process) in Constants.processList.withIndex()) {
            val isSelected = index == _currentProcess

            result.add(Process(process, isSelected))
        }

        return result
    }

    fun setCurrentProcess(process: Int) {
        _currentProcess = process
    }

    data class Process(val name: String, var isSelected: Boolean)
}