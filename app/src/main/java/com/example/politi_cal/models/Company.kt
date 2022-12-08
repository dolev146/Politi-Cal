package com.example.politi_cal.models

import com.google.firebase.firestore.Exclude


data class Company(@Exclude val companyID: String,
                   val categoryID: String,
                   val companyName: String) {
    constructor() : this("", "", "")
}
