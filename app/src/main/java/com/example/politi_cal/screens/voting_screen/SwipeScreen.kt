package com.example.politi_cal.screens.voting_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.politi_cal.Navigation
import com.example.politi_cal.R
import com.example.politi_cal.Screen
import com.example.politi_cal.celebListParam
import com.example.politi_cal.models.Celeb
import com.google.firebase.auth.FirebaseAuth




@Composable
fun SwipeScreen(navController: NavController, auth: FirebaseAuth) {

    SwipeScreenAlternate(navController, auth)
}


@Composable
fun SwipeScreenAlternate(navController: NavController, auth: FirebaseAuth) {
    // A surface container using the 'background' color from the theme
    Surface(color = MaterialTheme.colors.background) {
        Column() {
            PoliticalAppIconTop()
//            Text(
//                text = celebListParam.toString(), modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp)
//            )
            var celeb = Celeb(Company = "test" , FirstName = "text" , LastName = "text" , BirthDate = 0 , ImgUrl = "https://user-images.githubusercontent.com/62290677/208085405-50e2a05c-2a41-4579-8038-263fe097b80d.png" , CelebInfo = "text" , Category = "text" , RightVotes = 0 , LeftVotes = 0)
            // check if the list is empty

            if (celebListParam.isEmpty()) {
                Text(
                    text = "Loading...", modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            } else {
                celeb = celebListParam[0]

            }
//            if(celebListParam.isNotEmpty()){
//                 celeb = celebListParam[0]
//            }

            HeroCard(
//                fullName = celeb.FirstName + " " + celeb.LastName,
//                worksAt = celeb.Company,
                fullName = celeb.FirstName + " " + celeb.LastName,
                worksAt = "Works at " + celeb.Company,
                painter = celeb.ImgUrl
            )
            LeftRightButtonsRow(navController)





        }
    }
}

@Composable
fun LeftRightButtonsRow(navController : NavController) {
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
            Text(
                text = "Lefty", style = TextStyle(
                    color = Color(0xFF03588c), fontSize = 24.sp, fontWeight = FontWeight.Bold
                ),
                // move the text a little bit to the right
                modifier = Modifier.padding(start = 30.dp)
            )
        }
        IconButton(onClick = {
            celebListParam.removeAt(0)
            navController.navigate(Screen.SwipeScreen.route)
        }) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.righty_svg),
                contentDescription = "Righty",
                tint = Color(0xFFa60321),
                modifier = Modifier.size(150.dp)
            )
            Text(
                text = "Righty", style = TextStyle(
                    color = Color(0xFFa60321), fontSize = 24.sp, fontWeight = FontWeight.Bold
                ), modifier = Modifier.offset(x = -(25).dp)
            )
        }

    }
}


@Composable
fun HeroCard(modifier: Modifier = Modifier, fullName: String, worksAt: String, painter: String) {
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
    painter: String, worksAt: String, fullName: String, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(0.98f)
            .fillMaxHeight(0.8f),
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp
    ) {
        Box() {
//            Image(
//                painter = rememberAsyncImagePainter("https://picsum.photos/300/300"),
//                contentDescription = "$fullName $worksAt",
//                contentScale = ContentScale.Crop,
//            )
            AsyncImage(
                model = painter,
                contentDescription = null,
                placeholder = painterResource(R.drawable.app_logo),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
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