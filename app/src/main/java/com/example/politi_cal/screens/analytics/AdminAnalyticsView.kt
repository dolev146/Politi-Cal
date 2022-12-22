package com.example.politi_cal.screens.analytics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
                Text(text = "Admin Analytics", style = MaterialTheme.typography.h1)

                Button(onClick = {
                    /*TODO*/
                    navController.navigate(Screen.AdminAnalyticsScreen.route)


                }) {
                    Text(text = "Go Back to Admin Analytics")

                }
            }

        }

    })




}