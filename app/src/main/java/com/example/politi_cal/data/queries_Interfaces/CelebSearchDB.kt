package com.example.politi_cal.data.queries_Interfaces

import com.example.politi_cal.db
import com.example.politi_cal.models.CallBack
import com.example.politi_cal.models.Celeb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CelebSearchDB {
    fun getCelebByName(callBack: CallBack<String, Celeb>)
    = CoroutineScope(Dispatchers.IO).launch {
        val result = db.collection("celebs")
            .document(callBack!!.getInput().toString())
            .get().await()
        if(result.exists()){
            val fname = result["firstName"].toString()
            val lname = result["lastName"].toString()
            val company = result["company"].toString()
            val category = result["category"].toString()
            val img = result["imgUrl"].toString()
            val info = result["celebInfo"].toString()
            val birthDate = Integer.parseInt(result["birthDate"].toString())
            val left = Integer.parseInt(result["leftVotes"].toString())
            val right = Integer.parseInt(result["rightVotes"].toString())
            var celeb = Celeb(Company = company, FirstName = fname, LastName = lname, BirthDate = birthDate.toLong(),
                ImgUrl = img, CelebInfo = info, Category = category, RightVotes = right.toLong(), LeftVotes = left.toLong())
            callBack.setOutput(celeb)
            callBack.Call()
        }
        else{
            callBack.setOutput(null)
            callBack.Call()
        }
    }
}