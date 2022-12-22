package com.example.politi_cal.data.queries_Interfaces

import com.example.politi_cal.db
import com.example.politi_cal.models.CallBack
import com.example.politi_cal.models.Celeb
import com.example.politi_cal.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CelebSearchDB {

    fun getCelebByName(callBack: CallBack<String, MutableList<Celeb>>)
    = CoroutineScope(Dispatchers.IO).launch {
        val celebs = db.collection("celebs").get().await()
        if (celebs.documents.isNotEmpty()) {
            val foundCelebs = mutableListOf<Celeb>()
            for (celeb in celebs) {
                var category = celeb["category"].toString()
                val fname = celeb["firstName"].toString()
                val lname = celeb["lastName"].toString()
                var fullName = fname + " " + lname
                val birthdate = Integer.parseInt(celeb["birthDate"].toString())
                var company = celeb["company"].toString()
                val imgUrl = celeb["imgUrl"].toString()
                val left = Integer.parseInt(celeb["leftVotes"].toString())
                val right = Integer.parseInt(celeb["rightVotes"].toString())
                val info = celeb["celebInfo"].toString()
                var celeb = Celeb(
                    Company = company,
                    FirstName = fname,
                    LastName = lname,
                    BirthDate = birthdate.toLong(),
                    ImgUrl = imgUrl,
                    CelebInfo = info,
                    RightVotes = right.toLong(),
                    LeftVotes = left.toLong(),
                    Category = category
                )
                fullName = fullName.lowercase()
                company = company.lowercase()
                category = category.lowercase()
                var inputSearchFromUser = callBack.getInput()
                inputSearchFromUser = inputSearchFromUser!!.lowercase()
                if (category == inputSearchFromUser || company == inputSearchFromUser) {
                    foundCelebs.add(celeb)
                }
                else if (fullName.contains(inputSearchFromUser)) {
                    foundCelebs.add(celeb)
                }
            }
            callBack.setOutput(foundCelebs)
            callBack.Call()
        }
    }


    fun getUsersByEmail(callBack: CallBack<String, MutableList<User>>)
    = CoroutineScope(Dispatchers.IO).launch {
        val users = db.collection("users").get().await()
        if (users.documents.isNotEmpty()) {
            val foundCelebs = mutableListOf<Celeb>()
            for (user in users) {
                var email = user["email"].toString()
                var favoritePartyID = user["favoritePartyID"].toString()
                var registerDate = user["registerDate"].toString()
                var userAge = user["userAge"].toString()
                var userGender = user["userGender"].toString()
                var userPerf = user["userPerf"].toString()
                var user = User(
                    email = email,
                    favoritePartyID = favoritePartyID,
                    registerDate = registerDate,
                    userAge = userAge,
                    userGender = userGender,
                    userPerf = userPerf
                )

            }
            callBack.setOutput()
            callBack.Call()
        }
    }





}