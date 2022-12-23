package com.example.politi_cal.data.queries_Interfaces


import com.example.politi_cal.models.CallBack
import com.example.politi_cal.models.UserVote
import kotlinx.coroutines.Job

interface UserVoteDBInterface {
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

    fun vote(userVote: UserVote): Job

    /**
     * This function will get a userVote object and will check if
     * the user already voted for the celeb
     * This function is a private function.
     */

    fun alreadyVoted(userVote: UserVote, callBack: CallBack<UserVote, Boolean>): Job

    /**
     * This function will add a new vote to the DB
     * This is a private function.
     */

    fun addVote(userVote: UserVote)

    /**
     * This function will update the current record in the DB according to the new vote
     * This is a private function
     */

    fun updateVote(userVote: UserVote): Job

    /**
     * This function will delete a record of a vote from the DB.
     * This function will occur only when the admin will delete a user.
     * Therefore
     */

    fun deleteVote(userVote: UserVote): Job

    /**
     * This function removes all of the votes of specific user
     */

    fun deleteAllVotesByUserID():Job

    /**
     * This function removes all of the votes of specific company
     */

    fun deleteAllVotesByCompanyID(userVote: UserVote):Job

    /**
     * This function removes all of the votes of specific category
     */

    fun deleteAllVotesByCategoryID(userVote: UserVote):Job

    /**
     * This function removes all of the votes of specific vote
     */

    fun deleteAllVotesByVoteID(userVote: UserVote):Job
}