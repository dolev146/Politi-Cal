package com.example.politi_cal.data.object_interfaces

import com.example.politi_cal.models.UserVote

/**
 * This function will be the UserVote object interface
 */

interface UserVoteInterface {
    /**
     * This section of the interface will contain only getters
     */

    fun getRecoredID(): String
    fun getUserID(): String
    fun getCelebID(): String
    fun getCompanyID(): String
    fun getCategoryID(): String
    fun getVoteID(): String
}