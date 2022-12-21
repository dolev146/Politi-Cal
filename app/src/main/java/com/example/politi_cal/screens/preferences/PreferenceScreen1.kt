package com.example.politi_cal.screens.preferences


import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.politi_cal.*
import com.example.politi_cal.MainActivity.Companion.TAG
import com.example.politi_cal.common.dropDownMenu
import com.example.politi_cal.models.CallBack
import com.example.politi_cal.models.Celeb
import com.example.politi_cal.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@Composable
fun PreferenceScreen1(navController: NavController, auth: FirebaseAuth) {
    var gender = listOf<String>("Male", "Female", "Prefers not to say", "Other")
    var selectedGender by remember { mutableStateOf("this is the first ") }
    var age = listOf<String>(
        "18-25", "26-32", "33-40", "41-50", "51-60", "61-70", "71-80", "More then 81"
    )
    var selectedAge by remember { mutableStateOf("") }
    var party = listOf<String>(
        "Likud",
        "Yesh atid",
        "Blue and White",
        "Shas",
        "United Torah Judaism",
        "Yisrael Beiteinu",
        "Meretz",
        "Ra'am",
        "Hadash",
        "Balad",
        "Kulanu",
        "Yamina",
        "Zehut",
        "Other"
    )
    var selectedParty by remember { mutableStateOf("") }


    val checkStateSports = remember { mutableStateOf(false) }
    val checkStateJournalism = remember { mutableStateOf(false) }
    val checkStatePolitics = remember { mutableStateOf(false) }
    val checkStateFamous = remember { mutableStateOf(false) }
    val checkStateAcademic = remember { mutableStateOf(false) }






    LazyColumn(content = {
        item {

            // add h1 to the top of the page
            Text(
                text = "Preferences",
                modifier = Modifier.padding(20.dp),
                style = MaterialTheme.typography.h4
            )
            dropDownMenu(list = gender,
                labeli = "Select gender",
                onSelected = { selectedGender = it })
            dropDownMenu(list = age, labeli = "Select age", onSelected = { selectedAge = it })
            dropDownMenu(list = party, labeli = "Select party", onSelected = { selectedParty = it })
            Text(
                text = "Select your interests",
                modifier = Modifier.padding(5.dp),
                style = MaterialTheme.typography.h5
            )
            Column() {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {


                    Column() {
                        Text(
                            text = "Sports", modifier = Modifier.padding(5.dp)
                        )
                        Checkbox(checked = checkStateSports.value,
                            onCheckedChange = { checkStateSports.value = it })
                    }

                    Column() {
                        Text(
                            text = "Journalism", modifier = Modifier.padding(5.dp)
                        )
                        Checkbox(checked = checkStateJournalism.value,
                            onCheckedChange = { checkStateJournalism.value = it })
                    }

                    Column() {
                        Text(
                            text = "Politics", modifier = Modifier.padding(5.dp)
                        )
                        Checkbox(checked = checkStatePolitics.value,
                            onCheckedChange = { checkStatePolitics.value = it })
                    }

                    Column() {
                        Text(
                            text = "Famous", modifier = Modifier.padding(5.dp)
                        )
                        Checkbox(checked = checkStateFamous.value,
                            onCheckedChange = { checkStateFamous.value = it })
                    }

                    Column() {
                        Text(
                            text = "Academic", modifier = Modifier.padding(5.dp)
                        )
                        Checkbox(checked = checkStateAcademic.value,
                            onCheckedChange = { checkStateAcademic.value = it })
                    }


                }
            }


            val context = LocalContext.current
            // blue submit button with round edges on the center
            Button(
                onClick = {
                    // collect the checkbox values to list
                    val interests = mutableListOf<String>()
                    if (checkStateSports.value) {
                        interests.add("Sports")
                    }
                    if (checkStateJournalism.value) {
                        interests.add("Journalism")
                    }
                    if (checkStatePolitics.value) {
                        interests.add("Politics")
                    }
                    if (checkStateFamous.value) {
                        interests.add("Famous")
                    }
                    if (checkStateAcademic.value) {
                        interests.add("Academic")
                    }


                    // select the current year
                    val currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
                    val currentMonth =
                        java.util.Calendar.getInstance().get(java.util.Calendar.MONTH)
                    val currentDay =
                        java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH)
                    val stringDate = "$currentYear$currentMonth$currentDay"
                    val userClass = User(
                        email = auth.currentUser?.email.toString(),
                        favoritePartyID = selectedParty,
                        userName = auth.currentUser?.displayName.toString(),
                        registerDate = stringDate.toLong(),
                        userPref = interests,
                        userID = auth.currentUser?.uid.toString(),
                        userAge = selectedAge,
                        userGender = selectedGender,
                    )

                    // add the user to the database
                    editUser(auth, userClass, context, navController)





                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                enabled = selectedGender != "" && selectedAge != "" && selectedParty != "" && (checkStateSports.value || checkStateJournalism.value || checkStatePolitics.value || checkStateFamous.value || checkStateAcademic.value)
            ) {
                Text(text = "Submit")
            }

        }
    })


}


//private fun deleteDataUser(auth: FirebaseAuth) = CoroutineScope(Dispatchers.IO).launch {
//    val emailFilterDoc =
//        userCollectionRef.whereEqualTo("email", auth.currentUser?.email.toString()).get().await()
//    if (emailFilterDoc.documents.isNotEmpty()) {
//        for (doc in emailFilterDoc) {
//            try {
//                userCollectionRef.document(doc.id).delete().await()
//            } catch (e: Exception) {
//                withContext(Dispatchers.Main) {
//                    Toast.makeText(MainActivity(), "Error deleting user data", Toast.LENGTH_SHORT)
//                        .show()
//                }
//            }
//
//        }
//    }
//}

private  fun editUser(auth : FirebaseAuth, userClass : User, context : Context, navController: NavController) = CoroutineScope(Dispatchers.IO).launch {
    try {
        userCollectionRef.document(auth.currentUser?.email.toString()).set(userClass, SetOptions.merge()).await()
        withContext(Dispatchers.Main) {
            Toast.makeText(context, "User updated", Toast.LENGTH_SHORT).show()
            celebListParam.clear()
            var callBack = CallBack<Boolean,MutableList<Celeb>>(false)
            retrieveCelebsByUserOfri(callBack)
            while (!callBack.getStatus()) {
                continue
            }
            val working = callBack.getOutput()
            if (working != null) {
                celebListParam = working
                celebListParam.shuffle()
            }
            navController.navigate(Screen.SwipeScreen.route)
        }
    } catch (e: Exception) {
        Log.d(TAG, "Error updated user to database: $e")
        withContext(Dispatchers.Main) {
            Toast.makeText(
                context, "Error updated user to database: $e", Toast.LENGTH_SHORT
            ).show()
        }
    }
}

