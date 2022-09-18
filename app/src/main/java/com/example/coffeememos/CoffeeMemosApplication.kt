package com.example.coffeememos

import android.app.Application

class CoffeeMemosApplication : Application() {
    lateinit var database :CoffeeMemosDatabase

    override fun onCreate() {
        super.onCreate()
        database =  CoffeeMemosDatabase.getDatabase(this)
    }
}