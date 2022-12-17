package com.example.politi_cal.models

data class UserVote(
    val UserEmail: String,
    val CelebFullName: String,
    val CategoryName: String,
    val CompanyName: String,
    val VoteDirection: String
)
