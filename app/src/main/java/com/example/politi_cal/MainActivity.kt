package com.example.politi_cal

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavController
import com.example.politi_cal.ui.theme.PolitiCalTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


val db = Firebase.firestore
val userCollectionRef = db.collection("users")
val celebCollectionRef = db.collection("celebs")

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
