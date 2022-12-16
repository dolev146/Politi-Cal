package com.example.politi_cal.data.queries_Interfaces

import com.example.politi_cal.models.CallBack
import kotlinx.coroutines.Job

/**
 * This interface will contain analytics queries that relevant to the admin
 *
 */

interface AdminAnalyticsQueriesInterface {

    /**
     * This function will return a distribution of age groups of that represent the users
     * of the app
     */

    fun getAgeDistribution(callback: CallBack<Boolean, Map<String, Double>>): Job

    /**
     * This function will return a distribution of the favorite party of the users of the app
     */

    fun getPartiesDistribution(callback: CallBack<Boolean, Map<String, Double>>): Job

    /**
     * This query will get get the number of users registered to the app during specific time.
     * It will get the first date and will return how many registered since then
     */

    fun getNumberOfUsersByTime(callBack: CallBack<Int, Int>): Job

    /**
     * This query will get get the number of users registered to the app during specific time.
     * It will get the first date and the last date and will return how many
     * registered since the first date till the last date
     */

    fun getNumberOfUsersByTime(start: Long, end: Long, callBack: CallBack<Int, Int>):Job

    /**
     * This function will get the number of users that registered in a specific year
     */

    fun getNumberOfUsersByYear(callBack: CallBack<Int, Int>):Job
    /**
     * This function will get the number of users that registered in a given years and will
     * return a map that contains how many users registered in each month.
     */

    fun getNumberOfUsersByYear_MonthBasedData(callBack: CallBack<Int, Map<Int, Int>>):Job

}