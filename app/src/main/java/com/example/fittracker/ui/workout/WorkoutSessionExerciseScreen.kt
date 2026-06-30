package com.example.fittracker.ui.workout

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fittracker.viewmodel.WorkoutViewModel

@Composable
fun WorkoutSessionExerciseScreen(
    vm: WorkoutViewModel,
    sessionId: Int,
    exerciseId: Int,
    navController: NavController
) {

    val exercises by vm.getSessionExercises(sessionId)
        .collectAsState(initial = emptyList())

    val currentExercise = exercises.firstOrNull {
        it.exerciseId == exerciseId
    }

    if (currentExercise == null) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text("Exercise not found")
            Button(onClick = { navController.popBackStack() }) {
                Text("Back")
            }
        }
        return
    }

    var reps by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(currentExercise.exerciseName, style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Sets: ${currentExercise.targetSets}")

        OutlinedTextField(
            value = reps,
            onValueChange = { reps = it },
            label = { Text("Reps") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("Weight") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                vm.saveSet(
                    sessionId = sessionId,
                    exerciseId = exerciseId,
                    setNumber = 1,
                    reps = reps.toIntOrNull() ?: 0,
                    weight = weight.toFloatOrNull() ?: 0f
                )

                navController.popBackStack()
            }
        ) {
            Text("Save Set")
        }
    }
}