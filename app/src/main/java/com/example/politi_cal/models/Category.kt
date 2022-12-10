package com.example.politi_cal.models

import com.google.firebase.firestore.Exclude

data class Category(@Exclude val categoryID: String,
                    val categoryName: String)
