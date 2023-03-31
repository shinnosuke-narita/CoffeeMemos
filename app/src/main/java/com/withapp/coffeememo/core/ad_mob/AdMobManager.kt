package com.withapp.coffeememo.core.ad_mob

import android.content.Context
import android.view.ViewGroup
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.withapp.coffeememo.BuildConfig

object AdMobManager {
    private val proAdUnitId = "ca-app-pub-7093807169305333/9360848027"
    private val devAdUnitId = "ca-app-pub-3940256099942544/6300978111"

    // AdMob 初期化
    fun init(context: Context) {
        MobileAds.initialize(context) {}
    }

    fun loadAd(context: Context, adViewPlace: ViewGroup) {
        val adView: AdView = makeAdView(context, adViewPlace)

        val adRequest: AdRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    private fun makeAdView(
        context: Context,
        adMobPlace: ViewGroup
    ): AdView {
        val adView = AdView(context)
        adView.setAdSize(AdSize.BANNER)
        adView.adUnitId =
            if (BuildConfig.DEBUG) devAdUnitId else proAdUnitId

        adMobPlace.addView(adView)

        return adView
    }
}