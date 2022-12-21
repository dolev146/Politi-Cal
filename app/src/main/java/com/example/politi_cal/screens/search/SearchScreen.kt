package com.example.politi_cal.screens.search

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
import androidx.compose.runtime.snapshots.SnapshotStateList
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
import com.example.politi_cal.CelebForCelebProfile
import com.example.politi_cal.MainActivity
import com.example.politi_cal.Screen
import com.example.politi_cal.data.queries_Interfaces.CelebSearchDB
import com.example.politi_cal.models.CallBack
import com.example.politi_cal.models.Celeb
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SearchScreen(navController: NavController, auth: FirebaseAuth) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    var search by remember {
        mutableStateOf("")
    }

    // list of celebs
    var celebsSearchList by remember {
        mutableStateOf(mutableStateListOf<Celeb>())
    }



    var celebExample = Celeb(
        Company = "no company",
        FirstName = "no more swipes",
        LastName = "finished",
        BirthDate = 0,
        ImgUrl = "https://user-images.githubusercontent.com/62290677/208495339-a1f0a482-878f-4f88-ad88-1eef3bfe36c4.png",
        CelebInfo = "text",
        Category = "text",
        RightVotes = 0,
        LeftVotes = 0
    )






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

                    if (search == "") {
                        Toast.makeText(context, "Please enter a name", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        var callback = CallBack<String, MutableList<Celeb>>(search)
                        val searchdb = CelebSearchDB()
                        searchdb.getCelebByName(callback)
                        while (!callback.getStatus()) {
                            continue
                        }
                        if (callback.getOutput() != null) {
                            with(celebsSearchList) {
                                clear()
                                addAll(callback.getOutput()!!)
                            }
//                            celebsSearchList.clear()
//                            celebsSearchList.addAll(callback.getOutput()!!)

                        } else {
                            Toast.makeText(context, "Celeb not found", Toast.LENGTH_LONG).show()
                        }
                    }
                }) {
                    Text(text = "Search")

                }


            }
           // celebListComp(celebsSearchList = celebsSearchList, navController = navController)

        }
        itemsIndexed(celebsSearchList) { index, item ->
            if (celebsSearchList.isEmpty()) {
                     println("no celebs")
            } else {
            val celebie = item
         //   celebsSearchList.forEach { celebie ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp),
                        elevation = 8.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(MaterialTheme.shapes.medium)
                        ,horizontalArrangement = Arrangement.SpaceAround
                        , verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = celebie.FirstName + " " + celebie.LastName,
                                style = MaterialTheme.typography.h6
                            )
                            Button(onClick = {
                                CelebForCelebProfile = celebie
                                navController.navigate(Screen.CelebProfileScreen.route)
                            }, colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.White, contentColor = Color.Gray
                                )) {
                                Text(text = "View Profile")
                            }
                        }
                    }



            }
        }



    })
}

//@Composable
//fun celebListComp(celebsSearchList: MutableList<Celeb> , navController: NavController) {
//    LazyColumn(content = {
//        items(celebsSearchList.size) { index ->
//            celebComp(celebsSearchList[index])
//        }
//    })
//
//}
//
//@Composable
//fun celebComp(celeb: Celeb) {
//    Card {
//        Column(
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 20.dp)
//        ) {
//            Text(text = celeb.FirstName + " " + celeb.LastName, style = MaterialTheme.typography.h4)
//            Text(text = celeb.Company, style = MaterialTheme.typography.h4)
//        }
//    }
//
//}