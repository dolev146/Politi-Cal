package com.example.politi_cal.screens.add_celeb


import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.politi_cal.MainActivity
import com.example.politi_cal.Screen
import com.example.politi_cal.celebCollectionRef
import com.example.politi_cal.common.dropDownMenu
import com.example.politi_cal.companiesForAddCelebNames
import com.example.politi_cal.models.Celeb
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


private  fun addCeleb(context : Context, celeb:Celeb , navController: NavController) = CoroutineScope(Dispatchers.IO).launch {
    try {
        val celebFullName = celeb.FirstName + " " + celeb.LastName
        celebCollectionRef.document(celebFullName).set(celeb).await()
        withContext(Dispatchers.Main) {
            Toast.makeText(context, "Celeb Added Successfully", Toast.LENGTH_SHORT).show()
            navController.navigate(Screen.SwipeScreen.route)
        }
    } catch (e: Exception) {
        Log.d(MainActivity.TAG, "Error adding user to database: $e")
        withContext(Dispatchers.Main) {
            Toast.makeText(
                context, "Error adding user to database: $e", Toast.LENGTH_SHORT
            ).show()
        }
    }
}



@Composable
fun AddCelebScreen(navController: NavController, auth: FirebaseAuth) {
    val context = LocalContext.current

    var listOfCompanies by remember {
        mutableStateOf(
            companiesForAddCelebNames
        )
    }


    val focusManager = LocalFocusManager.current
    // create variables to store the celeb info
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var imgUrl by remember { mutableStateOf("") }
    var celebInfo by remember { mutableStateOf("") }
    var company by remember { mutableStateOf("") }
//    var listOfCompanies by remember {
//        mutableStateOf(
//            listOf("")
//        )
//    }

    LazyColumn(content = {
        item {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                Text(text = "Add Celeb Screen", style = MaterialTheme.typography.h4)
            }


            OutlinedTextField(
                value = firstName,
                onValueChange = {
                    firstName = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                label = { Text(text = " First Name ") },
                placeholder = { Text(text = "Enter First Name") },
                textStyle = MaterialTheme.typography.body1,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface,
                    focusedIndicatorColor = MaterialTheme.colors.primary,
                    unfocusedIndicatorColor = MaterialTheme.colors.primary,
                    disabledIndicatorColor = MaterialTheme.colors.primary,
                    cursorColor = MaterialTheme.colors.primary,
                    textColor = MaterialTheme.colors.primary
                ),
            )

            OutlinedTextField(
                value = lastName,
                onValueChange = {
                    lastName = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                label = { Text(text = "Last Name") },
                placeholder = { Text(text = "Enter Last Name ") },
                textStyle = MaterialTheme.typography.body1,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface,
                    focusedIndicatorColor = MaterialTheme.colors.primary,
                    unfocusedIndicatorColor = MaterialTheme.colors.primary,
                    disabledIndicatorColor = MaterialTheme.colors.primary,
                    cursorColor = MaterialTheme.colors.primary,
                    textColor = MaterialTheme.colors.primary
                ),
            )

            OutlinedTextField(
                value = birthDate,
                onValueChange = {
                    birthDate = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                label = { Text(text = "Birth Date") },
                placeholder = { Text(text = "YYYYMMDD") },
                textStyle = MaterialTheme.typography.body1,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface,
                    focusedIndicatorColor = MaterialTheme.colors.primary,
                    unfocusedIndicatorColor = MaterialTheme.colors.primary,
                    disabledIndicatorColor = MaterialTheme.colors.primary,
                    cursorColor = MaterialTheme.colors.primary,
                    textColor = MaterialTheme.colors.primary
                ),
            )

            OutlinedTextField(
                value = imgUrl,
                onValueChange = {
                    imgUrl = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                label = { Text(text = "Image URL") },
                placeholder = { Text(text = "Enter Image URL") },
                textStyle = MaterialTheme.typography.body1,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface,
                    focusedIndicatorColor = MaterialTheme.colors.primary,
                    unfocusedIndicatorColor = MaterialTheme.colors.primary,
                    disabledIndicatorColor = MaterialTheme.colors.primary,
                    cursorColor = MaterialTheme.colors.primary,
                    textColor = MaterialTheme.colors.primary
                ),
            )

            OutlinedTextField(
                value = celebInfo,
                onValueChange = {
                    celebInfo = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                label = { Text(text = "Celeb Info") },
                placeholder = { Text(text = "Enter Celeb Info") },
                textStyle = MaterialTheme.typography.body1,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface,
                    focusedIndicatorColor = MaterialTheme.colors.primary,
                    unfocusedIndicatorColor = MaterialTheme.colors.primary,
                    disabledIndicatorColor = MaterialTheme.colors.primary,
                    cursorColor = MaterialTheme.colors.primary,
                    textColor = MaterialTheme.colors.primary
                ),
            )

            Card(
                modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp)
                    ,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color.Black)
            ) {
                Column() {
                    dropDownMenu(
                        list = listOfCompanies,
                        labeli = "Select Company",
                        onSelected = { company = it },
                    )
                    // add a button to add a new company
                    Button(
                        onClick = {
                            navController.navigate(Screen.AddNewCompanyScreen.route)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp , bottom = 20.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.primary,
                            contentColor = MaterialTheme.colors.onPrimary
                        ),

                    ) {
                        Text(text = "Add New Company ➡️")
                    }

                }

            }


            Button(
                onClick = {
//                    if (firstName.isNotEmpty() && lastName.isNotEmpty() && birthDate.isNotEmpty() && imgUrl.isNotEmpty() && celebInfo.isNotEmpty() && company.isNotEmpty()) {
                        var newCeleb = Celeb(
                            FirstName = firstName,
                            LastName = lastName,
                            BirthDate = birthDate.toLong(),
                            ImgUrl = imgUrl,
                            CelebInfo = celebInfo,
                            Company = company,
                        )
                        addCeleb(context,newCeleb,navController)
//                    } else {
//                        Toast.makeText(
//                            context,
//                            "Please fill all the fields",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 90.dp, end = 90.dp, top = 10.dp, bottom = 10.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.onPrimary
                ),
                enabled = firstName.isNotEmpty() && lastName.isNotEmpty() && birthDate.isNotEmpty() && imgUrl.isNotEmpty() && celebInfo.isNotEmpty() && company.isNotEmpty()

            ) {
                Text(text = "Add New Celeb")
            }


        }
    })


//    Column(modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.spacedBy(15.dp))
//    {
//        Surface(
//            modifier = Modifier
//                .width(250.dp)
//                .height(50.dp)
//        ) {
//            Text(text = "Add Celebrity", fontSize = 40.sp)
//        }
//        Surface(
//            modifier = Modifier
//                .width(450.dp)
//                .height(75.dp)
//        ) {
//            Row(
//                modifier = Modifier
//                    .height(450.dp)
//                    .width(75.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Center
//            ) {
//                var fname by remember { mutableStateOf("Enter the celeb first name") }
//                OutlinedTextField(value = fname,
//                    label = { Text(text = "First Name") },
//                    onValueChange = { input -> fname = input },
//                    leadingIcon = {
//                        Icon(
//                            imageVector = Icons.Filled.AccountCircle,
//                            contentDescription = "First Name"
//                        )
//                    })
//            }
//        }
//        Surface(
//            modifier = Modifier
//                .width(450.dp)
//                .height(75.dp)
//        ) {
//            Row(
//                modifier = Modifier
//                    .height(450.dp)
//                    .width(75.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Center
//            ) {
//                var lname by remember { mutableStateOf("Enter the celeb last name") }
//                OutlinedTextField(value = lname,
//                    label = { Text(text = "Last Name") },
//                    onValueChange = { input -> lname = input },
//                    leadingIcon = {
//                        Icon(
//                            imageVector = Icons.Filled.AccountCircle,
//                            contentDescription = "Last Name"
//                        )
//                    })
//            }
//        }
//        Surface(
//            modifier = Modifier
//                .width(450.dp)
//                .height(75.dp)
//        ) {
//            Row(
//                modifier = Modifier
//                    .height(450.dp)
//                    .width(75.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Center
//            ) {
//                var desc by remember { mutableStateOf("Enter the celeb description") }
//                OutlinedTextField(value = desc,
//                    label = { Text(text = "Description") },
//                    onValueChange = { input -> desc = input },
//                    leadingIcon = {
//                        Icon(
//                            imageVector = Icons.Filled.Info,
//                            contentDescription = "Description"
//                        )
//                    })
//            }
//        }
//
//        Surface(
//            modifier = Modifier
//                .width(450.dp)
//                .height(75.dp)
//        ) {
//            Row(
//                modifier = Modifier
//                    .height(450.dp)
//                    .width(75.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Center
//            ) {
//                var img by remember { mutableStateOf("Enter the celeb image") }
//                OutlinedTextField(value = img,
//                    label = { Text(text = "Image URL") },
//                    onValueChange = { input -> img = input },
//                    leadingIcon = {
//                        Icon(
//                            imageVector = Icons.Filled.Face,
//                            contentDescription = "Image"
//                        )
//                    })
//            }
//        }
//        Surface(
//            modifier = Modifier
//                .width(450.dp)
//                .height(75.dp)
//        ) {
//            Row(
//                modifier = Modifier
//                    .height(450.dp)
//                    .width(75.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Center) {
//                val categories = listOf<String>(
//                    "Sport", "Journalism", "Academics",
//                    "Culinary", "Entertainment", "Science"
//                )
//                var selection by remember { mutableStateOf(categories[0]) }
//                var expanded by remember {
//                    mutableStateOf(false)
//                }
//                ExposedDropdownMenuBox(expanded = expanded,
//                    onExpandedChange = { expanded = !expanded })
//                {
//                    TextField(
//                        readOnly = true,
//                        value = selection,
//                        onValueChange = { },
//                        label = { Text(text = "Category") },
//                        trailingIcon = {
//                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
//                        },
//                        colors = ExposedDropdownMenuDefaults.textFieldColors()
//                    )
//                    ExposedDropdownMenu(expanded = expanded,
//                        onDismissRequest = { expanded = false }) {
//                        categories.forEach { selected ->
//                            DropdownMenuItem(onClick = {
//                                selection = selected
//                                expanded = false
//                            })
//                            {
//                                Text(text = selected)
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        Surface(
//            modifier = Modifier
//                .width(450.dp)
//                .height(75.dp)
//        ) {
//            Row(
//                modifier = Modifier
//                    .height(450.dp)
//                    .width(75.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Center) {
//                val companies = listOf<String>(
//                    "Maccabi Haifa soccer club", "Maccabi Tel Aviv Basketball club", "The Likud",
//                    "N12 News", "Ariel University", "Mordechai from OS course"
//                )
//                var selectioncompany by remember { mutableStateOf(companies[0]) }
//                var expanded by remember {
//                    mutableStateOf(false)
//                }
//                ExposedDropdownMenuBox(expanded = expanded,
//                    onExpandedChange = { expanded = !expanded })
//                {
//                    TextField(
//                        readOnly = true,
//                        value = selectioncompany,
//                        onValueChange = { },
//                        label = { Text(text = "Company") },
//                        trailingIcon = {
//                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
//                        },
//                        colors = ExposedDropdownMenuDefaults.textFieldColors()
//                    )
//                    ExposedDropdownMenu(expanded = expanded,
//                        onDismissRequest = { expanded = false }) {
//                        companies.forEach { selected ->
//                            DropdownMenuItem(onClick = {
//                                selectioncompany = selected
//                                expanded = false
//                            })
//                            {
//                                Text(text = selected)
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        OutlinedButton(onClick = { /*TODO*/ },
//            colors=ButtonDefaults.buttonColors(
//                backgroundColor = MaterialTheme.colors.primary,
//                contentColor = Color.White
//            )) {
//            Text("Submit")
//        }
//
//    }
}



