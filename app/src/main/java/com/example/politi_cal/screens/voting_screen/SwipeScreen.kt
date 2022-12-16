package com.example.politi_cal.screens.voting_screen

import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.politi_cal.MainActivity
import com.example.politi_cal.R
import com.example.politi_cal.celebCollectionRef
import com.example.politi_cal.models.Celeb
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

var celebListParam = mutableListOf<Celeb>()

fun getCelebrities(context: Context) = CoroutineScope(Dispatchers.IO).launch {
    try {
        celebCollectionRef.get().addOnSuccessListener { documents ->
            for (document in documents) {
                val celeb = Celeb(
                    FirstName = document.data["firstName"] as String,
                    LastName = document.data["lastName"] as String,
                    BirthDate = document.data["birthDate"] as Long,
                    ImgUrl = document.data["imgUrl"] as String,
                    CelebInfo = document.data["celebInfo"] as String,
                    Category = document.data["category"] as String,
                    RightVotes = document.data["rightVotes"] as Long,
                    LeftVotes = document.data["leftVotes"] as Long,
                    Company = document.data["company"] as String,
                )
                celebListParam.add(celeb)
            }
        }.await()
        println(
            "Celebrities: $celebListParam"
        )

        withContext(Dispatchers.Main) {
            Toast.makeText(context, "Celebrities loaded", Toast.LENGTH_SHORT).show()
        }

        celebListParam.shuffle() // shuffle the list

    } catch (e: Exception) {
        Log.d(MainActivity.TAG, "Error Getting celebrities: $e")
        withContext(Dispatchers.Main) {
            Toast.makeText(
                context, "Error Error Getting celebrities: $e", Toast.LENGTH_SHORT
            ).show()
        }
    }
}


@Composable
fun SwipeScreen(navController: NavController, auth: FirebaseAuth) {
    val context = LocalContext.current
    getCelebrities(context)

    val celebList = celebListParam
    // create  a Text and insert the celebList to it and show it
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Text(
            text = celebList.toString(), modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
    SwipeScreenAlternate(navController, auth)
}


@Composable
fun SwipeScreenAlternate(navController: NavController, auth: FirebaseAuth) {
    // A surface container using the 'background' color from the theme
    Surface(color = MaterialTheme.colors.background) {
        Column() {
            PoliticalAppIconTop()
            HeroCard(
//                fullName = celeb.FirstName + " " + celeb.LastName,
//                worksAt = celeb.Company,
                fullName = "Joe Biden",
                worksAt = "President of the United States",
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
            Text(
                text = "Lefty", style = TextStyle(
                    color = Color(0xFF03588c), fontSize = 24.sp, fontWeight = FontWeight.Bold
                ),
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
                text = "Righty", style = TextStyle(
                    color = Color(0xFFa60321), fontSize = 24.sp, fontWeight = FontWeight.Bold
                ), modifier = Modifier.offset(x = -(25).dp)
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
//            Image(
//                painter = rememberAsyncImagePainter("https://picsum.photos/300/300"),
//                contentDescription = "$fullName $worksAt",
//                contentScale = ContentScale.Crop,
//            )
            AsyncImage(
                model = "https://user-images.githubusercontent.com/62290677/207936995-629a9f12-3992-48b2-b23f-12f901aa19c2.png",
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