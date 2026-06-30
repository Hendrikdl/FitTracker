package com.example.fittracker.ui.workout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fittracker.viewmodel.WorkoutViewModel

@Composable
fun WorkoutSessionOverviewScreen(
    sessionId: Int,
    planId: Int,
    vm: WorkoutViewModel,
    navController: NavController
) {

    val sessionExercises by vm.getSessionExercises(sessionId)
        .collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Workout Session",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Tap an exercise to start / continue",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (sessionExercises.isEmpty()) {
            Text("No exercises found in this session")
            return
        }

        LazyColumn {

            items(sessionExercises) { ex ->

                val progressText = "Planned: ${ex.targetSets} sets"

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable {

                            navController.navigate("exercise_log/$sessionId/${ex.exerciseId}")
                        }
                ) {

                    Column(modifier = Modifier.padding(16.dp)) {

                        Text(
                            text = "Exercise ID: ${ex.exerciseId}",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(progressText)

                        Text("Reps: ${ex.targetReps}")
                        Text("Weight: ${ex.targetWeight} kg")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Finish Session")
        }
    }
}