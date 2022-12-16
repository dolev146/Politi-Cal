package com.example.politi_cal.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.politi_cal.MainActivity
import com.example.politi_cal.Screen
import com.example.politi_cal.categoriesForAddCelebNames
import com.example.politi_cal.common.dropDownMenu
import com.example.politi_cal.companyCollectionRef
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


private fun addCompanyToDB(
    context: Context, company: String, category: String, navController: NavController
) = CoroutineScope(
    Dispatchers.IO
).launch {
    try {
//        val celebFullName = celeb.FirstName + " " + celeb.LastName
//        celebCollectionRef.document(celebFullName).set(celeb).await()
        companyCollectionRef.document(company).set(hashMapOf("category" to category))

        withContext(Dispatchers.Main) {
            Toast.makeText(context, "Company Added !", Toast.LENGTH_SHORT).show()
            navController.navigate(Screen.AddCelebScreen.route)
        }
    } catch (e: Exception) {
        Log.d(MainActivity.TAG, "Error adding Company to database: $e")
        withContext(Dispatchers.Main) {
            Toast.makeText(
                context, "Error adding Company to database: $e", Toast.LENGTH_SHORT
            ).show()
        }
    }
}


@Composable
fun AddNewCompanyScreen(navController: NavController, auth: FirebaseAuth) {
    val focusManager = LocalFocusManager.current

    var company by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Add New Company Screen",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.h5
        )

        dropDownMenu(list = categoriesForAddCelebNames,
            labeli = "Select category",
            onSelected = { category = it })

        OutlinedTextField(value = company,
            onValueChange = { company = it },
            label = { Text(text = "Company") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = { focusManager.clearFocus() }),

            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary,
                unfocusedBorderColor = MaterialTheme.colors.onSurface
            ),
            singleLine = false
        )

        val context = LocalContext.current

        // submit button
        Button(modifier = Modifier.padding(16.dp),

            onClick = {

                addCompanyToDB(context, company, category, navController)



            }) {
            Text(text = "Submit")

        }


    }


}