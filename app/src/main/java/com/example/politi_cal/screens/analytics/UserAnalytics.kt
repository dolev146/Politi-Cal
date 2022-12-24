package com.example.politi_cal.screens.analytics

import android.graphics.Typeface
import android.os.Build
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.politi_cal.*
import com.example.politi_cal.DBObjects.AnalyticsQueriesObj
import com.example.politi_cal.R
import com.example.politi_cal.models.CallBack
import com.example.politi_cal.models.Category
import com.example.politi_cal.models.Company
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.streams.toList

var piechart: PieChart? = null

@Composable
fun UserAnalyticsScreen(navController: NavController, auth: FirebaseAuth) {
    distribution.clear()
    LazyColumn(content = {
        item {
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
                }
                Column(
                    modifier = Modifier
                        .padding(18.dp)
                        .size(320.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
//                createAndUpdatePieChart()
                    Crossfade(targetState = distribution) { pieChartData ->
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
                var callback_category = CallBack<Boolean, Boolean>(false)
                var callback_company = CallBack<Boolean, Boolean>(false)
                categoriesForAddCelebNames.clear()
                companiesForAddCeleb.clear()
                retrieveCategories(callback_category)
                retrieveCompanies(callback_company)
                var flag = 0
                while (true) {
                    if (callback_category.getStatus()) {
                        flag += 1
                    }
                    if (callback_company.getStatus()) {
                        flag += 1
                    }
                    if (flag == 2) {
                        break
                    } else {
                        flag = 0
                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    createDropMenus()
                }
            }
        }
    })
}


@RequiresApi(Build.VERSION_CODES.N)
@Composable
@OptIn(ExperimentalMaterialApi::class)
fun createDropMenus() {
    var comp_enabled by remember {
        mutableStateOf(true)
    }

    var cat_enabled by remember {
        mutableStateOf(true)
    }
    val categories = categoriesForAddCelebNames
    var selection by remember { mutableStateOf(categories[0]) }
    var category_expanded by remember {
        mutableStateOf(false)
    }

    var companies = companiesForAddCeleb.stream()
        .filter { it.category.equals(selection) }
        .map { it.companyID }
        .toList().toMutableList()
    var selectioncompany by remember { mutableStateOf(companies[0]) }
    var company_expanded by remember {
        mutableStateOf(false)
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
                Switch(checked = cat_enabled,
                    onCheckedChange = {
                        cat_enabled = !cat_enabled
                    })
                ExposedDropdownMenuBox(expanded = category_expanded,
                    onExpandedChange = { category_expanded = !category_expanded && cat_enabled })
                {
                    TextField(
                        readOnly = true,
                        enabled = cat_enabled,
                        value = selection,
                        onValueChange = { },
                        label = { Text(text = "Category") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = category_expanded)
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors()
                    )
                    ExposedDropdownMenu(expanded = category_expanded,
                        onDismissRequest = { category_expanded = false }) {
                        categories.forEach { selected ->
                            DropdownMenuItem(onClick = {
                                selection = selected
                                companies.clear()
                                companies = companiesForAddCeleb.stream()
                                    .filter { it.category.equals(selection) }
                                    .map { it.companyID }
                                    .toList() as ArrayList<String>
                                if(companies.isEmpty()){
                                    selectioncompany = ""
                                }
                                else{
                                    selectioncompany = companies[0]
                                }
                                category_expanded = false
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

                Switch(checked = comp_enabled && cat_enabled,
                    onCheckedChange = { comp_enabled = !comp_enabled && cat_enabled })
                ExposedDropdownMenuBox(expanded = company_expanded,
                    onExpandedChange = {
                        company_expanded = !company_expanded && comp_enabled && cat_enabled
                    })
                {
                    TextField(
                        readOnly = true,
                        enabled = comp_enabled && cat_enabled,
                        value = selectioncompany,
                        onValueChange = { },
                        label = { Text(text = "Company") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = company_expanded)
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors()
                    )
                    ExposedDropdownMenu(expanded = company_expanded,
                        onDismissRequest = { company_expanded = false }) {
                        companies.forEach { selected ->
                            DropdownMenuItem(onClick = {
                                selectioncompany = selected
                                company_expanded = false
                            })
                            {
                                Text(text = selected)
                            }
                        }
                    }
                }
            }
        }
        OutlinedButton(
            onClick =  {
                val analyticsCreator = AnalyticsQueriesObj()
                // company distribution
                if(cat_enabled && comp_enabled) {
                    var company = Company(selectioncompany.toString(), selection.toString())
                    var callback = CallBack<Company, Map<String, Double>>(company)
                    analyticsCreator.getCompanyDistribution(callback)
                    while (!callback.getStatus()){
                        continue
                    }
                    val data = callback!!.getOutput()
                    if(data!=null) {
                        val left_distribution = data!!["Left"]!! * 100
                        val right_distribution = data!!["Right"]!! * 100
                        distribution.clear()
                        distribution.add(PieChartData("Left", left_distribution!!.toFloat()))
                        distribution.add(PieChartData("Right", right_distribution!!.toFloat()))
                    }
                }
                // category distribution
                else if(cat_enabled && !comp_enabled){
                    var category = Category(selection.toString(), selection.toString())
                    var callback = CallBack<Category, Map<String, Double>>(category)
                    analyticsCreator.getCategoryStatistics(callback)
                    while (!callback.getStatus()){
                        continue
                    }
                    val data = callback.getOutput()
                    if(data!=null) {
                        val left_distribution = data!!["Left"]!! * 100
                        val right_distribution = data!!["Right"]!! * 100
                        distribution.clear()
                        distribution.add(PieChartData("Left", left_distribution!!.toFloat()))
                        distribution.add(PieChartData("Right", right_distribution!!.toFloat()))
                    }
                }
                //total distribution
                else{
                    var callback = CallBack<Int, Map<String, Double>>(0)
                    analyticsCreator.getTotalDistribution(callback)
                    while (!callback.getStatus()){
                        continue
                    }
                    val data = callback!!.getOutput()
                    if(data!=null) {
                        val left_distribution = data!!["Left"]!! * 100
                        val right_distribution = data!!["Right"]!! * 100
                        distribution.clear()
                        distribution.add(PieChartData("Left", left_distribution!!.toFloat()))
                        distribution.add(PieChartData("Right", right_distribution!!.toFloat()))
                    }
                }

                CoroutineScope(Dispatchers.IO).launch {
                    updatePieChartWithData(piechart!!, distribution)
                }
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White
            )
        ) {
            Text("Apply")
        }
    }
}


//@Composable
fun updatePieChartWithData(
    chart: PieChart,
    data: List<PieChartData>
) {
    piechart = chart
    val entries = ArrayList<PieEntry>()
    for (i in data.indices) {
        val item = data[i]
        entries.add(PieEntry(item.value ?: 0.toFloat(), item.Vote ?: ""))
    }

    val ds = PieDataSet(entries, "")

    val redColor = Color(0xFF03588c)
    val blueColor = Color(0xFFa60321)

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