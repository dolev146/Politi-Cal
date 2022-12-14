package com.example.politi_cal.screens.admin_screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AdminOnlyScreen(navController: NavController, auth: FirebaseAuth) {
    Text(text = "Admin Only Screen")

}