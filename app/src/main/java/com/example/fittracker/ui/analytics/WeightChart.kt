package com.example.fittracker.ui.analytics

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.viewinterop.AndroidView
import com.example.fittracker.data.local.entity.WeightLog
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun WeightChart(weights: List<WeightLog>) {

    val sorted = weights.sortedBy { it.date }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            LineChart(context)
        },
        update = { chart ->

            // -----------------------------
            // 1. Convert data into entries
            // -----------------------------
            val entries = sorted.map { log ->
                Entry(
                    log.date.toFloat(),
                    log.weight.toFloat()
                )
            }

            val dataSet = LineDataSet(entries, "Weight Progress").apply {
                setDrawValues(false)
                lineWidth = 3f
                circleRadius = 4f
            }

            chart.data = LineData(dataSet)

            // -----------------------------
            // 2. Description (clean UI)
            // -----------------------------
            chart.description = Description().apply {
                text = "Weight Progress Over Time"
            }

            // -----------------------------
            // 3. X-axis formatting (dates)
            // -----------------------------
            val dateFormat = SimpleDateFormat("dd MMM", Locale.getDefault())

            chart.xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                granularity = 1f
                setDrawGridLines(false)

                valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return dateFormat.format(Date(value.toLong()))
                    }
                }
            }

            // -----------------------------
            // 4. UI cleanup
            // -----------------------------
            chart.axisRight.isEnabled = false
            chart.axisLeft.setDrawGridLines(true)
            chart.legend.isEnabled = true

            chart.invalidate()
        }
    )
}