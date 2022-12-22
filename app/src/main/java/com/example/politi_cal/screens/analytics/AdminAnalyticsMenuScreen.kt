package com.example.politi_cal.screens.analytics

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.politi_cal.DBObjects.AdminAnalyticsQueriesDBObj
import com.example.politi_cal.Screen
import com.example.politi_cal.adminAnalyticsTitle
import com.example.politi_cal.adminDistribution
import com.example.politi_cal.models.CallBack
import com.google.firebase.auth.FirebaseAuth

//var piechart: PieChart? = null

@Composable
fun AdminAnalyticsMenuScreen(navController: NavController, auth: FirebaseAuth) {
    val analyticsCreator = AdminAnalyticsQueriesDBObj()
    LazyColumn(content = {
        item {
            Column(modifier =  Modifier.fillMaxSize().padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround,




            ) {


            Text(
                text = "Admin Analytics",
                style = MaterialTheme.typography.h4
            )


            Button(
                modifier = Modifier.padding(10.dp).fillMaxWidth(),

                onClick = {
                // TODO: Add analytics
                    var callback = CallBack<Boolean, Map<String, Double>>(true)
                    analyticsCreator.getPartiesDistribution(callback)
                    while(!callback.getStatus()){
                        continue
                    }
                    val output = callback.getOutput()
                    adminDistribution.clear()
                    for(entry in output!!.entries){
                        val partyName = entry.key
                        val precent = entry.value.toFloat() *100
                        adminDistribution.add(PieChartData(partyName, precent))
                    }
                    adminAnalyticsTitle = "Favorite parties distribution"
                    navController.navigate(Screen.AdminAnalyticsViewScreen.route)
                }
            ) {
                Text(text = "Party Distribution")
            }

            Button(
                modifier = Modifier.padding(10.dp).fillMaxWidth(),
                onClick = {
                // TODO: Add analytics
                    var callback = CallBack<Boolean, Map<String, Double>>(true)
                    analyticsCreator.getAgeDistribution(callback)
                    while(!callback.getStatus()){
                        continue
                    }
                    val output = callback.getOutput()
                    adminDistribution.clear()
                    for(entry in output!!.entries){
                        val partyName = entry.key
                        val precent = entry.value.toFloat() *100
                        adminDistribution.add(PieChartData(partyName, precent))
                    }
                    adminAnalyticsTitle = "Age group distribution"
                    navController.navigate(Screen.AdminAnalyticsViewScreen.route)

            }
            ) {
                Text(text = "age group distribution")
            }

            Button(
                modifier = Modifier.padding(10.dp).fillMaxWidth(),
                onClick = {
                // TODO: Add analytics
            }
            ) {
                Text(text = "age group distribution")
            }

            Button(
                modifier = Modifier.padding(10.dp).fillMaxWidth(),
                onClick = {
                    // TODO show number of users
            }
            ) {
                Text(text = "Number Of Users")
            }

            Button(
                modifier = Modifier.padding(10.dp).fillMaxWidth(),
                onClick = {
                    // TODO show number of users
            }
            ) {
                Text(text = "Number Of Users In Year ")
            }


            Button(
                modifier = Modifier.padding(10.dp).fillMaxWidth(),
                onClick = {
                // TODO show number of users
            }
            ) {
                Text(text = "users registered in year distributed by month ")
            }





            }




        }
    })




}

