package com.example.politi_cal.models


data class Company(
    val name: String,
    val email: String,
    val password: String) {
    constructor() : this("", "", "")
}

fun main() {
    var c= Company()
    val c2= Company()
}
