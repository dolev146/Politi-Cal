package com.example.politi_cal.models

data class SearchPost(val name: String, val email: String, val password: String) {
    constructor() : this("", "", "")
}
