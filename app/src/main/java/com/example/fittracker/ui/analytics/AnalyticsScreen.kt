package com.example.fittracker.ui.analytics

import androidx.compose.foundation.layout.*

import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AnalyticsScreen(viewModel: AnalyticsViewModel) {

    val weights by viewModel.weightHistory.collectAsState()
    val goal by viewModel.goal.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {

        Text("Analytics", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(12.dp))

        goal?.let {
            Text("Goal: ${it.targetWeight} kg")
        }

        Spacer(Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            WeightChart(weights)
        }

        Spacer(Modifier.height(16.dp))

        val latest = weights.maxByOrNull { it.date }?.weight
        val target = goal?.targetWeight?.toDouble()

        if (latest != null && target != null) {

            val diff = latest - target

            Text(
                text = if (diff > 0)
                    "You are ${"%.1f".format(diff)}kg above your goal"
                else
                    "You are ${"%.1f".format(-diff)}kg below your goal"
            )
        }
    }
}