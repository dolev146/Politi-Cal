package com.example.politi_cal.screens.analytics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

//var piechart: PieChart? = null

@Composable
fun AdminAnalyticsScreen(navController: NavController, auth: FirebaseAuth) {
    LazyColumn(content = {
        item {
            Column(modifier =  Modifier.fillMaxSize().padding(10.dp) ) {


            Text(
                text = "Admin Analytics",
                style = MaterialTheme.typography.h4
            )


            Button(onClick = {
                // TODO: Add analytics

                }
            ) {
                Text(text = "Party Distribution")
            }

            Button(onClick = {
                // TODO: Add analytics

            }
            ) {
                Text(text = "age group distribution")
            }

            Button(onClick = {
                // TODO: Add analytics
            }
            ) {
                Text(text = "age group distribution")
            }

            Button(onClick = {
                    // TODO show number of users
            }
            ) {
                Text(text = "Number Of Users")
            }

            Button(onClick = {
                    // TODO show number of users
            }
            ) {
                Text(text = "Number Of Users In Year ")
            }


            Button(onClick = {
                // TODO show number of users
            }
            ) {
                Text(text = "users registered in year distributed by month ")
            }

            Button(onClick = {
                // TODO show number of users
            }
            ) {
                Text(text = " ")
            }



            }




        }
    })




}

