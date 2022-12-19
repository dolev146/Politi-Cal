package com.example.politi_cal.screens.NavDrawer


import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.politi_cal.Screen
import com.example.politi_cal.db
import com.example.politi_cal.userCollectionRef
import com.google.common.primitives.UnsignedBytes.toInt
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher



@Composable
fun DrawerHeader() {
    Box(
        modifier = Modifier
            //.height(200.dp)
            //.background(Color.LightGray)
            .fillMaxWidth()
            .padding(vertical = 64.dp), contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Header", fontSize = 60.sp, modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun DrawerBody(
    items: List<MenuItem>,
    modifier: Modifier = Modifier,
    itemTextStyle: TextStyle = TextStyle(fontSize = 18.sp),
    onItemClick: (MenuItem) -> Unit,
) {
    LazyColumn(modifier) {
        items(items) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick(item) }
                    .padding(16.dp),

                ) {
                Icon(imageVector = item.icon, contentDescription = item.contentDescription)
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = item.title,
                    //modifier = Modifier.align(Alignment.CenterVertically)
                    modifier = Modifier.weight(1f), style = itemTextStyle

                )
            }
        }
    }

}

// the drawer TopBar should get the navController as a parameter ,
// and the composable screen function that it needs to draw after the drawer
@Composable
fun DrawerTopBar(
    navController: NavController,
    auth: FirebaseAuth = FirebaseAuth.getInstance(),
    screen: @Composable (navController: NavController) -> Unit
) {
    var isAdminState by remember {
        mutableStateOf(false)
    }



    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    isAdmin(context){
        isAdminState = true
    }
    var menuContent = mutableListOf(
        MenuItem(
            id = "search",
            title = "Search",
            contentDescription = "Go to Search screen",
            icon = Icons.Default.Search
        ),
        MenuItem(
            id = "swipe",
            title = "Swipe",
            contentDescription = "Go to Swipe screen",
            icon = Icons.Default.Home
        ),
        MenuItem(
            id = "settings",
            title = "Settings",
            contentDescription = "Go to Settings screen",
            icon = Icons.Default.Settings
        ),
        MenuItem(
            id = "user profile",
            title = "User Profile",
            contentDescription = "Go to User profile screen",
            icon = Icons.Default.AccountBox
        ),
        MenuItem(
            id = "add celeb",
            title = "Add Celeb",
            contentDescription = "Go to User profile screen",
            icon = Icons.Default.Add
        ),

        MenuItem(
            id = "admin analytics",
            title = "Admin Analytics",
            contentDescription = "Go to Admin Analytics screen",
            icon = Icons.Default.MoreVert
        ),
        MenuItem(
            id = "logout",
            title = "Logout",
            contentDescription = "Go to Logout screen",
            icon = Icons.Default.ExitToApp
        ),

        )

    if (isAdminState) {
        // add the ,
        // MenuItem(
        //            id = "admin only",
        //            title = "Admin only",
        //            contentDescription = "Go to Admin Only screen",
        //            icon = Icons.Default.MoreVert,
        //        )
        menuContent.add(
            MenuItem(
                id = "admin only",
                title = "Admin only",
                contentDescription = "Go to Admin Only screen",
                icon = Icons.Default.MoreVert,
            )
        )
    }



    Scaffold(

        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(onNavigationIconClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }

            })
        },
        drawerContent = {
            DrawerHeader()
            DrawerBody(items = menuContent, onItemClick = {
                when (it.id) {
                    "swipe" -> {
                        navController.navigate(Screen.SwipeScreen.route)
                    }
                    "settings" -> {
                        navController.navigate(Screen.PreferenceScreen.route)
                    }
                    "user profile" -> {
                        navController.navigate(Screen.UserProfileScreen.route)
                    }
                    "add celeb" -> {
                        navController.navigate(Screen.AddCelebScreen.route)
                    }
                    "admin analytics" -> {
                        navController.navigate(Screen.AdminAnalyticsScreen.route)
                    }
                    "logout" -> {
                        auth.signOut()
                        navController.navigate(Screen.LoginScreen.route)
                    }
                    "search" -> {
                        navController.navigate(Screen.SearchScreen.route)
                    }
                    "admin only" -> {
                        // check that the user is admin
                        // if not, show a message
                        // if yes, navigate to the admin only screen
                        if (isAdminState) {
                            navController.navigate(Screen.AdminOnlyScreen.route)
                        } else {
                            // show a message
                            Toast.makeText(context, "You are not an admin", Toast.LENGTH_SHORT)
                                .show()

                        }

                    }
                }
            })

        }) {

        screen(navController = navController)

    }
}


fun isAdmin(context: Context , changeStateFunc: () -> Unit) = CoroutineScope(Dispatchers.IO).launch {
    try {
        val email = FirebaseAuth.getInstance().currentUser?.email
        if (email != null) {
            val doc = userCollectionRef.document(email).get().await()
            if (doc.data != null) {
                for (document in doc.data?.entries!!) {
                    if (document.key == "roleID") {
                        var roleID = document.value as Long
                        if (roleID == 0L){

                                changeStateFunc()


                        }

                    }
                }
            }
        } else {

            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Didnt found user in user collection", toInt(2))
            }
        }


    } catch (e: Exception) {
        withContext(Dispatchers.Main) {
            Toast.makeText(context, "Bad request", toInt(2))
        }

    }

}


//{
//    val email = FirebaseAuth.getInstance().currentUser?.email
//
//
//    userCollectionRef.document(email).get().await()
//
//
//
//
//    return false
//
//}