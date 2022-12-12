package com.example.politi_cal.models

import com.google.firebase.firestore.Exclude

data class Party(
    @Exclude val PartyID: String,
    val PartyName: String
    ){


}
