package com.example.politi_cal.screens.AdminUserManagment

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.politi_cal.Screen
import com.example.politi_cal.UserForUserProfile
import com.example.politi_cal.data.queries_Interfaces.CelebSearchDB
import com.example.politi_cal.models.CallBack
import com.example.politi_cal.models.User
import com.google.firebase.auth.FirebaseAuth


@Composable
fun AdminUserManagement(navController: NavController, auth: FirebaseAuth) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    var search by remember {
        mutableStateOf("")
    }

    // list of celebs
    var usersearchList by remember {
        mutableStateOf(mutableStateListOf<User>())
    }


    LazyColumn(content = {
        item {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                Text(text = "Search User", style = MaterialTheme.typography.h4)

                OutlinedTextField(
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search, contentDescription = "Search Icon"
                        )
                    },
                    value = search,
                    onValueChange = {
                        search = it

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
                    label = { Text(text = " Search User Email ") },
                    placeholder = { Text(text = "Enter User Email ") },
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

                Button(onClick = {
                    var callback = CallBack<String, MutableList<User>>(search)
                    val searchdb = CelebSearchDB()
                    searchdb.getUsersByEmail(callback)
                    while (!callback.getStatus()) {
                        continue
                    }
                    if (callback.getOutput() != null) {
                        with(usersearchList) {
                            clear()
                            addAll(callback.getOutput()!!)
                        }
                    } else {
                        Toast.makeText(context, "User not found", Toast.LENGTH_LONG).show()
                    }

                }) {
                    Text(text = "Search")
                }
            }
        }

        itemsIndexed(usersearchList) { index, item ->
            if (usersearchList.isEmpty()) {
                println("no search results")
            } else {
                val userie = item
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp),
                    elevation = 8.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.medium),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = userie.email,
                            style = MaterialTheme.typography.h6
                        )
                        Button(
                            onClick = {
                                UserForUserProfile = userie
                                navController.navigate(Screen.UserProfileScreen.route)
                            }, colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.White, contentColor = Color.Gray
                            )
                        ) {
                            Text(text = "View Profile")
                        }
                    }
                }


            }
        }
    })
}



