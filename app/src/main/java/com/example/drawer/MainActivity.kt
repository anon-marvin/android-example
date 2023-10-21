package com.example.drawer

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.drawer.model.screens
import com.example.drawer.screens.AccountScreen
import com.example.drawer.screens.MyApp
import com.example.drawer.screens.SettingScreen
import com.example.drawer.ui.theme.DrawerTheme


class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController // This is the nav controller
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DrawerTheme() { // Root Composable Component
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        navController = rememberNavController() // Navcontroller initialization
                        NavHost(
                            navController = navController,
                            startDestination = screens.main.route
                        ) {
                            composable(
                                route = screens.main.route
                            ) {
                                MyApp(navController)
                            }
                            composable(
                                route = screens.account.route
                            ) {
                                AccountScreen(navController)
                            }
                            composable(
                                route = screens.settings.route
                            ) {
                                SettingScreen(navController)
                            }
                        }

                    }
                }
            }
        }
    }

