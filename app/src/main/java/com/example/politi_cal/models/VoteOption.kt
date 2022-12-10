package com.example.politi_cal.models

import com.google.firebase.firestore.Exclude

data class VoteOption(
    @Exclude val voteID: String,
    val voteDescription: String
)
