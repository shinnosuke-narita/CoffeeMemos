package com.example.coffeememos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
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
                R.id.homeFragment       -> showBottomNav()
                R.id.favoriteFragment   -> showBottomNav()
                R.id.beansFragment      -> showBottomNav()
                R.id.timerFragment      -> showBottomNav()
                R.id.selectBeanFragment -> hideBottomNav()
                R.id.newRecipeFragment  -> hideBottomNav()
            }
        }

        GlobalScope.launch {

            recipeDao.clearTable()
            beanDao.clearTable()
            tasteDao.clearTable()

            for (i in 1..5) {
                beanDao.insert(Constants.sampleBean)
            }


            val beans = beanDao.getAll()

            Constants.sampleRecipe1.beanId = beans[0].id
            Constants.sampleRecipe2.beanId = beans[0].id

            recipeDao.insert(Constants.sampleRecipe1)
            recipeDao.insert(Constants.sampleRecipe2)


            val recipes = recipeDao.getAll()

            Constants.sampleTaste.recipeId = recipes[0].id
            tasteDao.insert(Constants.sampleTaste)

            Constants.sampleTaste.recipeId = recipes[1].id
            tasteDao.insert(Constants.sampleTaste)



            val result = beanDao.getBeanWithRecipe()
            val recipeAndBean = result[0]
            val bean = recipeAndBean.bean


            val result2 = recipeDao.getRecipeWithTaste()
            val recipeWithTaste = result2[0]
            val recipe = recipeWithTaste.recipe
            val taste = recipeWithTaste.taste


            System.out.println("hello")
        }
        System.out.println("hello")
    }

    private fun showBottomNav() {
        binding.bottomNavBar.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        binding.bottomNavBar.visibility = View.GONE
    }
}