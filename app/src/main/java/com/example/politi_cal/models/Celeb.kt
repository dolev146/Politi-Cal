package com.example.politi_cal.models

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime

data class Celeb(
    val celebID: String = "",
    val firstName: String = "",
    val lastName: String = "",
    var birthDate: LocalDate = LocalDate.parse("2022-01-01"),
    val celebDesc: String = "",
    val imageURL: String = "",
    val companyID: String = "",
    val interestID: String = "",
    val lastUpdate: LocalDateTime = LocalDateTime.now()
    )
