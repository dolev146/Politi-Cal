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
import com.example.politi_cal.Screen
import com.google.firebase.auth.FirebaseAuth

//var piechart: PieChart? = null

@Composable
fun AdminAnalyticsMenuScreen(navController: NavController, auth: FirebaseAuth) {
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
                    navController.navigate(Screen.AdminAnalyticsViewScreen.route)

                }
            ) {
                Text(text = "Party Distribution")
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

