package com.example.ayyappamala

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.ayyappamala.ui.MalaViewModel
import com.example.ayyappamala.ui.screens.GuideScreen
import com.example.ayyappamala.ui.screens.HistoryScreen
import com.example.ayyappamala.ui.screens.HomeScreen
import com.example.ayyappamala.ui.screens.SettingsScreen
import com.example.ayyappamala.ui.theme.AyyappaMalaTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MalaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AyyappaMalaTheme {
                MainScreen(viewModel = viewModel)
            }
        }
    }
}

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Tracker", Icons.Default.Home)
    object History : Screen("history", "History", Icons.Default.List)
    object Guide : Screen("guide", "Guide", Icons.Default.Info)
    object Settings : Screen("settings", "Settings", Icons.Default.Settings)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MalaViewModel) {
    val items = listOf(
        Screen.Home,
        Screen.History,
        Screen.Guide,
        Screen.Settings
    )
    var selectedRoute by remember { mutableStateOf(Screen.Home.route) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (selectedRoute) {
                            Screen.Home.route -> "Ayyappa Mala Tracker"
                            Screen.History.route -> "Sadhana History"
                            Screen.Guide.route -> "Guide & Mantras"
                            Screen.Settings.route -> "Profile & Backup"
                            else -> "Ayyappa Mala"
                        }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ) {
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = selectedRoute == screen.route,
                        onClick = { selectedRoute = screen.route },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            when (selectedRoute) {
                Screen.Home.route -> HomeScreen(viewModel = viewModel)
                Screen.History.route -> HistoryScreen(viewModel = viewModel)
                Screen.Guide.route -> GuideScreen()
                Screen.Settings.route -> SettingsScreen(viewModel = viewModel)
            }
        }
    }
}
