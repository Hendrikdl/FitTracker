package com.example.fittracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

import com.example.fittracker.ui.home.HomeScreen
import com.example.fittracker.ui.login.LoginScreen
import com.example.fittracker.ui.register.RegisterScreen
import com.example.fittracker.ui.workout.WorkoutPlansScreen
import com.example.fittracker.ui.workout.ExercisePickerScreen
import com.example.fittracker.ui.workout.WorkoutPlanDetailsScreen
import com.example.fittracker.ui.workout.WorkoutSessionOverviewScreen
import com.example.fittracker.ui.workout.WorkoutExerciseLogScreen

import com.example.fittracker.viewmodel.AuthViewModel
import com.example.fittracker.viewmodel.WorkoutViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    workoutViewModel: WorkoutViewModel
) {

    NavHost(
        navController = navController,
        startDestination =
            if (authViewModel.isLoggedIn())
                Screen.Home.route
            else
                Screen.Login.route
    ) {

        // -------------------------
        // PLAN DETAILS
        // -------------------------
        composable(
            route = "plan_detail/{planId}",
            arguments = listOf(
                navArgument("planId") { type = NavType.IntType }
            )
        ) { backStackEntry ->

            val planId =
                backStackEntry.arguments?.getInt("planId") ?: return@composable

            WorkoutPlanDetailsScreen(
                planId = planId,
                vm = workoutViewModel,
                navController = navController
            )
        }

        // -------------------------
        // LOGIN
        // -------------------------
        composable(Screen.Login.route) {
            LoginScreen(navController, authViewModel)
        }

        // -------------------------
        // REGISTER
        // -------------------------
        composable(Screen.Register.route) {
            RegisterScreen(navController, authViewModel)
        }

        // -------------------------
        // HOME
        // -------------------------
        composable(Screen.Home.route) {
            HomeScreen(
                navController = navController,
                vm = workoutViewModel
            )
        }

        // -------------------------
        // WORKOUT PLANS
        // -------------------------
        composable(Screen.WorkoutPlans.route) {
            WorkoutPlansScreen(
                navController = navController,
                vm = workoutViewModel
            )
        }

        // -------------------------
        // SESSION OVERVIEW
        // -------------------------
        composable(
            route = "session_overview/{sessionId}/{planId}",
            arguments = listOf(
                navArgument("sessionId") { type = NavType.IntType },
                navArgument("planId") { type = NavType.IntType }
            )
        ) { backStackEntry ->

            val sessionId =
                backStackEntry.arguments?.getInt("sessionId") ?: return@composable

            val planId =
                backStackEntry.arguments?.getInt("planId") ?: return@composable

            WorkoutSessionOverviewScreen(
                sessionId = sessionId,
                planId = planId,
                vm = workoutViewModel,
                navController = navController
            )
        }

        // -------------------------
        // WORKOUT SESSION (LIST ONLY)
        // -------------------------
        composable(
            route = "exercise_log/{sessionId}/{exerciseId}",
            arguments = listOf(
                navArgument("sessionId") {
                    type = NavType.IntType
                },
                navArgument("exerciseId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val sessionId =
                backStackEntry.arguments?.getInt("sessionId")
                    ?: return@composable

            val exerciseId =
                backStackEntry.arguments?.getInt("exerciseId")
                    ?: return@composable

            WorkoutExerciseLogScreen(
                vm = workoutViewModel,
                sessionId = sessionId,
                exerciseId = exerciseId,
                navController = navController
            )
        }
        // -------------------------
        // EXERCISE PICKER
        // -------------------------
        composable(
            route = Screen.ExercisePicker.route + "/{planId}",
            arguments = listOf(
                navArgument("planId") { type = NavType.IntType }
            )
        ) { backStackEntry ->

            val planId =
                backStackEntry.arguments?.getInt("planId") ?: 0

            ExercisePickerScreen(
                navController = navController,
                vm = workoutViewModel,
                planId = planId
            )
        }
    }
}