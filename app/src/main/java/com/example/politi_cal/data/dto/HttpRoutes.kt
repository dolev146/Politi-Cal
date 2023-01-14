package com.example.politi_cal.data.dto

object HttpRoutes {
    // http://localhost:5000/api
    // https://politicalnodejs.onrender.com/api
    private const val BASE_URL = "http://localhost:5000/api"
    const val USERS = "$BASE_URL/users"
    const val VOTES = "$BASE_URL/votes"
    const val CELEBS = "$BASE_URL/celebs"
}