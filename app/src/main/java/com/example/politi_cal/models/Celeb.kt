package com.example.politi_cal.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
//TODO: After we add the db, we need to implement the auto generation of the userID
@Entity
data class Celeb(
    @PrimaryKey(autoGenerate = true)
    val celebID: Int=0,
    val firstName: String,
    val lastName: String,
    val celebDesc: String,
    val imageURL: String,
    val companyID: String,
    val interestID: String
    )
    {
        constructor(fname: String, lname: String, desc: String, url: String, compId: String, interId: String):
            this(firstName = fname,
            lastName=lname,
            celebDesc=desc,
            imageURL=url,
            companyID = compId,
            interestID = interId
            )

        constructor():
                this(firstName="ofri", lastName="tavor", celebDesc="", imageURL="", companyID="", interestID="")
    }

fun main() {

}
