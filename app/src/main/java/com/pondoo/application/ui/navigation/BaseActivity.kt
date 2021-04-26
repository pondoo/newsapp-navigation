package com.pondoo.application.ui.navigation

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.pondoo.application.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        this.supportActionBar!!.hide()
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        isNightModeOn()
    }
    private fun isNightModeOn(){
        val prefences = this.getSharedPreferences("Utils", Context.MODE_PRIVATE)
        var night=prefences.getBoolean("night",false)
        if(night){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO                  )
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) supportFragmentManager.popBackStack()
            else super.onBackPressed()


    }

}