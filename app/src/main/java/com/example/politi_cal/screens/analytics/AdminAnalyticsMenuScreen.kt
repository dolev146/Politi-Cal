package com.example.politi_cal.screens.analytics

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavController
import com.example.politi_cal.DBObjects.AdminAnalyticsQueriesDBObj
import com.example.politi_cal.DBObjects.AnalyticsQueriesObj
import com.example.politi_cal.Screen
import com.example.politi_cal.adminAnalyticsTitle
import com.example.politi_cal.adminDistribution
import com.example.politi_cal.models.CallBack
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.material.AlertDialog as AlertDialog1

var dialogtext = ""
var dialoginputtext = ""
var dialoginput1 = ""
var dialoginput1text = ""
var dialoginput2 = ""
var dialoginput2text = ""

//var piechart: PieChart? = null
var openDialog = false
var userNumber = false
var userNumberByYear = false
var userNumberTimeRange = false

@Composable
fun AdminAnalyticsMenuScreen(navController: NavController, auth: FirebaseAuth) {
    val analyticsCreator = AdminAnalyticsQueriesDBObj()
    var openDialog by remember { mutableStateOf(openDialog) }
    var userNumber by remember { mutableStateOf(userNumber) }
    var userNumberByYear by remember { mutableStateOf(userNumberByYear) }
    var userNumberTimeRange by remember { mutableStateOf(userNumberTimeRange) }
    LazyColumn(content = {
        item {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround,


                ) {


                Text(
                    text = "Admin Analytics",
                    style = MaterialTheme.typography.h4
                )


                Button(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),

                    onClick = {
                        // TODO: Add analytics
                        var callback = CallBack<Boolean, Map<String, Double>>(true)
                        analyticsCreator.getPartiesDistribution(callback)
                        while (!callback.getStatus()) {
                            continue
                        }
                        val output = callback.getOutput()
                        adminDistribution.clear()
                        for (entry in output!!.entries) {
                            val partyName = entry.key
                            val precent = entry.value.toFloat() * 100
                            adminDistribution.add(PieChartData(partyName, precent))
                        }
                        adminAnalyticsTitle = "Favorite parties distribution"
                        navController.navigate(Screen.AdminAnalyticsViewScreen.route)
                    }
                ) {
                    Text(text = "Party Distribution")
                }

                Button(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    onClick = {
                        // TODO: Add analytics
                        var callback = CallBack<Boolean, Map<String, Double>>(true)
                        analyticsCreator.getAgeDistribution(callback)
                        while (!callback.getStatus()) {
                            continue
                        }
                        val output = callback.getOutput()
                        adminDistribution.clear()
                        for (entry in output!!.entries) {
                            val partyName = entry.key
                            val precent = entry.value.toFloat() * 100
                            adminDistribution.add(PieChartData(partyName, precent))
                        }
                        adminAnalyticsTitle = "Age group distribution"
                        navController.navigate(Screen.AdminAnalyticsViewScreen.route)

                    }
                ) {
                    Text(text = "age group distribution")
                }

                Button(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    onClick = {
                        // TODO show number of users
                        var callback = CallBack<Int, Int>(0)
                        analyticsCreator.getNumberOfUsersByTime(callback)
                        while (!callback.getStatus()) {
                            continue
                        }
                        var output = callback!!.getOutput()
                        dialoginputtext = "There are currently $output registered \n " +
                                "users in the application."
                        userNumber = true
                        userNumberByYear = false
                        userNumberTimeRange = false
                        openDialog = true

                    }
                ) {
                    Text(text = "Number Of Users")
                }

                Button(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    onClick = {
                        // TODO show number of users
                        userNumber = false
                        userNumberByYear = true
                        userNumberTimeRange = false
                        openDialog = true
                    }
                ) {
                    Text(text = "Number Of Users In Year ")
                }


                Button(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    onClick = {
                        // TODO show number of users
                    }
                ) {
                    Text(text = "users registered in year distributed by month ")
                }
            }
            if (openDialog) {
                if (userNumber) {
                    AlertDialog1(
                        onDismissRequest = {
                            openDialog = false
                        },
                        title = {
                            Text(text = "Result")
                        },
                        text = {
                            Text(text = dialoginputtext)
                        },
                        buttons = {
                            Row(
                                modifier = Modifier.padding(all = 8.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Button(
                                    modifier = Modifier.fillMaxWidth(),
                                    onClick = {
                                        openDialog = false
                                        userNumber = false
                                        userNumberByYear = false
                                        userNumberTimeRange = false
                                    }
                                ) {
                                    Text("OK")
                                }
                            }
                        }
                    )
                } else if (userNumberByYear) {
                    var selected_year by remember {
                        mutableStateOf("")
                    }
                    AlertDialog1(
                        onDismissRequest = {
                            openDialog = false
                        },
                        title = {
                            Text(text = "Input needed")
                        },
                        text = {
                            Column() {
                                Text("Enter the Wanted year")
                                TextField(
                                    value = selected_year,
                                    onValueChange = {
                                        if(it.isDigitsOnly()) {
                                            selected_year = it
                                        }
                                    }
                                )
                            }
                        },
                        buttons = {
                            Row(
                                modifier = Modifier.padding(all = 8.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Button(
                                    modifier = Modifier.fillMaxWidth(),
                                    onClick = {
                                        dialoginput1 = selected_year
                                        val analyticsCreator = AdminAnalyticsQueriesDBObj()
                                        val year = Integer.parseInt(dialoginput1)
                                        var callback = CallBack<Int, Int>(year)
                                        analyticsCreator.getNumberOfUsersByYear(callback)
                                        while (!callback.getStatus()) {
                                            continue
                                        }
                                        var numberOfUsers = callback!!.getOutput()
                                        dialoginputtext = "There are $numberOfUsers users who register in year $year"
                                        userNumber = true
                                        userNumberByYear = false
                                        userNumberTimeRange = false
                                        openDialog = true
                                    }
                                ) {
                                    Text("OK")
                                }
                            }
                        }
                    )
                } else if (userNumberTimeRange) {

                }
            }
        }
    })
}

//@Composable
//fun InfoDialog() {
//    AlertDialog1(
//        onDismissRequest = {
//            openDialog = false
//        },
//        title = {
//            Text(text = "Result")
//        },
//        text = {
//            Text(text = dialoginputtext)
//        },
//        buttons = {
//            Row(
//                modifier = Modifier.padding(all = 8.dp),
//                horizontalArrangement = Arrangement.Center
//            ) {
//                Button(
//                    modifier = Modifier.fillMaxWidth(),
//                    onClick = {
//                        openDialog = false
//                        userNumber = false
//                        userNumberByYear = false
//                        userNumberTimeRange = false
//                    }
//                ) {
//                    Text("OK")
//                }
//            }
//        }
//    )
//}

//
//@Composable
//fun InputDialog() {
//    AlertDialog1(
//        onDismissRequest = {
//            openDialog = false
//        },
//        title = {
//            Text(text = "Input needed")
//        },
//        text = {
//            Column() {
//                Text("Enter the Wanted year")
//                TextField(
//                    value = "2022",
//                    onValueChange = {
//                        if (it.isDigitsOnly()) {
//                            dialoginput1 = it
//                        } else {
//                            dialoginput1 = "2022"
//                        }
//                    }
//                )
//            }
//        },
//        buttons = {
//            Row(
//                modifier = Modifier.padding(all = 8.dp),
//                horizontalArrangement = Arrangement.Center
//            ) {
//                Button(
//                    modifier = Modifier.fillMaxWidth(),
//                    onClick = {
//                        val analyticsCreator = AdminAnalyticsQueriesDBObj()
//                        val year = Integer.parseInt(dialoginput1)
//                        var callback = CallBack<Int, Int>(year)
//                        analyticsCreator.getNumberOfUsersByYear(callback)
//                        while (!callback.getStatus()) {
//                            continue
//                        }
//                        var numberOfUsers = callback!!.getOutput()
//                        dialoginputtext = "There are $numberOfUsers users who register in year $year"
//                        userNumber = true
//                        userNumberByYear = false
//                        userNumberTimeRange = false
//                        openDialog = true
//                    }
//                ) {
//                    Text("OK")
//                }
//            }
//        }
//    )
//}
//
