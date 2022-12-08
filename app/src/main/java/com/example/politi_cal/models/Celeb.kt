package com.example.politi_cal.models

import com.google.firebase.firestore.Exclude

data class Celeb(@Exclude val CelebID: String = "",
                 val companyID: String = "",
                 val Name: String = "",
                 val BirthDate: Long,
                 val imgUrl: String = "",
                 val celebInfo: String = "",
                 var rightVotes: Int=0,
                 var leftVotes: Int=0
)
