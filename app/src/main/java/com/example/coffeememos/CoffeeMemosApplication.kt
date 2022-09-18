package com.example.coffeememos

import android.app.Application

class CoffeeMemosApplication : Application() {
    val database :CoffeeMemosDatabase by lazy {
        CoffeeMemosDatabase.getDatabase(this)
    }
}