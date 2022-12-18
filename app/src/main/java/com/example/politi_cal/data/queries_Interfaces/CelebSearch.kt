package com.example.politi_cal.data.queries_Interfaces

import androidx.compose.ui.geometry.CornerRadius
import com.example.politi_cal.db
import com.example.politi_cal.models.CallBack
import com.example.politi_cal.models.Celeb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CelebSearch {
    fun searchCeleb(callBack: CallBack<Boolean, HashSet<Celeb>>,fname: String, lname: String)
    = CoroutineScope(Dispatchers.IO).launch{
        val result = db.collection("celebs")
            .whereGreaterThanOrEqualTo("firstName", fname)
//            .whereGreaterThanOrEqualTo("lastName", listOf(lname))
            .get().await()
        if(result.documents.isNotEmpty()){
            var outset = HashSet<Celeb>()
            for(celeb in result){
                val celebid = celeb.id
                var celeb_fname = celeb["firstName"].toString()
                if(!celeb_fname.startsWith(fname)){
                    continue
                }
                var celeb_lname = celeb["lastName"].toString()
                val celeb_name = celeb_fname + " " + celeb_lname
                val celeb_info = celeb["celebInfo"].toString()
                val image_url = celeb["imgUrl"].toString()
                val company = celeb["company"].toString()
                val left = Integer.parseInt(celeb["leftVotes"].toString())
                val right = Integer.parseInt(celeb["rightVotes"].toString())
                val bdate = Integer.parseInt(celeb["birthDate"].toString())
                var out_celeb = Celeb(celebid, company, celeb_name,
                    bdate.toLong(), image_url, celeb_info, right, left)
                outset.add(out_celeb)
            }
            callBack.setOutput(outset)
            callBack.Call()
        }
    }
}