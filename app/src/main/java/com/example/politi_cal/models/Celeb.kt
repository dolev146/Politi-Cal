package com.example.politi_cal.models


data class Celeb(
                 val Company: String ,
                 val FirstName: String ,
                 val LastName: String ,
                 val BirthDate: Long,
                 val ImgUrl: String ,
                 val CelebInfo: String ,
                 val Category: String,
                 var RightVotes: Long = 0,
                 var LeftVotes: Long = 0
)
