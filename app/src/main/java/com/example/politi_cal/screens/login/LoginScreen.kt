package com.example.politi_cal.screens.login


import android.content.Context
import android.nfc.Tag
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.politi_cal.MainActivity
import com.example.politi_cal.MainActivity.Companion.TAG
import com.example.politi_cal.R
import com.example.politi_cal.Screen

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen(navController: NavController, auth: FirebaseAuth) {

    // find the onstart

    val focusManager = LocalFocusManager.current

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

//    var isEmailValid by remember {
//        Pattern.EMAIL_ADDRESS.matcher(email).matches()
//    }

    val isPasswordValid by derivedStateOf {
        password.length >= 0
    }

    var isPasswordVisiable by remember {
        mutableStateOf(false)
    }



    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.app_logo),
            contentDescription = "logo",
            modifier = Modifier
                .padding(16.dp)
                .size(200.dp)
        )
        Text(
            text = "Login Screen",
            style = MaterialTheme.typography.h1,
            fontSize = 32.sp,
            modifier = Modifier.padding(8.dp)
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 8.dp
                ), shape = RoundedCornerShape(16.dp), border = BorderStroke(1.dp, Color.Black)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                    },
                    label = { Text(text = "Enter Email") },
                    placeholder = { Text(text = "Enter Email") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(FocusDirection.Down)

                    })
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    label = { Text(text = "Enter Password") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onNext = { focusManager.clearFocus() }),
                    isError = !isPasswordValid,
                    visualTransformation = if (isPasswordVisiable) VisualTransformation.None else PasswordVisualTransformation()
                )
                val context = LocalContext.current

                Button(
                    onClick = {
                        LoginUser(email, password, auth, navController, context)
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                        enabled = isPasswordValid && email.isNotEmpty()
                ) {
                    Text(text = "Login")
                }

            }

        }
        Button(
            onClick = {
                navController.navigate(Screen.RegisterScreen.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Red, contentColor = Color.White
            )
        ) {
            Text(text = "New User ? Register now ! ")
        }
    }
}


fun LoginUser(
    email: String,
    password: String,
    auth: FirebaseAuth,
    navController: NavController,
    context: Context
) {

    if (email.isEmpty() || password.isEmpty()) {
        Toast.makeText(
            context, "Please enter email and password", Toast.LENGTH_SHORT
        ).show()
    } else {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.w(TAG, email.toString())
                Log.w(TAG, password.toString())
                val auth2 = FirebaseAuth.getInstance()
                auth2.signInWithEmailAndPassword(email, password).await()
                withContext(context = Dispatchers.Main) {
                    Toast.makeText(
                        context, "Login Success", Toast.LENGTH_SHORT
                    ).show()
                    navController.navigate(Screen.SwipeScreen.route)
                }
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
                withContext(context = Dispatchers.Main) {
                    Toast.makeText(
                        context, e.message, Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}



