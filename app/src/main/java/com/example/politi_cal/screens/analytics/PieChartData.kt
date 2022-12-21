package com.example.politi_cal.screens.analytics

data class PieChartData(
    var Vote:  String?,
    var value: Float?
)

val getPieChartData = listOf(
    PieChartData("Left", 45.6F),
    PieChartData("Right", 54.4F)
)

