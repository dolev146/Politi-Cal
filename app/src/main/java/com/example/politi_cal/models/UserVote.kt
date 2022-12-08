package com.example.politi_cal.models

import com.google.firebase.firestore.Exclude

data class UserVote(
    @Exclude val RecordID: String,
    val UserID: String,
    val CelebID: String,
    val CategoryID: String,
    val CompanyID: String,
    val VoteID: String
)
