package com.example.drawer.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.drawer.model.screens
import com.example.drawer.ui.theme.DrawerTheme
import kotlinx.coroutines.launch

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

@SuppressLint("QueryPermissionsNeeded")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(navController: NavHostController) {
DrawerTheme {
    val screen= listOf(
        screens.main,
        screens.account,
        screens.settings,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val items = listOf(
        NavigationItem(
            title = "Main Screen",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
        ),
        NavigationItem(
            title = "Account Screen",
            selectedIcon = Icons.Filled.AccountCircle,
            unselectedIcon = Icons.Outlined.AccountCircle,
        ),
        NavigationItem(
            title = "Settings Screen",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
        ),
    )
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var selectedItemIndex by rememberSaveable {
            mutableIntStateOf(0)
        }
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    Box(
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxSize()
                            .background(
                                color = Color(
                                    0xFF3F51B5
                                )
                            )
                    ) {

                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    items.forEachIndexed { index, item ->
                        NavigationDrawerItem(
                            label = {
                                Text(text = item.title)
                            },
                            selected = index == selectedItemIndex,
                            onClick = {

                                selectedItemIndex = index
                                scope.launch {
                                    drawerState.close()
                                }
                                when (index) {
                                    0 -> navController.navigate(screens.main.route)
                                    1 -> navController.navigate(screens.account.route)
                                    2 -> navController.navigate(screens.settings.route)
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = if (index == selectedItemIndex) {
                                        item.selectedIcon
                                    } else item.unselectedIcon,
                                    contentDescription = item.title
                                )
                            },

                            )


                    }
                    Spacer(modifier = Modifier.padding(5.dp))
                    Divider(modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 0.dp))
                    Spacer(modifier = Modifier.padding(5.dp))
                    NavigationDrawerItem(
                        label = { Text(text = "Report a Bug") },
                        selected = false,
                        icon = {
                            Icon(
                                imageVector = Icons.Default.MailOutline,
                                contentDescription = null
                            )
                        },
                        onClick = {

                        })
                    NavigationDrawerItem(
                        label = { Text(text = "Info") },
                        selected = false,
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null
                            )
                        },
                        onClick = {
                            scope.launch {
                                drawerState.close()
                            }
                        })
                    NavigationDrawerItem(
                        label = { Text(text = "Donate") },
                        selected = false,
                        icon = {
                            Icon(
                                imageVector = Icons.Default.ThumbUp,
                                contentDescription = null
                            )
                        },
                        onClick = {
                            val url = "https://paypal.me/SohanMonies?country.x=IN&locale.x=en_GB" // Replace with your desired URL
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            navController.context.startActivity(intent)
                        })

                }
            },
            drawerState = drawerState
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = "QR & BarCode Scanner")
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Menu"
                                )
                            }
                        }
                    )
                },
                bottomBar = {
                    NavigationBar {
                        screen.forEach{
                                screen -> AddItem(screen = screen, currentDestination = currentDestination, navController = navController)
                        }
                    }
                }
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "This is MainScreen")
                    Button(onClick = {
                        navController.navigate(route = screens.account.route)
                    }) {
                        Text(text = "Click to Navigate")
                    }
                }
            }
        }
    }
}}

@Composable
fun RowScope.AddItem(
    screen : screens,
    currentDestination: NavDestination?,
    navController: NavHostController
){
    NavigationBarItem(label = {
        Text(text = screen.title)
    },
        icon = {
            Icon(imageVector = screen.icon, contentDescription = "Navigation Icon")
        }
        ,
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true ,
        onClick = {
            navController.navigate(screen.route)

        }
    )
}