package com.example.politi_cal.models

/**
 * Roles:
 * 1 will represent a regular user.
 * 0 will represent an admin.
 * The system will automatically assign any registered user with 1 as a role.
 * Only the admin can change a user role.
 */

data class User(val userID: String ,
                val roleID: Int = 1, // 0 admin 1 user
                val favoritePartyID: String,
                val userName: String,
                val email: String,
                val registerDate: Long,
                var userPref: List<String>,
                var userGender: String,
                var userAge : String,
                )
