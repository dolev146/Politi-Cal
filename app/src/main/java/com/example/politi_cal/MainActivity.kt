package com.example.politi_cal

import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.politi_cal.DBObjects.AnalyticsQueriesObj
import com.example.politi_cal.DBObjects.EarlyQueriesObj
import com.example.politi_cal.DBObjects.UserVoteDBObj
import com.example.politi_cal.DBObjects.VoteOptionDBObj
import com.example.politi_cal.models.CallBack
import com.example.politi_cal.models.Celeb
import com.example.politi_cal.models.UserVote
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
                val e = EarlyQueriesObj()
                val celeb = Celeb("", "", "Omer Adam", 0, "", "", 70, 30)
                var callback = CallBack<Celeb, Map<String, Int>>(celeb)
                val s = AnalyticsQueriesObj()
                s.getCelebDistribution(callback)
                while(!callback.getStatus()){
                    continue
                }
                var output = "Left votes: " + callback.getOutput()!!["Left"] + "%\n"
                output+= "Right votes: " + callback.getOutput()!!["Right"] + "%"
                Toast.makeText(this, "Query Output $output", Toast.LENGTH_LONG).show()
                Navigation(auth = auth)
            }
        }
    }
}

