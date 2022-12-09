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

    /**
     * This section will contain the CRUD functions
     */

    /**
     * This function will divide to two parts.
     * 1. If the user already vote for the celeb:
     *  1.1 If the vote is different than the current vote - update it
     *  1.2 Otherwise - do nothing
     * 2. Otherwise - add it to the DB
     */

    fun vote(userVote: UserVote)

    /**
     * This function will get a userVote object and will check if
     * the user already voted for the celeb
     * This function is a private function.
     */

    fun alreadyVoted(userVote: UserVote): Boolean

    /**
     * This function will add a new vote to the DB
     * This is a private function.
     */

    fun addVote(userVote: UserVote)

    /**
     * This function will update the current record in the DB according to the new vote
     * This is a private function
     */

    fun updateVote(userVote: UserVote)

    /**
     * This function will delete a record of a vote from the DB.
     * This function will occur only when the admin will delete a user.
     * Therefore
     */

    fun deleteVote(userVote: UserVote)

}