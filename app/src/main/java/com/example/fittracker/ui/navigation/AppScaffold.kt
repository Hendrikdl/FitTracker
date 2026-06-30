package com.example.fittracker.ui.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.example.fittracker.navigation.NavGraph
import com.example.fittracker.viewmodel.AuthViewModel
import com.example.fittracker.viewmodel.WorkoutViewModel

@Composable
fun AppScaffold(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    workoutViewModel: WorkoutViewModel,
) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                navController = navController,
                drawerScope = scope,
                drawerState = drawerState
            )
        }
    ) {
        NavGraph(
            navController = navController,
            authViewModel = authViewModel,
            workoutViewModel = workoutViewModel,

        )
    }
}