package com.example.politi_cal

sealed class Screen(val route: String) {
    object RegisterScreen : Screen("register_screen")
    object LoginScreen : Screen("login_screen")
    object PreferenceScreen : Screen("preference_screen")
    object PreferenceScreen1 : Screen("preference_screen1")
    object PreferenceScreen2 : Screen("preference_screen2")
    object SwipeScreen : Screen("swipe_screen")
    object CelebProfileScreen : Screen("celeb_profile_screen")
    object UserProfileScreen : Screen("user_profile_screen")
    object AddCelebScreen : Screen("add_celeb_screen")
    object UserAnalyticsScreen : Screen("user_analytics_screen")
    object AdminAnalyticsMenuScreen : Screen("admin_analytics_screen")
    object AdminOnlyScreen : Screen("admin_only_screen")
    object AddNewCompanyScreen : Screen("add_new_company_screen")
    object SearchScreen : Screen("search_screen")
    object AdminAnalyticsViewScreen : Screen("admin_analytics_view_screen")
    object AdminUserManagementScreen : Screen("admin_user_managment_screen")
    object SplashScreen : Screen("splash_screen")



    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
