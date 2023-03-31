package com.withapp.coffeememo

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import com.withapp.coffeememo.core.ad_mob.AdMobManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CoffeeMemosApplication : Application() {
    val database :CoffeeMemosDatabase by lazy {
        CoffeeMemosDatabase.getDatabase(this)
    }

    override fun onCreate() {
        super.onCreate()
        AdMobManager.init(this)
    }
}