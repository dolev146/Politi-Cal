package com.example.politi_cal.screens.analytics

//import java.util.ArrayList
import android.graphics.Typeface
import android.os.Build
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.politi_cal.R
import com.example.politi_cal.Screen
import com.example.politi_cal.adminAnalyticsTitle
import com.example.politi_cal.adminDistribution
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.firebase.auth.FirebaseAuth
import kotlin.streams.toList

var admin_pieChart : PieChart? = null

@Composable
fun AdminAnalyticsView(navController: NavController, auth: FirebaseAuth) {

    LazyColumn(content = {
        item {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround,
            ) {
                Text(text = adminAnalyticsTitle, style = MaterialTheme.typography.h3)
            }

            Button(onClick = {
                /*TODO*/
                navController.navigate(Screen.AdminAnalyticsMenuScreen.route)


            }) {
                Text(text = "Go Back to Admin Analytics")

            }
        }
        item {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .size(600.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
//                createAndUpdatePieChart()
                Crossfade(targetState = adminDistribution) { pieChartData ->
                    AndroidView(factory = { context ->
                        PieChart(context).apply {
                            layoutParams = LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT,
                            )
                            this.setDrawSliceText(false)
                            this.description.isEnabled = false
                            this.isDrawHoleEnabled = false
                            this.legend.isEnabled = true
                            this.legend.textSize = 25F
                            this.legend.horizontalAlignment =
                                Legend.LegendHorizontalAlignment.CENTER
                            this.legend.isWordWrapEnabled=true
                            this.setEntryLabelColor((resources.getColor(R.color.white)))
                            this.legend.form= Legend.LegendForm.CIRCLE
                            this.legend.formSize=20F
                        }
                    },
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(5.dp), update = {
                            updateAdminPieChartWithData(it, pieChartData)
                        })
                }
            }
        }
    }
    )
}

fun updateAdminPieChartWithData(
    chart: PieChart,
    data: List<PieChartData>
) {
    if(admin_pieChart == null){
        admin_pieChart = chart
    }
    val entries = ArrayList<PieEntry>()
    for (i in data.indices) {
        val item = data[i]
        entries.add(PieEntry(item.value ?: 0.toFloat(), item.Vote ?: ""))
    }

    val ds = PieDataSet(entries, "")


    val redColor = Color(0xFF03588c)

    var color_list = getNColors(entries.size)
    var list = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        color_list.stream().map { it.toArgb() }.toList()
    } else {
        TODO("VERSION.SDK_INT < N")
    }
    ds.colors = list

    ds.yValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE
    ds.xValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE
    ds.sliceSpace = 2f
    ds.valueTextColor = Color.White.toArgb()
    ds.valueTypeface = Typeface.DEFAULT_BOLD
    ds.valueTextSize = 18F
    val d = PieData(ds)
    chart.data = d
    chart.invalidate()
}

fun getNColors(needed_colors: Int): ArrayList<Color>{
    var outColors = ArrayList<Color>()
    var i = 0
    val color_arr = enumValues<StaticColor>()
    while(i<needed_colors) {
        var color = Color(color_arr[i].rgb)

        outColors.add(color)
        i += 1
    }
    return outColors
}

