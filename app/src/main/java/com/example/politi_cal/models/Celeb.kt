package com.example.politi_cal.models


data class Celeb(
    var Company: String,
    var FirstName: String,
    var LastName: String,
    val BirthDate: Long,
    var ImgUrl: String,
    val CelebInfo: String,
    val Category: String,
    var RightVotes: Long = 0,
    var LeftVotes: Long = 0
)
