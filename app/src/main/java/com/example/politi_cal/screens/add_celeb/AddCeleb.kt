package com.example.politi_cal.screens.add_celeb


import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddCelebScreen(navController: NavController, auth: FirebaseAuth){
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp))
    {
        Surface(
            modifier = Modifier
                .width(250.dp)
                .height(50.dp)
        ) {
            Text(text = "Add Celebrity", fontSize = 40.sp)
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
                var fname by remember { mutableStateOf("Enter the celeb first name") }
                OutlinedTextField(value = fname,
                    label = { Text(text = "First Name") },
                    onValueChange = { input -> fname = input },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "First Name"
                        )
                    })
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
                var lname by remember { mutableStateOf("Enter the celeb last name") }
                OutlinedTextField(value = lname,
                    label = { Text(text = "Last Name") },
                    onValueChange = { input -> lname = input },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "Last Name"
                        )
                    })
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
                var desc by remember { mutableStateOf("Enter the celeb description") }
                OutlinedTextField(value = desc,
                    label = { Text(text = "Description") },
                    onValueChange = { input -> desc = input },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = "Description"
                        )
                    })
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
                var img by remember { mutableStateOf("Enter the celeb image") }
                OutlinedTextField(value = img,
                    label = { Text(text = "Image URL") },
                    onValueChange = { input -> img = input },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Face,
                            contentDescription = "Image"
                        )
                    })
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
                horizontalArrangement = Arrangement.Center) {
                val categories = listOf<String>(
                    "Sport", "Journalism", "Academics",
                    "Culinary", "Entertainment", "Science"
                )
                var selection by remember { mutableStateOf(categories[0]) }
                var expanded by remember {
                    mutableStateOf(false)
                }
                ExposedDropdownMenuBox(expanded = expanded,
                    onExpandedChange = { expanded = !expanded })
                {
                    TextField(
                        readOnly = true,
                        value = selection,
                        onValueChange = { },
                        label = { Text(text = "Category") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors()
                    )
                    ExposedDropdownMenu(expanded = expanded,
                        onDismissRequest = { expanded = false }) {
                        categories.forEach { selected ->
                            DropdownMenuItem(onClick = {
                                selection = selected
                                expanded = false
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
                horizontalArrangement = Arrangement.Center) {
                val companies = listOf<String>(
                    "Maccabi Haifa soccer club", "Maccabi Tel Aviv Basketball club", "The Likud",
                    "N12 News", "Ariel University", "Mordechai from OS course"
                )
                var selectioncompany by remember { mutableStateOf(companies[0]) }
                var expanded by remember {
                    mutableStateOf(false)
                }
                ExposedDropdownMenuBox(expanded = expanded,
                    onExpandedChange = { expanded = !expanded })
                {
                    TextField(
                        readOnly = true,
                        value = selectioncompany,
                        onValueChange = { },
                        label = { Text(text = "Company") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors()
                    )
                    ExposedDropdownMenu(expanded = expanded,
                        onDismissRequest = { expanded = false }) {
                        companies.forEach { selected ->
                            DropdownMenuItem(onClick = {
                                selectioncompany = selected
                                expanded = false
                            })
                            {
                                Text(text = selected)
                            }
                        }
                    }
                }
            }
        }

        OutlinedButton(onClick = { /*TODO*/ },
            colors=ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White
            )) {
            Text("Submit")
        }

    }
}



