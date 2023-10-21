package com.example.drawer.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector


sealed class screens(val route :String,val title :String,val icon: ImageVector){
    object main:screens(route = "main_screen","Home", Icons.Default.Home)
    object account:screens(route = "account_screen","Account",Icons.Default.AccountCircle)
    object settings:screens(route = "settings_screen","Settings",Icons.Default.Settings)
}
