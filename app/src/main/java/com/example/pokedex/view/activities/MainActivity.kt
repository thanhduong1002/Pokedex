package com.example.pokedex.view.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.pokedex.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private var isBottomNavVisible = true

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment

        navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        bottomNavigationView.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.cartFragment -> {
                    if (!isBottomNavVisible) {
                        isBottomNavVisible = true
                        bottomNavigationView.visibility = View.VISIBLE
                    }
                    navController.navigate(R.id.cartFragment)
                }
                R.id.homeFragment -> {
                    if (!isBottomNavVisible) {
                        isBottomNavVisible = true
                        bottomNavigationView.visibility = View.VISIBLE
                    }
                    navController.navigate(R.id.homeFragment)
                }
                R.id.supportFragment -> {
                    if (!isBottomNavVisible) {
                        isBottomNavVisible = true
                        bottomNavigationView.visibility = View.VISIBLE
                    }
                    navController.navigate(R.id.supportFragment)
                }
                R.id.settingFragment -> {
                    if (!isBottomNavVisible) {
                        isBottomNavVisible = true
                        bottomNavigationView.visibility = View.VISIBLE
                    }
                    navController.navigate(R.id.settingFragment)
                }
                R.id.profileFragment -> {
                    if (isBottomNavVisible) {
                        isBottomNavVisible = false
                        bottomNavigationView.visibility = View.VISIBLE
                    }
                    navController.navigate(R.id.profileFragment)
                }
            }
        }
    }
}