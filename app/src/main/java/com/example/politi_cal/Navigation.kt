package com.example.politi_cal

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.politi_cal.models.CallBack
import com.example.politi_cal.models.Celeb
import com.example.politi_cal.screens.AddNewCompanyScreen
import com.example.politi_cal.screens.NavDrawer.DrawerTopBar
import com.example.politi_cal.screens.add_celeb.AddCelebScreen
import com.example.politi_cal.screens.admin_screen.AdminOnlyScreen
import com.example.politi_cal.screens.analytics.AdminAnalyticsScreen
import com.example.politi_cal.screens.celeb_profile.CelebProfileScreen
import com.example.politi_cal.screens.login.LoginScreen
import com.example.politi_cal.screens.preferences.PreferenceScreen
import com.example.politi_cal.screens.registration.RegisterScreen
import com.example.politi_cal.screens.search.SearchScreen
import com.example.politi_cal.screens.user_profile.UserProfileScreen
import com.example.politi_cal.screens.voting_screen.SwipeScreen
import com.google.firebase.auth.FirebaseAuth


@Composable
fun Navigation(auth: FirebaseAuth, startScreen: String = Screen.LoginScreen.route) {
    val navCotroller = rememberNavController()
    NavHost(navController = navCotroller, startDestination = startScreen) {
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
            RegisterScreen(navController = navCotroller, auth = auth)
        }

        composable(route = Screen.LoginScreen.route) {

            LoginScreen(navController = navCotroller, auth = auth)

        }

        composable(route = Screen.PreferenceScreen.route) {


            PreferenceScreen(navController = navCotroller, auth = auth)


        }



        composable(route = Screen.SwipeScreen.route) {
            var callBack = CallBack<Boolean,MutableList<Celeb>>(false)
            retrieveCelebsByUserOfri(callBack)
            while (!callBack.getStatus()) {
                continue
            }
            val whynotworking = callBack.getOutput()
            if (whynotworking != null) {
                celebListParam = whynotworking
            }
            DrawerTopBar(navController = navCotroller, screen = { navController ->
                SwipeScreen(navController = navController, auth = auth)
            })
        }

        composable(
            route = Screen.CelebProfileScreen.route
        ) {
            DrawerTopBar(navController = navCotroller, screen = { navController ->
                val searchedCeleb = CelebForCelebProfile
                CelebProfileScreen(
                    navController = navCotroller,
                    auth = auth,
                    searchedCeleb.FirstName,
                    searchedCeleb.LastName,
                    searchedCeleb.Company,
                    searchedCeleb.Category,
                    searchedCeleb.ImgUrl,
                    searchedCeleb.CelebInfo,
                    searchedCeleb.BirthDate,
                    searchedCeleb.RightVotes,
                    searchedCeleb.LeftVotes,
                )
            })
        }

        composable(route = Screen.SearchScreen.route) {
            DrawerTopBar(navController = navCotroller, screen = { navController ->
                SearchScreen(navController = navController, auth = auth)
            })
        }

        composable(route = Screen.UserProfileScreen.route) {
            DrawerTopBar(navController = navCotroller, screen = { navController ->
                // this is the screen that will be drawn after the drawer
                // swipe screen
                UserProfileScreen(navController = navController, auth = auth)

            })
        }

        composable(route = Screen.AddCelebScreen.route) {
            DrawerTopBar(navController = navCotroller, screen = { navController ->
                // this is the screen that will be drawn after the drawer
                // swipe screen
                // clear the companiesForAddCeleb
                companiesForAddCeleb.clear()
                companiesForAddCelebNames.clear()
                categoriesForAddCeleb.clear()
                categoriesForAddCelebNames.clear()
                retrieveCompanies()
                retrieveCategories()
                AddCelebScreen(navController = navController, auth = auth)

            })
        }

        composable(route = Screen.AdminOnlyScreen.route) {
            DrawerTopBar(navController = navCotroller, screen = { navController ->
                // this is the screen that will be drawn after the drawer
                // swipe screen
                AdminOnlyScreen(navController = navController, auth = auth)

            })
        }

        composable(route = Screen.AdminAnalyticsScreen.route) {
            DrawerTopBar(navController = navCotroller, screen = { navController ->
                // this is the screen that will be drawn after the drawer
                // swipe screen
                AdminAnalyticsScreen(navController = navController, auth = auth)

            })
        }

        composable(route = Screen.AddNewCompanyScreen.route) {
            AddNewCompanyScreen(navController = navCotroller, auth = auth)
        }

    }
}





