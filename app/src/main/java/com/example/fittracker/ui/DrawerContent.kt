package com.example.fittracker.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.fittracker.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DrawerContent(
    navController: NavController,
    drawerScope: CoroutineScope,
    drawerState: DrawerState
) {

    val currentRoute by navController.currentBackStackEntryAsState()
    val route = currentRoute?.destination?.route

    ModalDrawerSheet {

        Text(
            text = "FitTracker",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)
        )

        HorizontalDivider()

        // HOME
        NavigationDrawerItem(
            label = { Text("Home") },
            icon = { Icon(Icons.Default.Home, null) },
            selected = route == Screen.Home.route,
            onClick = {
                drawerScope.launch { drawerState.close() }
                navController.navigate(Screen.Home.route)
            }
        )

        NavigationDrawerItem(
            label = { Text("Workout Plans") },
            selected = false,
            onClick = {
                drawerScope.launch {
                    drawerState.close()
                }
                navController.navigate(Screen.WorkoutPlans.route)
            }
        )

        // HISTORY
        NavigationDrawerItem(
            label = { Text("History") },
            icon = { Icon(Icons.Default.History, null) },
            selected = route == Screen.History.route,
            onClick = {
                drawerScope.launch { drawerState.close() }
                navController.navigate(Screen.History.route)
            }
        )

        // EXERCISE LIBRARY
        NavigationDrawerItem(
            label = { Text("Exercise Library") },
            icon = { Icon(Icons.Default.FitnessCenter, null) },
            selected = route == Screen.ExerciseLibrary.route,
            onClick = {
                drawerScope.launch { drawerState.close() }
                navController.navigate(Screen.ExerciseLibrary.route)
            }
        )

        // WORKOUT BUILDER
        NavigationDrawerItem(
            label = { Text("Workout Builder") },
            icon = { Icon(Icons.Default.FitnessCenter, null) },
            selected = route == Screen.WorkoutBuilder.route,
            onClick = {
                drawerScope.launch { drawerState.close() }
                navController.navigate(Screen.WorkoutBuilder.route)
            }
        )

        // ANALYTICS
        NavigationDrawerItem(
            label = { Text("Analytics") },
            icon = { Icon(Icons.Default.ShowChart, null) },
            selected = route == Screen.Analytics.route,
            onClick = {
                drawerScope.launch { drawerState.close() }
                navController.navigate(Screen.Analytics.route)
            }
        )

        // PROFILE
        NavigationDrawerItem(
            label = { Text("Profile") },
            icon = { Icon(Icons.Default.Person, null) },
            selected = route == Screen.Profile.route,
            onClick = {
                drawerScope.launch { drawerState.close() }
                navController.navigate(Screen.Profile.route)
            }
        )

        // SETTINGS
        NavigationDrawerItem(
            label = { Text("Settings") },
            icon = { Icon(Icons.Default.Settings, null) },
            selected = route == Screen.Settings.route,
            onClick = {
                drawerScope.launch { drawerState.close() }
                navController.navigate(Screen.Settings.route)
            }
        )
    }
}