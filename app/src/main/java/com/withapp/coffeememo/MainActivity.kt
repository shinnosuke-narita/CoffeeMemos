package com.withapp.coffeememo

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.ads.AdRequest
import com.withapp.coffeememo.databinding.ActivityMainBinding
import com.withapp.coffeememo.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // viewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // BottomNavigation セットアップ
        setUpBottomNavigation()

        // 広告読み込み
        loadAd()

        // timer画面のボトムナビゲーション 監視
        viewModel.newRecipeFragmentExists.observe(this) { exists ->
            if (exists) hideBottomNav()
        }
    }

    private fun setUpBottomNavigation() {
        val navHostFragment =
            supportFragmentManager
                .findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController

        // bottomNavigationとNavControllerの関連付け
        binding.bottomNavBar.setupWithNavController(navController)

        navController.addOnDestinationChangedListener {_, destination, _ ->
            when(destination.id) {
                R.id.homeRecipeFragment -> {
                    showBottomNav()
                }
                R.id.homeBeansFragment -> {
                    showBottomNav()
                }
                R.id.timerFragment -> {
                    showBottomNav()
                }
                R.id.searchFragment -> {
                    showBottomNav()
                }
                R.id.baseFavoriteFragment -> {
                    showBottomNav()
                }
                else -> {
                    hideBottomNav()
                }
            }
        }
    }

    private fun showBottomNav() {
        binding.bottomNavBar.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        binding.bottomNavBar.visibility = View.GONE
    }

    private fun loadAd() {
        val adRequest: AdRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }
}