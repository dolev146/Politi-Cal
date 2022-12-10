package com.example.politi_cal.data.static_collection_interfaces

import com.example.politi_cal.models.Company
import com.example.politi_cal.models.Party
import com.example.politi_cal.models.VoteOption
import kotlinx.coroutines.Job

/**
 * This interface will contain the queries needed in order to communicate with the VoteOption
 * collection in the DB.
 */


interface VoteOptionDBInterface {

    /**
     * We will actually use this functions by check if it the input is a key or a value in the
     * map that contains the data we already created.
     */

    fun isVoteExist(option: VoteOption): Boolean
    fun isVoteExist(option: String): Boolean

    /**
     * We will use this function to add a new vote option to the DB.
     * We will add a new record to the DB if and only if the company is not already exist
     * and the party does exist in the DB.
     * If we use this functions, we will add the new vote option to the map of vote options.
     *
     */

    fun addVoteOption(option: VoteOption): Job
    fun addVoteOption(option: String): Boolean

    /**
     * We will use this function to update an exist vote option name in the DB.
     * We will update a vote option in the DB if and only if the original vote option already exist,
     * otherwise we will do nothing.
     * If we updated a record we will update the map as well.
     */

    fun updateVoteOption(original_option: VoteOption, new_option: VoteOption): Job
    fun updateVoteOption(original_option: String, new_option: String): Job

    /**
     * We will use this function in order to delete a vote option from the DB.
     * We will delete a vote option from the DB if and only if the input vote option already
     * exist in the DB, otherwise we will do nothing.
     * Note: When we delete a vote option we Have to delete all of the votes
     * that voted for this option.
     * If we deleted a vote option we will delete it from the map as well
     */

    fun deleteVoteOption(vote: VoteOption): Job
    fun deleteVoteOption(vote: String):Job



}