package com.example.fittracker.ui.workout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fittracker.data.local.entity.WorkoutExerciseWithExercise
import com.example.fittracker.navigation.Screen
import com.example.fittracker.viewmodel.WorkoutViewModel

@Composable
fun WorkoutPlanDetailsScreen(
    planId: Int,
    vm: WorkoutViewModel,
    navController: NavController
) {

    // -----------------------------
    // DATA
    // -----------------------------
    val exercises by vm.getExercisesWithDetails(planId)
        .collectAsState(initial = emptyList())

    // -----------------------------
    // DIALOG STATE (GLOBAL)
    // -----------------------------
    var selectedExercise by remember {
        mutableStateOf<WorkoutExerciseWithExercise?>(null)
    }

    var showEditDialog by remember { mutableStateOf(false) }

    var reps by remember { mutableStateOf("") }
    var sets by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }

    // -----------------------------
    // SCREEN
    // -----------------------------
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Workout Builder",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // -----------------------------
        // ADD EXERCISE
        // -----------------------------
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.navigate(Screen.ExercisePicker.route + "/$planId")
            }
        ) {
            Text("Add Exercise")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // -----------------------------
        // LIST
        // -----------------------------
        if (exercises.isEmpty()) {

            Text("No exercises yet. Add your first exercise.")

        } else {

            LazyColumn {

                items(exercises) { item ->

                    val ex = item.workoutExercise
                    val details = item.exercise

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    ) {

                        Column(modifier = Modifier.padding(12.dp)) {

                            Text(
                                text = details.name,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text("Sets: ${ex.targetSets}")
                            Text("Reps: ${ex.targetReps}")
                            Text("Weight: ${ex.targetWeight} kg")

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {

                                Button(onClick = {
                                    selectedExercise = item
                                    sets = ex.targetSets.toString()
                                    reps = ex.targetReps.toString()
                                    weight = ex.targetWeight.toString()
                                    showEditDialog = true
                                }) {
                                    Text("Edit")
                                }

                                Button(
                                    onClick = {
                                        vm.removeExerciseFromPlan(planId, ex.exerciseId)
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.error
                                    )
                                ) {
                                    Text("Delete")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // -----------------------------
    // EDIT DIALOG (OUTSIDE LIST)
    // -----------------------------
    if (showEditDialog && selectedExercise != null) {

        AlertDialog(
            onDismissRequest = {
                showEditDialog = false
                selectedExercise = null
            },
            confirmButton = {
                Button(onClick = {

                    vm.updateExerciseInPlan(
                        planId = planId,
                        exerciseId = selectedExercise!!.workoutExercise.exerciseId,
                        sets = sets.toIntOrNull() ?: 0,
                        reps = reps.toIntOrNull() ?: 0,
                        weight = weight.toFloatOrNull() ?: 0f
                    )

                    showEditDialog = false
                    selectedExercise = null
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                Button(onClick = {
                    showEditDialog = false
                    selectedExercise = null
                }) {
                    Text("Cancel")
                }
            },
            title = { Text("Edit Exercise") },
            text = {
                Column {

                    OutlinedTextField(
                        value = sets,
                        onValueChange = { sets = it },
                        label = { Text("Sets") }
                    )

                    OutlinedTextField(
                        value = reps,
                        onValueChange = { reps = it },
                        label = { Text("Reps") }
                    )

                    OutlinedTextField(
                        value = weight,
                        onValueChange = { weight = it },
                        label = { Text("Weight") }
                    )
                }
            }
        )
    }
}