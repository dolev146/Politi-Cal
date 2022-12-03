package com.example.politi_cal.screens.celeb_profile

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.politi_cal.R


@Composable
fun CelebProfileScreen(navController: NavController) {
     BlackBackgroundSquare()
    Column(modifier = Modifier.fillMaxSize()) {

        LazyColumn(content = {
            item {

                TopBar()
                Spacer(modifier = Modifier.height(60.dp))
                ProfileSection(
                    name = "Amit Segal",
                    company = "N12 news channel",
                )

                // voting bar
                VotingBar(
                    leftyPercent = 10, rightyPercent = 90
                )
                // lazy column for more info

                MoreInfo("Amit Segal is a journalist and a news anchor. He is the host of the N12 news channel. He is a very popular journalist. Amit Yitzchak Segal[1] (born Biz in Nisan 5, 1982, April 10, 1982) is an Israeli journalist, radio and television personality. Serves as the political commentator of the news company and a political columnist in the \"Yediot Aharonot\" newspaper. One of the most influential journalists in Israel[2]. Presents Meet the Press on Channel 12 together with Ben Caspit.")


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
    modifier: Modifier = Modifier, leftyPercent: Int, rightyPercent: Int
) {
    var leftyPercentWeight: Float = (leftyPercent / 10).toFloat()
    var rightyPercentWeight: Float = (rightyPercent / 10).toFloat()

    val shape = RoundedCornerShape(32.dp)
    Column(
        Modifier.padding(start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(32.dp)
                .background(Color.White)
                .clip(shape)
                .border(1.dp, Color.Black, shape)
        ) {
            Column(
                // add rounded corners to the left side
                modifier = Modifier
                    .background(Color(0xFFA60321))
                    .weight(rightyPercentWeight)
                    .clip(CircleShape)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

            }
            Column(
                modifier = Modifier
                    .background(Color(0xFF03588C))
                    .fillMaxHeight(leftyPercentWeight)
                    .weight(1f)
                    .clip(CircleShape),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
                // add rounded corners to the right side
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
                            .background(Color(0xFFA60321))
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Right $rightyPercent%",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row() {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF03588C))
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Left $leftyPercent%", fontSize = 20.sp, fontWeight = FontWeight.Bold
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
    name: String, company: String, modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RoundImage(
            image = painterResource(id = R.drawable.profile_pic), modifier = Modifier.size(250.dp)
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

@Composable
fun RoundImage(
    image: Painter, modifier: Modifier = Modifier
) {
    Image(
        painter = image,
        contentDescription = "Profile image",
        modifier = modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .border(
                width = 6.dp, color = Color.White, shape = CircleShape
            )
            .padding(3.dp)
            .clip(CircleShape)
    )
}