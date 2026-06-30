package com.example.fittracker.ui.home

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
fun HomeScreen(
    navController: NavController,
    vm: WorkoutViewModel
) {

    val todayPlans by vm.getTodayPlans()
        .collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Hey Hendrik 👋",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Today's Workouts",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (todayPlans.isEmpty()) {

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Rest Day / No workouts scheduled")
                }
            }

        } else {

            LazyColumn {
                items(todayPlans) { plan ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    ) {

                        Column(modifier = Modifier.padding(16.dp)) {

                            Text(
                                text = plan.name,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Button(
                                onClick = {
                                    vm.startWorkout(plan.id) { sessionId ->
                                        navController.navigate(
                                            "session_overview/$sessionId/${plan.id}"
                                        )
                                    }
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Start Workout")
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    text = "Weekly Stats",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text("Workouts this week: 0")
                Text("Longest workout: --")
                Text("Heaviest weight: --")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                navController.navigate("workout_plans")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Workout Builder")
        }
    }
}