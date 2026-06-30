package com.example.fittracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.fittracker.data.local.DatabaseProvider
import com.example.fittracker.repository.WorkoutRepository
import com.example.fittracker.ui.theme.FitTrackerTheme
import com.example.fittracker.viewmodel.AuthViewModel
import com.example.fittracker.viewmodel.WorkoutViewModelFactory

import com.example.fittracker.ui.navigation.AppScaffold

import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.fittracker.data.repository.ExerciseRepository
import com.example.fittracker.navigation.Screen
import com.example.fittracker.repository.WorkoutSessionRepository
import com.example.fittracker.viewmodel.WorkoutViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {


        val splashScreen = installSplashScreen()
        var keepSplashOnScreen = true

        splashScreen.setKeepOnScreenCondition {
            keepSplashOnScreen
        }

        super.onCreate(savedInstanceState)

        val sharedPref = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)

        keepSplashOnScreen = false

        WindowCompat.setDecorFitsSystemWindows(window, false)

        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(
                WindowInsetsCompat.Type.statusBars() or
                        WindowInsetsCompat.Type.navigationBars()
            )

            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE


        }

        enableEdgeToEdge()



        setContent {

            val db = DatabaseProvider.getDatabase(this)

            val navController = rememberNavController()
            val authViewModel: AuthViewModel = viewModel()

            // -------------------------
            // REPOSITORIES (ONLY ONCE)
            // -------------------------

            val exerciseRepository = ExerciseRepository(
                db.exerciseDao()
            )

            val workoutRepository = WorkoutRepository(
                planDao = db.workoutPlanDao(),
                scheduleDao = db.workoutPlanScheduleDao(),
                workoutExerciseDao = db.workoutExerciseDao(),
                sessionDao = db.workoutSessionDao(),
                sessionExerciseDao = db.workoutSessionExerciseDao()
            )

            val workoutSessionRepository = WorkoutSessionRepository(
                sessionDao = db.workoutSessionDao(),
                setDao = db.workoutSessionSetDao(),
                sessionExerciseDao = db.workoutSessionExerciseDao()
            )
            // -------------------------
            // VIEWMODEL
            // -------------------------

            val workoutViewModel: WorkoutViewModel = viewModel(
                factory = WorkoutViewModelFactory(
                    workoutRepository,
                    exerciseRepository,
                    workoutSessionRepository
                )
            )

            AppScaffold(
                navController = navController,
                authViewModel = authViewModel,
                workoutViewModel = workoutViewModel
            )
        }
    }
}


