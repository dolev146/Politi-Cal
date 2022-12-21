package com.example.politi_cal

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.politi_cal.models.*
import com.example.politi_cal.ui.theme.PolitiCalTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


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
                this.window.statusBarColor = Color(0xFFD7C488).toArgb()
                this.window.navigationBarColor = Color(0xFFD7C488).toArgb()
                Navigation(auth = auth)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // use the checkLoggedInState function to check if the user is logged in
        val value = checkLoggedInState(auth)
        //getCelebrities()
        if (value) {
            setContent {
                this.window.statusBarColor = Color(0xFFD7C488).toArgb()
                this.window.navigationBarColor = Color(0xFFD7C488).toArgb()
                Navigation(auth = auth, Screen.SwipeScreen.route)
            }
        } else {
            setContent {
                this.window.statusBarColor = Color(0xFFD7C488).toArgb()
                this.window.navigationBarColor = Color(0xFFD7C488).toArgb()
                Navigation(auth = auth)
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

