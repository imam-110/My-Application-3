package kiet.imam.myapplication

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kiet.imam.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ElectricityConsumptionScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
    @Composable
    fun ElectricityConsumptionScreen(modifier: Modifier = Modifier) {
        val viewModel: ConsumptionViewModel = viewModel()
        val data by viewModel.data

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Electricity Consumption",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (data.isEmpty()) {
                Text(text = "Loading data...", fontSize = 14.sp)
            } else {
                LineGraph(data = data.map { it.electricity_demand })

                Spacer(modifier = Modifier.height(16.dp))
                Text("Hourly Predictions", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))

                Column {
                    data.forEach { item ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "${item.hour_of_day}h", modifier = Modifier.weight(1f))
                            Text(text = "${item.electricity_demand} kWh", modifier = Modifier.weight(1f))
                            Text(text = item.label, maxLines = 1,modifier = Modifier.weight(1f))
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(onClick = {viewModel.refreshData()}) { 
                            Text("Refresh")
                        }
                        Button(onClick = {}) {
                            Text("Download")
                        }
                    }
                }
            }
        }
    }


    @Composable
    fun LineGraph(data: List<Float>) {
        AndroidView(
            factory = { context ->
                LineChart(context).apply {
                    description.isEnabled = false
                    setTouchEnabled(true)
                    setPinchZoom(true)
                    setDrawGridBackground(false)
                    xAxis.apply {
                        position = XAxis.XAxisPosition.BOTTOM
                        setDrawGridLines(false)
                        granularity = 1f
                        textSize = 12f
                    }
                    axisLeft.apply {
                        setDrawGridLines(false)
                        textSize = 12f
                    }
                    axisRight.isEnabled = false

                    val entries = data.mapIndexed { index, value -> Entry(index.toFloat(), value) }
                    val dataSet = LineDataSet(entries, "Hourly Consumption").apply {
                        color = R.color.teal_700
                        valueTextColor = R.color.black
                        lineWidth = 2f
                        setCircleColor(R.color.teal_700)
                        circleRadius = 4f
                        setDrawCircleHole(false)
                        setDrawValues(true)
                    }
                    this.data = LineData(dataSet)
                    invalidate()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
    }



