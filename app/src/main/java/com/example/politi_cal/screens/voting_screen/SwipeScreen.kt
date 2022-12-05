package com.example.politi_cal.screens.voting_screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.politi_cal.R
import com.google.firebase.auth.FirebaseAuth


@Composable
fun SwipeScreen(navController: NavController, auth: FirebaseAuth) {
    // A surface container using the 'background' color from the theme
    Surface(color = MaterialTheme.colors.background) {
//        TopBar()
        Column() {
            PoliticalAppIconTop()
            HeroCard(
                fullName = "Yakov Cohen",
                worksAt = "McDonalds",
                painter = painterResource(id = R.drawable.kermit2)
            )
            LeftRightButtonsRow()
        }
    }
}

@Composable
fun LeftRightButtonsRow() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.left_svg),
                contentDescription = "lefty",
                tint = Color(0xFF03588c),
                modifier = Modifier.size(150.dp)
            )
            Text(text = "Lefty"
                , style = TextStyle(color = Color(0xFF03588c), fontSize = 24.sp, fontWeight = FontWeight.Bold),
                // move the text a little bit to the right
                modifier = Modifier.padding(start = 30.dp)
            )
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.righty_svg),
                contentDescription = "Righty",
                tint = Color(0xFFa60321),
                modifier = Modifier.size(150.dp)
            )
            Text(
                text = "Righty",
                style = TextStyle(color = Color(0xFFa60321), fontSize = 24.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.offset(x = -(25).dp)
            )
        }

    }
}


@Composable
fun HeroCard(modifier: Modifier = Modifier, fullName: String, worksAt: String, painter: Painter) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ) {
        ImageCard(painter = painter, fullName = fullName, worksAt = worksAt)
    }
}


@Composable
fun ImageCard(
    painter: Painter, worksAt: String, fullName: String, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(0.98f)
            .fillMaxHeight(0.8f),
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp
    ) {
        Box() {
            Image(
                painter = painter,
                contentDescription = "$fullName $worksAt",
                contentScale = ContentScale.Crop,
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black), startY = 300f
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Column() {
                    Text(
                        text = fullName,
                        style = TextStyle(color = Color.White, fontSize = 26.sp),

                        )
                    Text(text = worksAt, style = TextStyle(color = Color.White, fontSize = 16.sp))
                }

            }
        }
    }

}


@Composable
fun PoliticalAppIconTop(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.app_logo),
            contentDescription = "Political App Icon",
            tint = Color.Black,
            modifier = Modifier.size(100.dp)
        )
//        Image(
//            painter = painterResource(id = R.drawable.political_icon_image),
//            contentDescription = "Political App Icon",
//            modifier = Modifier
//                .size(100.dp)
//        )
    }
}

//@Composable
//fun TopBar(
//    modifier: Modifier = Modifier
//) {
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceBetween,
//        modifier = modifier.fillMaxWidth()
//    ) {
//        Icon(
//            imageVector = Icons.Default.Menu,
//            contentDescription = "Back",
//            tint = Color.Black,
//            modifier = Modifier
//                .size(40.dp)
//                .padding(start = 5.dp)
//        )
//    }
//}