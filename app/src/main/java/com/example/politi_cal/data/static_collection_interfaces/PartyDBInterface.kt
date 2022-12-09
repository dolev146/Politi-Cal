package com.example.politi_cal.data.static_collection_interfaces

import com.example.politi_cal.models.Company
import com.example.politi_cal.models.Party

/**
 * This interface will contain the queries needed in order to communicate with the Party
 * collection in the DB.
 */

interface PartyDBInterface {

    /**
     * We will actually use this functions by check if it the input is a key or a value in the
     * map that contains the data we already created.
     */

    fun isPartyExist(party: Party): Boolean
    fun isPartyExist(party: String): Boolean

    /**
     * We will use this function to add a new party to the DB.
     * We will add a new record to the DB if and only if the company is not already exist
     * and the party does exist in the DB.
     * If we use this functions, we will add the new party to the map of parties.
     *
     */

    fun addParty(party: Party): Boolean
    fun addParty(party: String): Boolean

    /**
     * We will use this function to update an exist party name in the DB.
     * We will update a party in the DB if and only if the original party already exist,
     * otherwise we will do nothing.
     * If we updated a record we will update the map as well.
     */

    fun updateParty(original_party: Party, new_party: Company)
    fun updateParty(original_party: String, new_party: String)

    /**
     * We will use this function in order to delete a party from the DB.
     * We will delete a company from the DB if and only if the input party already
     * exist in the DB, otherwise we will do nothing.
     * Note: When we delete a party we Have to delete it from all of the users who tagged it as
     * their favorites.
     * If we deleted a party we will delete it from the map as well
     */

    fun deleteParty(party: Party):Boolean
    fun deleteParty(party: String):Boolean


}