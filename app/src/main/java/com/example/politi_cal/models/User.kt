package com.example.politi_cal.models

data class User(
    val userID: Int=0,
    val RoleID: Int=2,
    val name: String,
    val email: String,
    val password: String,
    var favorites: ArrayList<String>
) {
    constructor() : this(0, 2, "", "", "", ArrayList<String>())
    constructor(name: String, email: String, password: String, favorites: ArrayList<String>):
            //TODO: add the auto generator of the userID
            this(0, 0, name, email, password, favorites)
}
