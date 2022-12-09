package com.example.politi_cal.data.queries_Interfaces

import com.example.politi_cal.models.Category
import com.example.politi_cal.models.Celeb
import com.example.politi_cal.models.Company
import com.example.politi_cal.models.Party
import com.example.politi_cal.models.User
import com.example.politi_cal.models.UserVote
import com.example.politi_cal.models.VoteOption

/**
 * In order to save time and be efficient, we will query the DB on some ocassions in order to get
 * data that we might need later in order to allow better user experience.
 * Static Collection: A collection that contains documents that cannot change.
 * For example the Party collection document contain only ID and PartyName each, just like an
 * Enum class, therefore we can only use the ID to represent the document that contains the name
 * of the party.
 * This interface will contain the queries needed.
 */

/**
 * We decided to use Map instead of HashMap or TreeMap by using polymorphism and abstraction
 * in order to get better performance.
 * If we don't have many(about 100,000 for example) records in a collection, then we HashMap
 * to get better performance - O(1) for each action. But if we have many records then the amortized
 * time for an action will be O(n) when n is the number of records. In this case we might want
 * to reduce the running time for each action by using TreeMap, since each
 * action will take us O(log(n)). By using Map, an interface, we will be able to create the
 * map dynamically and the type will depend on the number of the records.
 * That way we can improve our performance.
 */

interface EarlyQueriesInterface {

    /**
     * This function will query the DB and get all of the records from Party collection, turn them
     * to objects and return a list of the objects
     * We will use this in order to set the UI in the user profile screen dynamically
     */

    fun getAllParties(): Map<String, Party>

    /**
     * This function will query the DB and get all of the records from Company collection, turn them
     * to objects and return a list of the objects
     * We will use it in order to set the UI in the analytics screen dynamically
     */

    fun getAllCompanies(): Map<String, Company>

    /**
     * This function will query the DB and get all of the records from Category collection,
     * turn them to objects and return a list of the objects
     * We will use this is order to set the UI analytics screen and user preferences screen
     * dynamically
     */

    fun getAllCategories(): Map<String, Category>

    /**
     * This function will query the DB and get all of the records from VoteOptions collection,
     * turn them to objects and return a list of the objects
     * We will use this in order to set the voting options in the UI dynamically
     */

    fun getAllVoteOptions(): Map<String, VoteOption>

    /**
     * This function will query the DB and get all of the records from Celeb collection,
     * turn them to objects and return a list of the objects
     * * We will use this in order to get the celebs according to the user preferences for the vote
     * screen
     */

    fun getCelebsByPreferences(userData: User): Map<String, Celeb>

    /**
     * This function will query the DB and get all of the records from VoteOptions collection,
     * turn them to objects and return a list of the objects

     */

    fun getAllCelebs(): Map<String, Celeb>

    /**
     * This function will query the DB and get all of the records from UserVotes collection,
     * turn them to objects and return a list of the objects
     * We will use this in order to know whether a user already vote for a celeb
     */

    fun getUserVotes(): Map<String, UserVote>

}