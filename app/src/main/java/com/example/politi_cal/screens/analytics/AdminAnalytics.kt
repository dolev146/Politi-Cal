package com.example.politi_cal.screens.analytics

import android.graphics.Typeface
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.politi_cal.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.firebase.auth.FirebaseAuth
import java.util.*



@Composable
fun AdminAnalyticsScreen(navController: NavController, auth: FirebaseAuth) {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Distribution",
                style = androidx.compose.ui.text.TextStyle.Default,
                fontFamily = FontFamily.Default,
                fontStyle = FontStyle.Normal,
                fontSize = 36.sp
            )
            Column(
                modifier = Modifier
                    .padding(18.dp)
                    .size(320.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Crossfade(targetState = getPieChartData) { pieChartData ->
                    AndroidView(factory = { context ->
                        PieChart(context).apply {
                            layoutParams = LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT,
                            )
                            this.description.isEnabled = false
                            this.isDrawHoleEnabled = false
                            this.legend.isEnabled = true
                            this.legend.textSize = 40F
                            this.legend.horizontalAlignment =
                                Legend.LegendHorizontalAlignment.CENTER
                            this.setEntryLabelColor((resources.getColor(R.color.white)))
                        }
                    },
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(5.dp), update = {
                            updatePieChartWithData(it, pieChartData)
                        })
                }
            }
            createDropMenus()
        }
    }
    
}


@Composable
@OptIn(ExperimentalMaterialApi::class)
fun createDropMenus() {
    var comp_enabled by remember {
        mutableStateOf(true)
    }

    var cat_enabled by remember {
        mutableStateOf(true)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    )
    {
        Surface(
            modifier = Modifier
                .width(450.dp)
                .height(75.dp)
        ) {
            Row(
                modifier = Modifier
                    .height(450.dp)
                    .width(75.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                val categories = listOf<String>(
                    "Sport", "Journalism", "Academics",
                    "Culinary", "Entertainment", "Science"
                )
                var selection by remember { mutableStateOf(categories[0]) }
                var expanded by remember {
                    mutableStateOf(false)
                }
                Switch(checked = cat_enabled,
                    onCheckedChange = {
                        cat_enabled=!cat_enabled })
                ExposedDropdownMenuBox(expanded = expanded,
                    onExpandedChange = { expanded = !expanded && cat_enabled })
                {
                    TextField(
                        readOnly = true,
                        enabled=cat_enabled,
                        value = selection,
                        onValueChange = { },
                        label = { Text(text = "Category") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors()
                    )
                    ExposedDropdownMenu(expanded = expanded,
                        onDismissRequest = { expanded = false }) {
                        categories.forEach { selected ->
                            DropdownMenuItem(onClick = {
                                selection = selected
                                expanded = false
                            })
                            {
                                Text(text = selected)
                            }
                        }
                    }
                }
            }
        }

        Surface(
            modifier = Modifier
                .width(450.dp)
                .height(75.dp)
        ) {
            Row(
                modifier = Modifier
                    .height(450.dp)
                    .width(75.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                val companies = listOf<String>(
                    "All", "Maccabi Haifa soccer club", "Maccabi Tel Aviv Basketball club", "The Likud",
                    "N12 News", "Ariel University", "Mordechai from OS course"
                )
                var selectioncompany by remember { mutableStateOf(companies[0]) }
                var expanded by remember {
                    mutableStateOf(false)
                }
                Switch(checked = comp_enabled && cat_enabled,
                    onCheckedChange = {comp_enabled=!comp_enabled && cat_enabled})
                ExposedDropdownMenuBox(expanded = expanded,
                    onExpandedChange = { expanded = !expanded && comp_enabled &&cat_enabled })
                {
                    TextField(
                        readOnly = true,
                        enabled = comp_enabled && cat_enabled,
                        value = selectioncompany,
                        onValueChange = { },
                        label = { Text(text = "Company") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors()
                    )
                    ExposedDropdownMenu(expanded = expanded,
                        onDismissRequest = { expanded = false }) {
                        companies.forEach { selected ->
                            DropdownMenuItem(onClick = {
                                selectioncompany = selected
                                expanded = false
                            })
                            {
                                Text(text = selected)
                            }
                        }
                    }
                }
            }
        }
        OutlinedButton(onClick = { /*TODO*/ },
            colors= ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White
            )) {
            Text("Apply")
        }
    }
}


fun updatePieChartWithData(
    chart: PieChart,
    data: List<PieChartData>
) {
    val entries = ArrayList<PieEntry>()
    for (i in data.indices) {
        val item = data[i]
        entries.add(PieEntry(item.value ?: 0.toFloat(), item.Vote ?: ""))
    }

    val ds = PieDataSet(entries, "")

    val redColor = Color(0xFFF44336)
    val blueColor = Color(0xFF3291F3)

    ds.colors = arrayListOf(
        redColor.toArgb(),
        blueColor.toArgb(),
    )

    ds.yValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE
    ds.xValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE
    ds.sliceSpace = 2f
    ds.valueTextColor = Color.White.toArgb()
    ds.valueTypeface = Typeface.DEFAULT_BOLD
    ds.setValueTextSize(18F)
    val d = PieData(ds)
    chart.data = d
    chart.invalidate()
}
