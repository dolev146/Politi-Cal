package com.example.politi_cal.screens.preferences


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.politi_cal.MainActivity.Companion.TAG
import com.example.politi_cal.Screen
import com.example.politi_cal.common.dropDownMenu
import com.example.politi_cal.models.User
import com.example.politi_cal.userCollectionRef
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@Composable
fun PreferenceScreen(navController: NavController, auth: FirebaseAuth) {
    var gender = listOf<String>("Male" , "Female" , "Prefers not to say", "Other")
    var selectedGender by remember { mutableStateOf("this is the first ") }
    var age = listOf<String>("18-25", "26-32", "33-40", "41-50", "51-60", "61-70", "71-80", "More then 81")
    var selectedAge by remember { mutableStateOf(" this is the second ") }

    Column(modifier = Modifier.fillMaxWidth()) {
        // add h1 to the top of the page
        Text(text = "Preferences" , modifier = Modifier.padding(20.dp) , style = MaterialTheme.typography.h4)
        dropDownMenu(list = gender, labeli = "Select gender", onSelected = { selectedGender = it })
        dropDownMenu(list = age, labeli = "Select age" , onSelected = { selectedAge = it })
        Text(text = selectedGender)
        Text(text = selectedAge)

        val context = LocalContext.current
        // blue submit button with round edges on the center
        Button(onClick = {
            val userClass = User(
                email = auth.currentUser?.email.toString(),
                password = "not relevant",
                favoritePartyID = "Likud",
                userName = "BIBI",
                registerDate = System.currentTimeMillis(),
                userPref = listOf("Economy", "Healthcare", "Education")
            )
            // add the user to the database
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    userCollectionRef.add(userClass).await()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "User added successfully", Toast.LENGTH_SHORT).show()
                    }
                    Log.d(TAG, "User added to database")

                } catch (e: Exception) {
                    Log.d(TAG, "Error adding user to database: $e")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            context,
                            "Error adding user to database: $e",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }


            navController.navigate(Screen.SwipeScreen.route)

        }, modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()) {
            Text(text = "Submit")
        }
    }
}