package com.example.politi_cal.data.static_collection_interfaces

import com.example.politi_cal.models.Category

/**
 * This interface will contain the queries needed in order to communicate with the Category
 * collection in the DB.
 */

interface CategoryDBInterface {

    /**
     * We will actually use this functions by check if it the input is a key or a value in the
     * map that contains the data we already created.
     */

    fun isCategoryExist(category: Category): Boolean
    fun isCategoryExist(category: String): Boolean

    /**
     * We will use this function to add a new category to the DB.
     * We will add a new record to the DB if and only if the category is not already exist.
     * If we use this functions, we will add the new category to the map of categories.
     */

    fun addCategory(category: Category): Boolean
    fun addCategory(category: String): Boolean

    /**
     * We will use this function to update an exist category name in the DB.
     * We will update a category in the DB if and only if the original category already exist,
     * otherwise we will do nothing.
     * If we updated a record we will update the map as well.
     */

    fun updateCategory(original_category: Category, new_category: Category)
    fun updateCategory(original_category: String, new_category: String)

    /**
     * We will use this function in order to delete a category from the DB.
     * We will delete a category from the DB if and only if the input category already
     * exist in the DB, otherwise we will do nothing.
     * Note: When we delete a Category we Have to delete all of the related companies as well.
     * If we deleted a category we will delete it from the map as well
     */

    fun deleteCategory(category: Category):Boolean
    fun deleteCategory(category: String):Boolean


}