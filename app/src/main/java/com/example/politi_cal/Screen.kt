package com.example.politi_cal

sealed class Screen(val route: String) {
    object RegisterScreen : Screen("register_screen")
    object LoginScreen : Screen("login_screen")
    object PreferenceScreen : Screen("preference_screen")
    object SwipeScreen : Screen("swipe_screen")
    object CelebProfileScreen : Screen("celeb_profile_screen")
    object UserProfileScreen : Screen("user_profile_screen")
    object AddCelebScreen : Screen("add_celeb_screen")
    object AdminAnalyticsScreen : Screen("admin_analytics_screen")
    object AdminOnlyScreen : Screen("admin_only_screen")
    object AddNewCompanyScreen : Screen("add_new_company_screen")
    object SearchScreen : Screen("search_screen")


    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
