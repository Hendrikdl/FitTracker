    package com.example.fittracker.navigation

    sealed class Screen(val route: String) {

        object Login : Screen("login")
        object Register : Screen("register")
        object Home : Screen("home")
        object History : Screen("history")
        object Analytics : Screen("analytics")
        object Profile : Screen("profile")
        object ExercisePicker : Screen("exercise_picker")

        object Settings : Screen("settings")
        object ExerciseLibrary : Screen("exercise_library")

        object WorkoutPlans : Screen("workout_plans")
        object WorkoutSession : Screen("workout_session/{planId}") {
            fun createRoute(planId: Int) = "workout_session/$planId"
        }
        object WorkoutComplete : Screen("workout_complete")

        object WorkoutPlanDetail : Screen("workout_plan_detail/{planId}") {
            fun createRoute(planId: Int) = "workout_plan_detail/$planId"
        }
        object WorkoutBuilder : Screen("workout_builder")
    }