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
fun WorkoutPlansScreen(
    navController: NavController,
    vm: WorkoutViewModel
) {

    val plans by vm.plans.collectAsState()

    var name by remember { mutableStateOf("") }

    val days = remember {
        mutableStateListOf(
            DayItem("Mo", 1),
            DayItem("Tu", 2),
            DayItem("We", 3),
            DayItem("Th", 4),
            DayItem("Fr", 5),
            DayItem("Sa", 6),
            DayItem("Su", 7)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // -------------------------
        // TITLE
        // -------------------------
        Text(
            text = "Workout Builder",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // -------------------------
        // PLAN NAME
        // -------------------------
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Plan Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // -------------------------
        // DAY PICKER
        // -------------------------
        Text("Select Training Days")

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            days.forEach { day ->

                val isSelected = day.selected.value

                val backgroundColor =
                    if (isSelected) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.surfaceVariant

                val textColor =
                    if (isSelected) MaterialTheme.colorScheme.onPrimary
                    else MaterialTheme.colorScheme.onSurface

                Card(
                    modifier = Modifier
                        .weight(1f)
                        .padding(2.dp)
                        .clickable {
                            day.selected.value = !day.selected.value
                        },
                    colors = CardDefaults.cardColors(containerColor = backgroundColor)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(8.dp),
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        Text(day.label, color = textColor)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // -------------------------
        // CREATE PLAN BUTTON
        // -------------------------
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {

                val selectedDays = days
                    .filter { it.selected.value }
                    .map { it.value }

                if (name.isNotBlank() && selectedDays.isNotEmpty()) {

                    vm.createPlan(
                        name = name,
                        description = "",
                        daysOfWeek = selectedDays
                    )

                    // reset
                    name = ""
                    days.forEach { it.selected.value = false }
                }
            }
        ) {
            Text("Create Plan")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // -------------------------
        // PLAN LIST
        // -------------------------
        LazyColumn {

            items(plans) { plan ->

                val schedule by vm.getSchedule(plan.id)
                    .collectAsState(initial = emptyList())

                val daysText = remember(schedule) {
                    schedule
                        .sortedBy { it.dayOfWeek }
                        .joinToString(" ") { day ->
                            when (day.dayOfWeek) {
                                1 -> "Mo"
                                2 -> "Tu"
                                3 -> "We"
                                4 -> "Th"
                                5 -> "Fr"
                                6 -> "Sa"
                                7 -> "Su"
                                else -> "?"
                            }
                        }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {

                    Column(modifier = Modifier.padding(16.dp)) {

                        Text(
                            text = plan.name,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = daysText.ifBlank { "No days selected" },
                            style = MaterialTheme.typography.bodySmall
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // ✅ NEW: Enter builder screen
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                navController.navigate("plan_detail/${plan.id}")
                            }
                        ) {
                            Text("Add / Edit Exercises")
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Delete plan
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                vm.deletePlan(plan.id)
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