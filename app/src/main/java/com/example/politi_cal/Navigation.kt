package com.example.politi_cal

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.politi_cal.screens.NavDrawer.DrawerTopBar
import com.example.politi_cal.screens.add_celeb.AddCelebScreen
import com.example.politi_cal.screens.celeb_profile.CelebProfileScreen
import com.example.politi_cal.screens.login.LoginScreen
import com.example.politi_cal.screens.preferences.PreferenceScreen
import com.example.politi_cal.screens.registration.RegisterScreen
import com.example.politi_cal.screens.user_profile.UserProfileScreen
import com.example.politi_cal.screens.voting_screen.SwipeScreen


@Composable
fun Navigation() {
    val navCotroller = rememberNavController()
    NavHost(navController = navCotroller, startDestination = Screen.LoginScreen.route) {
//        composable(route = Screen.MainScreen.route) {
//            MainScreen(navController = navCotroller)
//        }
//        // if we want the name to be optional we can use navArgument with nullable = true
//        // we specify the route with ? to make it optional and we can use it in the composable
//        // ?name={name}
//        // if we want more than 1 parameter
//        // /{name}/{age}
//        composable(route = Screen.DetailScreen.route + "/{name}" , arguments = listOf(
//            navArgument("name") {
//                type = NavType.StringType
//                defaultValue = "Yaakov"
//                nullable = true
//            }
//        )
//        ){entry ->
//            DetailScreen(name = entry.arguments?.getString("name"))
//        }




        composable(route = Screen.RegisterScreen.route) {
            RegisterScreen(navController = navCotroller)
        }

        composable(route = Screen.LoginScreen.route) {

            LoginScreen(navController = navCotroller)

        }

        composable(route = Screen.PreferenceScreen.route) {
            DrawerTopBar(navController = navCotroller , screen = { navController ->
                // this is the screen that will be drawn after the drawer
                // swipe screen
                PreferenceScreen(navController = navController)

            })



        }

        composable(route = Screen.SwipeScreen.route) {
            DrawerTopBar(navController = navCotroller , screen = { navController ->
                // this is the screen that will be drawn after the drawer
                // swipe screen
                SwipeScreen(navController = navController)

            })
        }

        composable(route = Screen.CelebProfileScreen.route) {
            DrawerTopBar(navController = navCotroller , screen = { navController ->
                // this is the screen that will be drawn after the drawer
                // swipe screen
                CelebProfileScreen(navController = navController)

            })
        }

        composable(route = Screen.UserProfileScreen.route) {
            DrawerTopBar(navController = navCotroller , screen = { navController ->
                // this is the screen that will be drawn after the drawer
                // swipe screen
                UserProfileScreen(navController = navController)

            })
        }

        composable(route = Screen.AddCelebScreen.route) {
            DrawerTopBar(navController = navCotroller , screen = { navController ->
                // this is the screen that will be drawn after the drawer
                // swipe screen
                AddCelebScreen(navController = navController)

            })
        }

    }
}




