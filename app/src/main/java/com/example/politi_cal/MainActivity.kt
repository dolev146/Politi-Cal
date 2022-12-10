package com.example.politi_cal

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.politi_cal.DBObjects.VoteOptionDBObj
import com.example.politi_cal.models.VoteOption
import com.example.politi_cal.ui.theme.PolitiCalTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.android.awaitFrame

public var user :FirebaseUser? = null

val db = Firebase.firestore
val userCollectionRef = db.collection("users")

class MainActivity : ComponentActivity() {



    companion object {
        val TAG : String = MainActivity::class.java.simpleName
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
}

