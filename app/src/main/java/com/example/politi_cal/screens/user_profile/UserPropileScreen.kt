package com.example.politi_cal.screens.user_profile


import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth


@Composable
fun UserProfileScreen(navController: NavController, auth: FirebaseAuth) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Text(text = "User Profile", fontSize = 40.sp)
        var username by remember {
            mutableStateOf("ofri")
        }
        var mail by remember {
            mutableStateOf("mail")
        }


        Surface(
            modifier = Modifier
                .width(250.dp)
                .height(50.dp)
        ) {
            Row(
                modifier = Modifier
                    .height(250.dp)
                    .width(50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Welcome back " + username,
                    fontSize = 25.sp,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Surface(
            modifier = Modifier
                .width(350.dp)
                .height(50.dp)
        ) {
            Row(
                modifier = Modifier
                    .height(350.dp)
                    .width(50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Select your favorite categories:",
                    fontSize = 25.sp,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        var sport_checked by remember {
            mutableStateOf(true)
        }

        var journalism_checked by remember {
            mutableStateOf(true)
        }

        var academics_checked by remember {
            mutableStateOf(true)
        }

        var culinary_checked by remember {
            mutableStateOf(true)
        }

        var entertainment_checked by remember {
            mutableStateOf(true)
        }

        var science_checked by remember {
            mutableStateOf(true)
        }

        val categories = listOf<String>(
            "Sport", "Journalism", "Academics", "Culinary", "Entertainment", "Science"
        )

        Surface(
            modifier = Modifier
                .width(350.dp)
                .height(50.dp)
        ) {


            Row(
                modifier = Modifier
                    .height(350.dp)
                    .width(50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {


                //TODO: need to think how to make it changeable


                Checkbox(
                    checked = sport_checked,
                    onCheckedChange = { sport_checked = !sport_checked })
                Text(text = categories[0], fontSize = 20.sp)

                Spacer(modifier = Modifier.size(10.dp))

                Checkbox(checked = journalism_checked,
                    onCheckedChange = { journalism_checked = !journalism_checked })
                Text(text = categories[1], fontSize = 20.sp)

            }
        }

        Surface(
            modifier = Modifier
                .width(350.dp)
                .height(50.dp)
        ) {


            Row(
                modifier = Modifier
                    .height(350.dp)
                    .width(50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {


                //TODO: need to think how to make it changeable


                Checkbox(checked = academics_checked,
                    onCheckedChange = { academics_checked = !academics_checked })
                Text(text = categories[2], fontSize = 20.sp)

                Spacer(modifier = Modifier.size(10.dp))

                Checkbox(checked = culinary_checked,
                    onCheckedChange = { culinary_checked = !culinary_checked })
                Text(text = categories[3], fontSize = 20.sp)

            }
        }

        Surface(
            modifier = Modifier
                .width(350.dp)
                .height(50.dp)
        ) {
            Row(
                modifier = Modifier
                    .height(350.dp)
                    .width(50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {


                //TODO: need to think how to make it changeable


                Checkbox(checked = entertainment_checked,
                    onCheckedChange = { entertainment_checked = !entertainment_checked })
                Text(text = categories[4], fontSize = 20.sp)

                Spacer(modifier = Modifier.size(10.dp))

                Checkbox(checked = science_checked,
                    onCheckedChange = { science_checked = !science_checked })
                Text(text = categories[5], fontSize = 20.sp)

            }
        }

    }
}
