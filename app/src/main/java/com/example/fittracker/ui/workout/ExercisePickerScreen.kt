package com.example.fittracker.ui.workout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fittracker.data.local.entity.ExerciseEntity
import com.example.fittracker.viewmodel.WorkoutViewModel

@Composable
fun ExercisePickerScreen(
    navController: NavController,
    vm: WorkoutViewModel,
    planId: Int
) {

    var query by remember { mutableStateOf("") }

    val exercises by vm.searchExercises(query)
        .collectAsState(initial = emptyList())

    var selectedExercise by remember {
        mutableStateOf<ExerciseEntity?>(null)
    }

    var sets by remember { mutableStateOf("3") }
    var reps by remember { mutableStateOf("10") }
    var weight by remember { mutableStateOf("0") }
    var rest by remember { mutableStateOf("90") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Select Exercise",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search exercises") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {

            items(exercises) { exercise ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable {

                            selectedExercise = exercise

                            // Default values
                            sets = "3"
                            reps = "10"
                            weight = "0"
                            rest = "90"
                        }
                ) {

                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {

                        Text(
                            text = exercise.name,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(text = exercise.primaryMuscle)

                        Text(
                            text = exercise.equipment,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }

        selectedExercise?.let { exercise ->

            HorizontalDivider()

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Configure ${exercise.name}",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = sets,
                onValueChange = { sets = it },
                label = { Text("Sets") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = reps,
                onValueChange = { reps = it },
                label = { Text("Target Reps") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = weight,
                onValueChange = { weight = it },
                label = { Text("Target Weight (kg)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = rest,
                onValueChange = { rest = it },
                label = { Text("Rest (seconds)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {

                    vm.addExerciseToPlan(
                        planId = planId,
                        exerciseId = exercise.id,
                        sets = sets.toIntOrNull() ?: 3,
                        reps = reps.toIntOrNull() ?: 10,
                        weight = weight.toFloatOrNull() ?: 0f,
                        rest = rest.toIntOrNull() ?: 90,
                        order = 0
                    )

                    navController.popBackStack()
                }
            ) {
                Text("Add Exercise")
            }
        }
    }
}