package com.example.fittracker.ui.workout

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import com.example.fittracker.viewmodel.WorkoutViewModel
import com.example.fittracker.data.local.entity.WorkoutSet

// ✅ MOVE OUTSIDE COMPOSABLE (IMPORTANT)
data class SetInput(
    val setNumber: Int,
    var weight: String,
    var reps: String
)

@Composable
fun WorkoutExerciseLogScreen(
    vm: WorkoutViewModel,
    sessionId: Int,
    exerciseId: Int,
    navController: NavController
) {

    val exercises by vm.getSessionExercises(sessionId)
        .collectAsState(initial = emptyList())

    val exercise = exercises.firstOrNull {
        it.exerciseId == exerciseId
    }

    if (exercise == null) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text("Exercise not found")
        }
        return
    }

    val setInputs = remember {
        mutableStateListOf<SetInput>()
    }

    LaunchedEffect(exercise) {

        setInputs.clear()

        repeat(exercise.targetSets) { index ->
            setInputs.add(
                SetInput(
                    setNumber = index + 1,
                    weight = exercise.targetWeight.toString(),
                    reps = exercise.targetReps.toString()
                )
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            text = exercise.exerciseName,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("Target Sets: ${exercise.targetSets}")
        Text("Target Reps: ${exercise.targetReps}")
        Text("Target Weight: ${exercise.targetWeight} kg")

        Spacer(modifier = Modifier.height(24.dp))

        setInputs.forEachIndexed { index, input ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {

                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        text = "Set ${index + 1}",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = input.weight,
                        onValueChange = { input.weight = it },
                        label = { Text("Weight (kg)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = input.reps,
                        onValueChange = { input.reps = it },
                        label = { Text("Reps") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {

                val sets = setInputs.map { input ->

                    WorkoutSet(
                        sessionExerciseId = exercise.exerciseId,
                        setNumber = input.setNumber,
                        reps = input.reps.toIntOrNull() ?: 0,
                        weight = input.weight.toFloatOrNull() ?: 0f,
                        isCompleted = true
                    )
                }

                vm.saveExerciseSets(sets)

                navController.popBackStack()
            }
        ) {
            Text("Finish Exercise")
        }
    }
}