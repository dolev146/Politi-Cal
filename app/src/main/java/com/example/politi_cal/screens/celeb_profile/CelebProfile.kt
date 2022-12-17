package com.example.politi_cal.screens.celeb_profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.politi_cal.models.Celeb
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.roundToInt


@Composable
fun CelebProfileScreen(
    navController: NavController,
    auth: FirebaseAuth,
    firstName: String,
    lastName: String,
    company: String,
    category: String,
    imgUrl: String,
    celebInfo: String,
    birthDate: Long,
    rightVotes: Long,
    leftVotes: Long,
) {
    var celeb = Celeb(
        FirstName = firstName,
        LastName = lastName,
        Company = company,
        Category = category,
        ImgUrl = imgUrl,
        CelebInfo = celebInfo,
        BirthDate = birthDate,
        RightVotes = rightVotes,
        LeftVotes = leftVotes
    )
    CelebProfileScreenAlternate(navController, auth, celeb)
}


@Composable
fun CelebProfileScreenAlternate(navController: NavController, auth: FirebaseAuth, celeb: Celeb) {
    BlackBackgroundSquare()
    Column(modifier = Modifier.fillMaxSize()) {

        LazyColumn(content = {
            item {

                TopBar()
                Spacer(modifier = Modifier.height(60.dp))
                ProfileSection(
                    name = celeb.FirstName + " " + celeb.LastName,
                    company = celeb.Company,
                    ImgUrl = celeb.ImgUrl,
                )

                // check if celeb.RightVotes is zero then increase it by 1
                if(celeb.RightVotes == 0L) {
                    celeb.RightVotes += 1
                }
                // check if celeb.LeftVotes is zero then increase it by 1
                if(celeb.LeftVotes == 0L) {
                    celeb.LeftVotes += 1
                }
                val total = celeb.RightVotes + celeb.LeftVotes
                val rightPercent = (celeb.RightVotes.toDouble() / total.toDouble()) * 100
                val leftPercent = (celeb.LeftVotes.toDouble() / total.toDouble()) * 100

                // voting bar
                VotingBar(
                    leftyPercent = leftPercent, rightyPercent = rightPercent
                )
                MoreInfo(
                    celeb.CelebInfo
                )
            }
        })


    }
}

@Composable
fun MoreInfo(information_param: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(start = 26.dp, end = 26.dp)) {
        Text(
            text = "More information",
            color = Color.Black,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = information_param,
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            maxLines = 10,
            overflow = TextOverflow.Ellipsis
        )


    }
}

@Composable
fun VotingBar(
    modifier: Modifier = Modifier, leftyPercent: Double, rightyPercent: Double
) {

    val shape = RoundedCornerShape(32.dp)
    Column(
        Modifier
            .padding(start = 16.dp, end = 16.dp)
            .background(Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(32.dp)
                .background(Color.Transparent)
                .clip(shape)
                .border(1.dp, Color.Black, shape)
        ) {

            Column(
                modifier = Modifier
                    .background(Color(0xFF03588C))
                    .fillMaxHeight()
                    .weight(leftyPercent.toFloat())
                    .clip(CircleShape),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
                // add rounded corners to the right side
            ) {
//0xFFA60321
            }
            Column(
                // add rounded corners to the left side
                modifier = Modifier
                    .background(Color(0xFFA60321))
                    .weight(rightyPercent.toFloat())
                    .clip(CircleShape)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

            }
        }
        // second row
        // stack over flow https://stackoverflow.com/questions/74619069/what-is-the-attribute-of-the-moddifier-that-i-need-to-change-to-make-the-corners?noredirect=1#comment131712293_74619069
        Column(
            Modifier.padding(start = 46.dp, end = 46.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.White),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF03588C))
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Lefty ${leftyPercent.roundToInt()}%",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row() {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFA60321))
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Righty ${rightyPercent.roundToInt()}%",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun BlackBackgroundSquare() {
    Box(
        // modifier fill only half the screen
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            // insert background color as hex
            .background(Color(0xFF2C3E50))
    )
}

@Composable
fun TopBar(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Profile",
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            color = Color.White
        )

    }

}


@Composable
fun ProfileSection(
    name: String, company: String, ImgUrl : String ,  modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImgUrl,
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(200.dp)
                .border(
                    width = 6.dp, color = Color.White, shape = CircleShape
                )
                .aspectRatio(1f, matchHeightConstraintsFirst = true)
                .clip(CircleShape)
                .padding(3.dp)

        )

        Text(
            text = name,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            color = Color.Black
        )
        Text(
            text = company,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.Black
        )
    }
}
