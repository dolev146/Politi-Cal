package com.example.politi_cal.data

import com.example.politi_cal.models.Category
import com.example.politi_cal.models.User

/**
 * This interface is the interface that the User object will implement.
 * Last update: 08/12/22 by Ofri Tavor
 */

interface UserInterface {

    /**
     * This section of the interface will contain the getters from the user object
     */

    fun getUserID(): String
    fun getUserName(): String
    fun getEmail(): String
    fun getBirthDate(): String
    fun getPartyID(): String
    fun getPartyName(): String
    fun getRoleID(): String
    fun getUserPreferences(): List<Category>

    /**
     * This section of the interface will contain the possible setters.
     * Special cases:
     * 1. We will allow to update the Email or Username if and only if it's not exist in the DB
     * already.
     * 2. We will allow only admin to set a new role for the user, the admin will have to verify
     * using his UserID in order to use this function, otherwise it will not work
     */

    fun setRoleID(adminUserID: String, newRoleID: String)
    fun setParty(newPartyID: String)
    fun setEmail(mail: String)
    fun setUserName(username: String)
    fun setPassword(password: String)

    /**
     * This section of the interface will contain the functions that deals with the user preferences
     */

    fun addPreference(pref: Category)
    fun containsPreference(pref: Category)
    fun removePreference(pref: Category)

    /**
     * This section of the interface will contain the user control function - CRUD
     */

    fun addUser(userData: User): Boolean
    fun getUserByUserID(UserID: String): User
    fun updateUser(userData: User): Boolean
    fun deleteUser(UserID: String): Boolean
}