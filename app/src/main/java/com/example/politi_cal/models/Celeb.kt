package com.example.politi_cal.models


data class Celeb(
                 val Company: String ,
                 val FirstName: String ,
                 val LastName: String ,
                 val BirthDate: Long,
                 val ImgUrl: String ,
                 val CelebInfo: String ,
                 val Category: String,
                 var RightVotes: Int = 0,
                 var LeftVotes: Int = 0
)
