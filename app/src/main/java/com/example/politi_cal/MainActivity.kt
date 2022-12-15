package com.example.politi_cal

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.politi_cal.models.Category
import com.example.politi_cal.models.Company
import com.example.politi_cal.ui.theme.PolitiCalTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


//val db = Firebase.firestore
val userCollectionRef = Firebase.firestore.collection("users")
val celebCollectionRef = Firebase.firestore.collection("celebs")
val companyCollectionRef = Firebase.firestore.collection("companies")
val categoriesCollectionRef = Firebase.firestore.collection("categories")

var companiesForAddCeleb = mutableListOf<Company>()
var companiesForAddCelebNames = mutableListOf<String>()

var categoriesForAddCeleb = mutableListOf<Category>()
var categoriesForAddCelebNames = mutableListOf<String>()


class MainActivity : ComponentActivity() {


    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retrieveCompanies()
        setContent {
            PolitiCalTheme {
                Navigation(auth = auth)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // use the checkLoggedInState function to check if the user is logged in
        val value = checkLoggedInState(auth)
        if (value) {
            setContent {
                Navigation(auth = auth, Screen.SwipeScreen.route)
            }
        } else {
            setContent {
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
    } catch (e: Exception) {
        withContext(Dispatchers.Main) {

        }
    }
}


