package com.example.politi_cal.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.politi_cal.Screen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SearchScreen(navController: NavController, auth: FirebaseAuth) {
    val focusManager = LocalFocusManager.current
    var search by remember {
        mutableStateOf("")
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
                Text(text = "Search Screen", style = MaterialTheme.typography.h4)

                OutlinedTextField(
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon"
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
                    label = { Text(text = " Search Celeb ") },
                    placeholder = { Text(text = "Enter Celeb Name") },
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

                Button(onClick =
                {
                    //Todo//

                    navController
                        .navigate(
                            Screen.CelebProfileScreen.route

                        )

                }) {
                    Text(text = "Search")

                }


            }

        }
    })
}