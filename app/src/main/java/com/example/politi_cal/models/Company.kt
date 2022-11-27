package com.example.politi_cal.models


data class Company(val name: String, val email: String, val password: String) {
    constructor() : this("", "", "")
}
