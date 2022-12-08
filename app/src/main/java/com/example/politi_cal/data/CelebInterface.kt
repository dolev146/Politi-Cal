package com.example.politi_cal.data

import com.example.politi_cal.models.Celeb

/**
 * This interface will be the interface of the Celeb object
 * Last update: 08/12/22 by Ofri Tavor
 */

interface CelebInterface {
    /**
     * This section will contain only the getters
     */
    fun getCelebID(): String
    fun getCompanyID(): String
    fun getName(): String
    fun getBirthDate(): String
    fun getImageURL(): String
    fun getInfo(): String
    fun getRightVotes(): Int
    fun getLeftVotes(): Int

    /**
     * This section will contain only the allowed setters
     */

    fun setBirthDate(newDate: Long)
    fun setName(newName: String)
    fun setImageURL(newURL: String)
    fun setInfo(newInfo: String)

    /**
     * This section will contain the Celeb control functions - CRUD
     */

    fun createCeleb(celebData: Celeb)
    fun getCelebByID(celebID: String)
    fun updateCeleb(celebData: Celeb)
    fun removeCeleb(celebID: String)
}