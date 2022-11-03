package com.example.coffeememos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.coffeememos.Constants
import com.example.coffeememos.databinding.ActivityMainBinding
import com.example.coffeememos.entity.Taste
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val database = (application as CoffeeMemosApplication).database
        val recipeDao = database.recipeDao()
        val beanDao = database.beanDao()
        val tasteDao = database.tasteDao()



        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
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
                R.id.selectBeanFragment -> {
                    hideBottomNav()
                }
                R.id.newRecipeFragment -> {
                    hideBottomNav()
                }
                R.id.newBeanFragment -> {
                    hideBottomNav()
                }
            }
        }


        // データベース初期化
        if (Constants.databaseResetFlag) {
            GlobalScope.launch {
                recipeDao.clearTable()
                beanDao.clearTable()
                tasteDao.clearTable()

                beanDao.insert(Constants.sampleBean)
                beanDao.insert(Constants.sampleBean2)
                beanDao.insert(Constants.sampleBean3)
                beanDao.insert(Constants.sampleBean4)
                beanDao.insert(Constants.sampleBean5)

                val beans = beanDao.getAll()
                for (bean in beans) {
                    Constants.sampleRecipe1.beanId = bean.id
                    Constants.sampleRecipe2.beanId = bean.id
                    recipeDao.insert(Constants.sampleRecipe1)
                    recipeDao.insert(Constants.sampleRecipe2)
                }

                val recipes = recipeDao.getAll()
                for ((index, recipe) in recipes.withIndex()) {
                    val value = (index % 5) + 1
                    tasteDao.insert(
                        Taste(
                            0,
                            recipe.id,
                            value,
                            value,
                            value,
                            value,
                            value
                        )
                    )
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
}