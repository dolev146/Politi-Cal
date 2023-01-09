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
import com.example.politi_cal.SendWelcomeNotification
import com.example.politi_cal.adminAnalyticsTitle
import com.example.politi_cal.adminDistribution
import com.example.politi_cal.models.CallBack
import com.example.politi_cal.notificationMap
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
var monthDistribution = false

@Composable
fun AdminAnalyticsMenuScreen(navController: NavController, auth: FirebaseAuth) {
    val analyticsCreator = AdminAnalyticsQueriesDBObj()
    var openDialog by remember { mutableStateOf(openDialog) }
    var userNumber by remember { mutableStateOf(userNumber) }
    var userNumberByYear by remember { mutableStateOf(userNumberByYear) }
    var monthDistribution by remember { mutableStateOf(monthDistribution) }
    if (notificationMap[1] != null) {
        SendWelcomeNotification(notification = notificationMap[1]!!)
    }
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
                        adminAnalyticsTitle = "Parties distribution"
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
                        adminAnalyticsTitle = "Age distribution"
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
                        openDialog = true
                        monthDistribution = false
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
                        userNumber = false
                        userNumberByYear = true
                        openDialog = true
                        monthDistribution = true
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
                                        if (it.isDigitsOnly()) {
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
                                        if (dialoginput1 != "") {
                                            val year = Integer.parseInt(dialoginput1)
                                            if (!monthDistribution) {
                                                var callback = CallBack<Int, Int>(year)
                                                analyticsCreator.getNumberOfUsersByYear(callback)
                                                while (!callback.getStatus()) {
                                                    continue
                                                }
                                                var numberOfUsers = callback!!.getOutput()
                                                dialoginputtext =
                                                    "There are $numberOfUsers users who register in year $year"
                                                userNumber = true
                                                userNumberByYear = false
                                                openDialog = true
                                            } else {
                                                var callback = CallBack<Int, Map<Int, Double>>(year)
                                                analyticsCreator.getNumberOfUsersByYear_MonthBasedData(
                                                    callback
                                                )
                                                while (!callback.getStatus()) {
                                                    continue
                                                }
                                                adminDistribution.clear()
                                                val output = callback!!.getOutput()
                                                for (entry in output!!.entries) {
                                                    adminDistribution
                                                        .add(
                                                            PieChartData(
                                                                getMonthName(entry.key),
                                                                entry.value.toFloat() * 100
                                                            )
                                                        )
                                                }
                                                monthDistribution = false
                                                userNumber = false
                                                userNumberByYear = false
                                                openDialog = false
                                                adminAnalyticsTitle =
                                                    "User registration in $year distribution"
                                                navController.navigate(Screen.AdminAnalyticsViewScreen.route)
                                            }
                                        } else {
                                            monthDistribution = false
                                            userNumber = false
                                            userNumberByYear = false
                                            openDialog = false
                                        }
                                    }
                                ) {
                                    Text("OK")
                                }
                            }
                        }
                    )
                }
            }
        }
    })
}


fun getMonthName(month: Int): String {
    if (month == 1) {
        return "January"
    } else if (month == 2) {
        return "Feburary"
    } else if (month == 3) {
        return "March"
    } else if (month == 4) {
        return "April"
    } else if (month == 5) {
        return "May"
    } else if (month == 6) {
        return "June"
    } else if (month == 7) {
        return "July"
    } else if (month == 8) {
        return "August"
    } else if (month == 9) {
        return "September"
    } else if (month == 10) {
        return "October"
    } else if (month == 11) {
        return "November"
    } else {
        return "December"
    }
}
