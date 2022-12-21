package com.example.politi_cal.data.queries_Interfaces

import androidx.compose.runtime.mutableStateListOf
import com.example.politi_cal.db
import com.example.politi_cal.models.CallBack
import com.example.politi_cal.models.Celeb
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
                val category = celeb["category"].toString()
                val fname = celeb["firstName"].toString()
                val lname = celeb["lastName"].toString()
                var fullName = fname + " " + lname
                fullName = fullName.lowercase()
                val birthdate = Integer.parseInt(celeb["birthDate"].toString())
                val company = celeb["company"].toString()
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
                if (fullName.contains(callBack.getInput()!!.lowercase())) {
                    foundCelebs.add(celeb)
                }
            }
            callBack.setOutput(foundCelebs)
            callBack.Call()
        }



//        val result = db.collection("celebs")
//            .document(callBack!!.getInput().toString())
//            .get().await()
//        if(result.exists()){
//            val fname = result["firstName"].toString()
//            val lname = result["lastName"].toString()
//            val company = result["company"].toString()
//            val category = result["category"].toString()
//            val img = result["imgUrl"].toString()
//            val info = result["celebInfo"].toString()
//            val birthDate = Integer.parseInt(result["birthDate"].toString())
//            val left = Integer.parseInt(result["leftVotes"].toString())
//            val right = Integer.parseInt(result["rightVotes"].toString())
//            var celeb = Celeb(Company = company, FirstName = fname, LastName = lname, BirthDate = birthDate.toLong(),
//                ImgUrl = img, CelebInfo = info, Category = category, RightVotes = right.toLong(), LeftVotes = left.toLong())
//            callBack.setOutput(celeb)
//            callBack.Call()
//        }
//        else{
//            callBack.setOutput(null)
//            callBack.Call()
//        }
    }
}