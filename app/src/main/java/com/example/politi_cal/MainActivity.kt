package com.example.politi_cal

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.produceState


import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.politi_cal.data.dto.PostResponse
import com.example.politi_cal.data.dto.PostService
import com.example.politi_cal.models.*
import com.example.politi_cal.screens.analytics.PieChartData
import com.example.politi_cal.ui.theme.PolitiCalTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.response.*
import io.ktor.http.*
import kotlinx.coroutines.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.io.IOException


var isAdminState = false
val db = Firebase.firestore
val userCollectionRef = Firebase.firestore.collection("users")
val celebCollectionRef = Firebase.firestore.collection("celebs")
val companyCollectionRef = Firebase.firestore.collection("companies")
val categoriesCollectionRef = Firebase.firestore.collection("categories")

var companiesForAddCeleb = mutableListOf<Company>()
var companiesForAddCelebNames = mutableListOf<String>()

var categoriesForAddCeleb = mutableListOf<Category>()
var categoriesForAddCelebNames = mutableListOf<String>()

var celebListParam = mutableListOf<Celeb>()

val userVotesCollectionRef = Firebase.firestore.collection("userVotes")

val celebListFilterNames = mutableListOf<String>()

val checkTrue = DontCotinueUntillTrue()

var CelebForCelebProfile = Celeb(
    "", "", "", 0, "", "", "", 0, 0
)

var UserForUserProfile = User(
    favoritePartyID = "",
    userName = "",
    registerDate = 0,
    userAge = 19001010,
    email = "",
    userPref = listOf(""),
    userGender = "",
    userID = ""
)


var distribution = ArrayList<PieChartData>()
var adminDistribution = ArrayList<PieChartData>()
var adminAnalyticsTitle = ""
var updateChannelID = "update_channel"
var welcomeChannelID = "welcome_channel"
var welcomeChannelStatus = false
var updateChannelStatus = false
var notificationMap = HashMap<Int, Notification>()
var updatePref = false
var deleteUser = false


class MainActivity : ComponentActivity() {




    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PolitiCalTheme {
//                val users = produceState<List<PostResponse>>(
//                    initialValue = emptyList() ,
//                    producer = {
//                        value = service.getUsers()
//                    }
//                )
//                println(users.value)
//                println("users.value@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")




                this.window.statusBarColor = Color(0xFFD7C488).toArgb()
                this.window.navigationBarColor = Color(0xFFD7C488).toArgb()
                val value = checkLoggedInState(auth)

                if (value) {


                    var callback = CallBack<Boolean, Boolean>(false)
                    isAdminCheckNav(callback)
                    while (!callback.getStatus()) {
//                    Log.d("TAG", "onStart: waiting for callback")
                    }

                    if (isAdminState) {
                        Navigation(auth = auth, Screen.AdminAnalyticsMenuScreen.route)
                    } else {
                        Navigation(auth = auth)
                    }
                } else {
                    Navigation(auth = auth, Screen.LoginScreen.route)
                }

            }
        }
    }

    override fun onStart() {
        super.onStart()

        // use the checkLoggedInState function to check if the user is logged in
        val value2 = checkLoggedInState(auth)
        setNotificationMap()

        setContent {
            if (value2) {

                    this.window.statusBarColor = Color(0xFFD7C488).toArgb()
                    this.window.navigationBarColor = Color(0xFFD7C488).toArgb()

                    var callback = CallBack<Boolean, Boolean>(false)
                    isAdminCheckNav(callback)
                    while (!callback.getStatus()) {
//                    Log.d("TAG", "onStart: waiting for callback")
                    }
                    if (isAdminState) {
                        Navigation(auth = auth, Screen.AdminAnalyticsMenuScreen.route)
                    } else {
                        Navigation(auth = auth, Screen.SwipeScreen.route)
                    }


            } else {

                    this.window.statusBarColor = Color(0xFFD7C488).toArgb()
                    this.window.navigationBarColor = Color(0xFFD7C488).toArgb()
                    Navigation(auth = auth, Screen.LoginScreen.route)


            }
        }



    }

}


// write a function that check if user is logged in take in auth as a parameter and return a boolean
// if user is logged in return true else return false
fun checkLoggedInState(auth: FirebaseAuth): Boolean {
    if (auth.currentUser != null) {
        Log.d(MainActivity.TAG, "User is logged in")
        return true
    } else {
        Log.d(MainActivity.TAG, "User is not logged in")
        return false
    }
}


fun retrieveCompanies() = CoroutineScope(Dispatchers.IO).launch {
    try {
        val querySnapshot = companyCollectionRef.get().await()
        for (document in querySnapshot.documents) {
            val companyDocument = document
            val companyID = companyDocument.id
            val companyCategory = companyDocument.data?.get("category").toString()
            val companyObject = Company(companyID, companyCategory)
            companiesForAddCeleb.add(companyObject)
            companiesForAddCelebNames.add(companyID)
        }
        withContext(Dispatchers.Main) {

        }
    } catch (e: Exception) {
        withContext(Dispatchers.Main) {

        }
    }
}

fun retrieveCategories() = CoroutineScope(Dispatchers.IO).launch {
    try {
        val querySnapshot = categoriesCollectionRef.get().await()
        for (document in querySnapshot.documents) {
            val categoryDocument = document
            val categoryID = categoryDocument.id
            val categoryName = categoryDocument.data?.get("categoryName").toString()
            val categoryObject = Category(categoryID, categoryName)
            categoriesForAddCeleb.add(categoryObject)
            categoriesForAddCelebNames.add(categoryName)
        }
        withContext(Dispatchers.Main) {

        }
        Log.d("Categories", categoriesForAddCeleb.toString())
    } catch (e: Exception) {
        withContext(Dispatchers.Main) {

        }
        Log.d("Categories", "Error")
    }
}

fun retrieveCelebsByUserOfri(callBack: CallBack<Boolean, MutableList<Celeb>>) =
    CoroutineScope(Dispatchers.IO).launch {
        val auth = FirebaseAuth.getInstance()
        val userId = auth.currentUser?.email.toString()
        val uservotes = db.collection("userVotes").whereEqualTo("userEmail", userId).get().await()
        val userData = db.collection("users").document(userId).get().await()

        var prefs = HashSet<String>()
        if (userData.data != null) {
            for (document in userData.data?.entries!!) {
                if (document.key == "userPref") {
                    prefs.addAll(document.value as List<String>)
                }
            }
        }

        var voted_celebs = HashSet<String>()
        if (uservotes.documents.isNotEmpty()) {
            for (document in uservotes) {
                val celebId = document["celebFullName"].toString()
                voted_celebs.add(celebId)
            }
        }
        val celebs = db.collection("celebs").get().await()
        if (celebs.documents.isNotEmpty()) {
            val unvoted_celeb = ArrayList<Celeb>()
            for (celeb in celebs) {
                if (celeb.id in voted_celebs) {
                    continue
                }
                val category = celeb["category"].toString()
                if (!(category in prefs)) {
                    continue
                }
                val fname = celeb["firstName"].toString()
                val lname = celeb["lastName"].toString()
                val birthdate = Integer.parseInt(celeb["birthDate"].toString())
                val company = celeb["company"].toString()
                val imgUrl = celeb["imgUrl"].toString()
                val left = Integer.parseInt(celeb["leftVotes"].toString())
                val right = Integer.parseInt(celeb["rightVotes"].toString())
                val info = celeb["celebInfo"].toString()
                var celeb = Celeb(
                    Company = company,
                    FirstName = fname,
                    LastName = lname,
                    BirthDate = birthdate.toLong(),
                    ImgUrl = imgUrl,
                    CelebInfo = info,
                    RightVotes = right.toLong(),
                    LeftVotes = left.toLong(),
                    Category = category
                )
                unvoted_celeb.add(celeb)
            }
            callBack.setOutput(unvoted_celeb)
            callBack.Call()
        }
    }


fun retrieveCompanies(callback: CallBack<Boolean, Boolean>) =
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val querySnapshot = companyCollectionRef.get().await()
            for (document in querySnapshot.documents) {
                val companyDocument = document
                val companyID = companyDocument.id
                val companyCategory = companyDocument.data?.get("category").toString()
                val companyObject = Company(companyID, companyCategory)
                companiesForAddCeleb.add(companyObject)
                companiesForAddCelebNames.add(companyID)
            }
            callback.setOutput(true)
            callback.Call()
            withContext(Dispatchers.Main) {

            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {

            }
        }
    }

fun retrieveCategories(callback: CallBack<Boolean, Boolean>) =
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val querySnapshot = categoriesCollectionRef.get().await()
            for (document in querySnapshot.documents) {
                val categoryDocument = document
                val categoryID = categoryDocument.id
                val categoryName = categoryDocument.data?.get("categoryName").toString()
                val categoryObject = Category(categoryID, categoryName)
                categoriesForAddCeleb.add(categoryObject)
                categoriesForAddCelebNames.add(categoryName)
            }
            callback.setOutput(true)
            callback.Call()
            withContext(Dispatchers.Main) {

            }
            Log.d("Categories", categoriesForAddCeleb.toString())
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {

            }
            Log.d("Categories", "Error")
        }
    }


fun isAdminCheckNav(callback: CallBack<Boolean, Boolean>) = CoroutineScope(Dispatchers.IO).launch {
    try {

        val auth = FirebaseAuth.getInstance()
        val userId = auth.currentUser?.email.toString()
        val userData = db.collection("users").document(userId).get().await()
        if (userData.data != null) {
            for (document in userData.data?.entries!!) {
                if (document.key == "roleID") {
                    if (document.value.toString() == "0") {
                        isAdminState = true
                        callback.setOutput(true)
                        callback.Call()
                    } else {
                        isAdminState = false
                        callback.setOutput(false)
                        callback.Call()
                    }
                }
            }

            UserForUserProfile = User(
                email = userData["email"].toString(),
                userID = userData["userID"].toString(),
                favoritePartyID = userData["favoritePartyID"].toString(),
                userName = userData["userName"].toString(),
                registerDate = userData["registerDate"].toString().toLong(),
                userPref = userData["userPref"] as List<String>,
                userGender = userData["userGender"].toString(),
                userAge = userData["userAge"].toString().toLong(),
                roleID = userData["roleID"].toString().toInt()
            )

        }
    } catch (e: Exception) {
        withContext(Dispatchers.Main) {

        }
    }
}

@Composable
private fun SendWelcomeNotification(notificationId: Int, titleText: String, notificationText: String){
    if(!welcomeChannelStatus) {
        createNotificationChannel(welcomeChannelID, LocalContext.current)
        welcomeChannelStatus = true
    }
    val myBitmap = BitmapFactory.decodeResource(LocalContext.current.resources, R.drawable.app_logo)
    showSimpleNotificationWithTapAction(
        LocalContext.current,
        welcomeChannelID,
        notificationId,
        titleText,
        notificationText
    )
}

@Composable
private fun SendUpdateNotification(notificationId: Int, titleText: String, notificationText: String){
    if(!updateChannelStatus) {
        createNotificationChannel(welcomeChannelID, LocalContext.current)
        updateChannelStatus = true
    }
    val myBitmap = BitmapFactory.decodeResource(LocalContext.current.resources, R.drawable.app_logo)
    showSimpleNotificationWithTapAction(
        LocalContext.current,
        updateChannelID,
        notificationId,
        titleText,
        notificationText
    )
}

@Composable
fun SendWelcomeNotification(notification: Notification){
    if(!welcomeChannelStatus) {
        createNotificationChannel(welcomeChannelID, LocalContext.current)
        welcomeChannelStatus = true
    }
    val once = notification.getOnce()
    val times = notification.getTimes()
    if(once){
        if(times == 1){
            notification.setTimes(0)
        }
        else{
            return
        }
    }
    SendWelcomeNotification(
        notificationId = notification.getID(),
        titleText = notification.getTitle(),
        notificationText = notification.getText()
    )
}

@Composable
fun SendUpdateNotification(notification: Notification){
    if(!updateChannelStatus) {
        createNotificationChannel(updateChannelID, LocalContext.current)
        updateChannelStatus = true
    }
    val once = notification.getOnce()
    val times = notification.getTimes()
    if(once){
        if(times == 1){
            notification.setTimes(0)
        }
        else{
            return
        }
    }
    val myBitmap = BitmapFactory.decodeResource(LocalContext.current.resources, R.drawable.app_logo)
    SendUpdateNotification(
        notificationId = notification.getID(),
        titleText = notification.getTitle(),
        notificationText=notification.getText()
    )
}


fun createNotificationChannel(channelId: String, context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = channelId
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, name, importance)
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}


// shows a simple notification with a tap action to show an activity
@SuppressLint("MissingPermission")
fun showSimpleNotificationWithTapAction(
    context: Context,
    channelId: String,
    notificationId: Int,
    textTitle: String,
    textContent: String,
    priority: Int = NotificationCompat.PRIORITY_DEFAULT
) {
    val intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    val pendingIntent: PendingIntent =PendingIntent.getActivity(
        context, 1, intent,
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_IMMUTABLE
        } else {
            0
        }
    )

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.app_logo)
        .setContentTitle(textTitle)
        .setContentText(textContent)
        .setPriority(priority)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)

    with(NotificationManagerCompat.from(context)) {
        notify(notificationId, builder.build())
    }
}

fun setNotificationMap(){
    var welcome = Notification(true,
        1,
        "Welcome to Politi-Cal!",
        "Tap here to enter the app!",
        welcomeChannelID,
        0)
    var welcomeAdmin = Notification(true,
    1,
    "Welcome Admin!",
    "Tap here to enter the admin zone",
    welcomeChannelID,
    1)
    var updatePref = Notification(false,
        1,
        "Preferences update",
        "You updated your preferences successfully",
        updateChannelID,
        2)
    var deleteUser = Notification(false,
        1,
        "Goodbye",
        "You deleted your user :( Come again later to check out new updates",
        updateChannelID,
        3)
    notificationMap[0] = welcome
    notificationMap[1] = welcomeAdmin
    notificationMap[2] = updatePref
    notificationMap[3] = deleteUser
}

suspend fun sendPostRequest(json : String, url : String) : String {
    val client = OkHttpClient()
    val body = json.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    val request = Request.Builder()
        .url(url)
        .post(body)
        .build()
    val response = client.newCall(request).execute()
    return response.body!!.string()
}

//        val api = retrofit.create(MyApi::class.java)
//        var stingof = "{\"email\":\"yaakov103@gmail.com\"}"
//        var body = EmailBody(email)
//
//        println(api)
//        // POST request the gmail to api
//        val response = api.postEmail(body)


//    val json = """
//    {
//        "email": "${email}"
//    }
//"""
//    val body = json.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
//    val request = Request.Builder()
//        .url("https://politicalnodejs.onrender.com/api/celebs")
//        .post(body)
//        .build()
//    val client = OkHttpClient()
//    println(body.toString())
//    println(request)
//    client.newCall(request).enqueue(object : Callback {
//        override fun onFailure(call: Call, e: IOException) {
//          println("Failed to execute request")
//        }
//        override fun onResponse(call: Call, response: Response) {
//            println("Response received")
//            println(response.body?.string())
//        }
//    })


fun sendEmailToServer(email: String){
    CoroutineScope(Dispatchers.IO).launch {
        sendPostRequest(email,"https://politicalnodejs.onrender.com/api/celebs")
    }
}