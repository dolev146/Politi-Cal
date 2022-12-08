package com.example.politi_cal.models

import com.google.firebase.firestore.Exclude

data class User(@Exclude val userID: String = "",
                val roleID: Int = 1,
                val favoritePartyID: String,
                val userName: String,
                val email: String,
                val password: String,
                val registerDate: Long,
                var userPref: List<String>
                ) {

}
