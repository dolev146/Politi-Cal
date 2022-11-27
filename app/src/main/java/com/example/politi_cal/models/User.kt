package com.example.politi_cal.models

data class User(val name: String, val email: String, val password: String) {
    constructor() : this("", "", "")
}
