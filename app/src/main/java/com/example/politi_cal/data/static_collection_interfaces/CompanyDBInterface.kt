package com.example.politi_cal.data.static_collection_interfaces

import com.example.politi_cal.models.Category
import com.example.politi_cal.models.Company

/**
 * This interface will contain the queries needed in order to communicate with the Company
 * collection in the DB.
 */


interface CompanyDBInterface {

    /**
     * We will actually use this functions by check if it the input is a key or a value in the
     * map that contains the data we already created.
     */

    fun isCompanyExist(company: Company): Boolean
    fun isCompanyExist(company: String): Boolean

    /**
     * We will use this function to add a new company to the DB.
     * We will add a new record to the DB if and only if the company is not already exist
     * and the party does exist in the DB.
     * If we use this functions, we will add the new company to the map of companies.
     *
     */

    fun addCompany(company: Company): Boolean
    fun addCompany(company: String, category: String): Boolean

    /**
     * We will use this function to update an exist company name in the DB.
     * We will update a company in the DB if and only if the original company already exist,
     * otherwise we will do nothing.
     * If we updated a record we will update the map as well.
     */

    fun updateCompany(original_company: Company, new_company: Company)
    fun updateCompany(original_company: String, new_company: String)

    /**
     * We will use this function to update an exist category ID in the DB.
     * We will update a company in the DB if and only if the original category
     * and the new category already exist, otherwise we will do nothing.
     */

    fun updateCategory(original_category: Category, new_category: Category)
    fun updateCategory(original_category: String, new_category: String)

    /**
     * We will use this function in order to delete a company from the DB.
     * We will delete a company from the DB if and only if the input company already
     * exist in the DB, otherwise we will do nothing.
     * Note: When we delete a company we Have to delete all of the related celebs as well.
     * If we deleted a company we will delete it from the map as well
     */

    fun deleteCompany(company: Company):Boolean
    fun deleteCompany(company: String):Boolean
}