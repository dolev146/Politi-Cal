package com.example.politi_cal.screens

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun UserProfileScreen(navController: NavController, auth: FirebaseAuth) {
    Text(text = "User Profile Screen", style = MaterialTheme.typography.h4)
}