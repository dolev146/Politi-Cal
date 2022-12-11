package com.example.politi_cal

import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.politi_cal.DBObjects.UserVoteDBObj
import com.example.politi_cal.DBObjects.VoteOptionDBObj
import com.example.politi_cal.models.CallBack
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
                val u = UserVote("", "Ee41G8VlE36dV3BlA9df",
                    "QQLBE8yKYcqM5kDsPebc",
                "P5Gt1vfwlPuaXE4rbe4Z", "wWbzw9bKz8ClanToPpB0",
                "6ito58lCU26sQa2AhH1o")
                val v = UserVoteDBObj(this)
//                v.deleteVote(u)
                v.vote(u)
//                v.deleteAllVotesByUserID(u)
//                val u2 = UserVote("", "Ee41G8VlE36dV3BlA9df",
//                    "QQLBE8yKYcqM5kDsPebc",
//                    "P5Gt1vfwlPuaXE4rbe4Z", "wWbzw9bKz8ClanToPpB0",
//                    "qNqDuavPCv5Wdq8sSwzf")
//                v.updateVote(u2)
                Navigation(auth = auth)
            }
        }
    }
}

