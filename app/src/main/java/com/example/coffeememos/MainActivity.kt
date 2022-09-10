package com.example.coffeememos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.coffeememos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // bottomTabとnavigationの関連付け
        val hostNav = findNavController(R.id.nav_host)
        NavigationUI.setupWithNavController(binding.bottomNavBar, hostNav)
    }
}