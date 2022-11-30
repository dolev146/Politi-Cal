package com.example.politi_cal.models

data class User(
    val userID: String,
    val RoleID: String,
    val name: String,
    val email: String,
    val password: String,
    var favorites: ArrayList<String>
) {
    constructor() : this("", "", "", "", "", ArrayList<String>())
    constructor(name: String, email: String, password: String, favorites: ArrayList<String>):
            //TODO: add the auto generator of the userID
            this("add in the future", "2", name, email, password, favorites)
}
