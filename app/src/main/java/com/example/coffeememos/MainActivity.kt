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

        // toolbarとNavControllerの関連付け
        binding.toolbar.setupWithNavController(navController, AppBarConfiguration(navController.graph))

        navController.addOnDestinationChangedListener {_, destination, _ ->
            when(destination.id) {
                R.id.homeFragment       -> {
                    showBottomNav()
                    showToolbar()
                }
                R.id.favoriteFragment   -> {
                    showBottomNav()
                    showToolbar()
                }
                R.id.beansFragment      -> {
                    showBottomNav()
                    showToolbar()
                }
                R.id.timerFragment      -> {
                    showBottomNav()
                    showToolbar()
                }
                R.id.selectBeanFragment -> {
                    hideBottomNav()
                    showToolbar()
                }
                R.id.newRecipeFragment  -> {
                    hideBottomNav()
                    hideToolbar()
                }
                R.id.newBeanFragment    -> {
                    hideBottomNav()
                    hideToolbar()
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

                Constants.sampleTaste.recipeId = recipes[0].id
                tasteDao.insert(Constants.sampleTaste)

                Constants.sampleTaste.recipeId = recipes[1].id
                tasteDao.insert(Constants.sampleTaste)

                val result = beanDao.getBeanWithRecipe()

            }
        }

    }

    private fun showBottomNav() {
        binding.bottomNavBar.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        binding.bottomNavBar.visibility = View.GONE
    }

    private fun showToolbar() {
        binding.toolbar.visibility = View.VISIBLE
    }

    private fun hideToolbar() {
        binding.toolbar.visibility = View.GONE
    }
}