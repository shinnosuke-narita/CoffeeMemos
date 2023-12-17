package com.withapp.coffeememo

import android.app.Application
import com.withapp.coffeememo.core.ad_mob.AdMobManager
import com.withapp.coffeememo.core.data.CoffeeMemosDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CoffeeMemosApplication : Application() {
    val database : CoffeeMemosDatabase by lazy {
        CoffeeMemosDatabase.getDatabase(this)
    }

    override fun onCreate() {
        super.onCreate()
        AdMobManager.init(this)
    }
}