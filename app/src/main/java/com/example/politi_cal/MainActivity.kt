package com.example.politi_cal

import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.politi_cal.DBObjects.AdminAnalyticsQueriesDBObj
import com.example.politi_cal.DBObjects.AnalyticsQueriesObj
import com.example.politi_cal.DBObjects.EarlyQueriesObj
import com.example.politi_cal.DBObjects.UserVoteDBObj
import com.example.politi_cal.DBObjects.VoteOptionDBObj
import com.example.politi_cal.data.queries_Interfaces.CelebSearch
import com.example.politi_cal.models.CallBack
import com.example.politi_cal.models.Category
import com.example.politi_cal.models.Celeb
import com.example.politi_cal.models.Company
import com.example.politi_cal.models.UserVote
import com.example.politi_cal.models.VoteOption
import com.example.politi_cal.ui.theme.PolitiCalTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

public var user: FirebaseUser? = null

val db = Firebase.firestore
val userCollectionRef = db.collection("users")

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
//                val search = CelebSearch()
//                var callback = CallBack<Boolean, HashSet<Celeb>>(false)
//                search.searchCeleb(callback, "Omer", "ad")
//                while(!callback.getStatus()){
//                    continue
//                }
//                Toast.makeText(this, "hello world", Toast.LENGTH_LONG).show()
                val user = com.example.politi_cal.models.User("v@v.com", 1, "", "",
                    "", "", 2222, listOf(""))
                var callback = CallBack<com.example.politi_cal.models.User, ArrayList<Celeb>>(user)
                val queries = AnalyticsQueriesObj()
                queries.getUnVotedCelebsByUserID(callback)
                while(!callback.getStatus()){
                    continue
                }
                println("hello world")
                Navigation(auth = auth)
            }
        }
    }
}

