package com.example.politi_cal.screens.voting_screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import com.example.politi_cal.R
import com.example.politi_cal.celebCollectionRef
import com.example.politi_cal.celebListParam
import com.example.politi_cal.models.Celeb
import com.example.politi_cal.models.UserVote
import com.example.politi_cal.userVotesCollectionRef
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


private fun leftVote(celeb: Celeb, context: Context) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val email = FirebaseAuth.getInstance().currentUser?.email
            val userVote = UserVote(
                UserEmail = email.toString(),
                CelebFullName = celeb.FirstName + " " + celeb.LastName,
                CategoryName = celeb.Category,
                CompanyName = celeb.Company,
                VoteDirection = "left"
            )
            userVotesCollectionRef.add(userVote).await()
            celeb.LeftVotes += 1
            celebCollectionRef.document(celeb.FirstName + " " + celeb.LastName)
                .set(celeb, SetOptions.merge()).await()
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Left Vote Added", Toast.LENGTH_SHORT).show()
            }

        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

private fun rightVote(celeb: Celeb, context: Context) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val email = FirebaseAuth.getInstance().currentUser?.email
            val userVote = UserVote(
                UserEmail = email.toString(),
                CelebFullName = celeb.FirstName + " " + celeb.LastName,
                CategoryName = celeb.Category,
                CompanyName = celeb.Company,
                VoteDirection = "right"
            )
            userVotesCollectionRef.add(userVote).await()
            celeb.RightVotes += 1
            celebCollectionRef.document(celeb.FirstName + " " + celeb.LastName)
                .set(celeb, SetOptions.merge()).await()
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Right Vote Added", Toast.LENGTH_SHORT).show()
            }

        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun SwipeScreen(navController: NavController, auth: FirebaseAuth) {

    var celeb by remember {
        mutableStateOf<Celeb>(
            Celeb(
                Company = "test",
                FirstName = "text",
                LastName = "text",
                BirthDate = 0,
                ImgUrl = "https://user-images.githubusercontent.com/62290677/208085405-50e2a05c-2a41-4579-8038-263fe097b80d.png",
                CelebInfo = "text",
                Category = "text",
                RightVotes = 0,
                LeftVotes = 0
            )
        )
    }

    if (celebListParam.isEmpty()) {
        Text(
            text = "Loading...", modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    } else {
        celeb = celebListParam[0]
    }

    SwipeScreenAlternate(celeb) {
        // check if the list is empty before removing the first element
        if (celebListParam.size > 1) {
            celebListParam.removeAt(0)
            celeb = celebListParam[0]
        }
    }
}


@Composable
fun SwipeScreenAlternate(
    celeb: Celeb, mycustomfun: () -> Unit
) {
    // A surface container using the 'background' color from the theme
    Surface(color = MaterialTheme.colors.background) {
        Column() {
            PoliticalAppIconTop()
            HeroCard(
                fullName = celeb.FirstName + " " + celeb.LastName,
                worksAt = "Works at " + celeb.Company,
                painter = celeb.ImgUrl
            )
            if (celebListParam.size != 1) {
                LeftRightButtonsRow(mycustomfun, celeb)
            }

        }
    }
}

@Composable
fun LeftRightButtonsRow(mycustomfun: () -> Unit, celeb: Celeb) {
    val context = LocalContext.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        IconButton(onClick = {
            leftVote(celeb, context)
            mycustomfun()
        }) {
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

            mycustomfun()
            rightVote(celeb, context)
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

