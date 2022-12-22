package com.example.politi_cal.DBObjects

import com.example.politi_cal.data.queries_Interfaces.AdminAnalyticsQueriesInterface
import com.example.politi_cal.db
import com.example.politi_cal.models.CallBack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.threeten.bp.LocalDate

class AdminAnalyticsQueriesDBObj: AdminAnalyticsQueriesInterface {
    override fun getAgeDistribution(callback: CallBack<Boolean, Map<String, Double>>)
            = CoroutineScope(Dispatchers.IO).launch{
        val age_groups_list = listOf("18-25", "26-32", "33-40", "41-50", "51-60", "61-70",
            "71-80", "More than 81")
        var age_group_map = HashMap<String, Int>() //change to long
        for(age_group in age_groups_list){
            age_group_map[age_group] = 0
        }
        val users = db.collection("users")
            .get().await()

        if(users.documents.isNotEmpty()){
            for(user in users){
                val age = getAge(Integer.parseInt(user["userAge"].toString()))
                for (age_group in age_groups_list){
                    if(age > 80 && age_group == "More than 81"){
                        age_group_map[age_group] = age_group_map[age_group] !!+ 1
                        break
                    }
                    val group_split = age_group.split("-")
                    val min = Integer.parseInt(group_split[0])
                    val max = Integer.parseInt(group_split[1])
                    if(age >= min && age <= max){
                        age_group_map[age_group] = age_group_map[age_group] !!+ 1
                        break
                    }
                }
            }
            var outmap = HashMap<String, Double>()
            for (age_group in age_groups_list){
                val dist = (age_group_map[age_group]!!.toDouble() !!/ users.size()).toDouble()
                outmap[age_group] = dist
            }
            callback.setOutput(outmap)
            callback.Call()
        }
    }

    fun getAge(date: Int): Int {
        val date_year = Integer.parseInt(date.toString().substring(0, 4))
        val date_month = Integer.parseInt(date.toString().substring(4, 6))
        val date_day = Integer.parseInt(date.toString().substring(6, 8))
        return LocalDate.now().minusYears(date_year.toLong())
            .minusMonths(date_month.toLong())
            .minusDays(date_day.toLong()).year
    }

    override fun getPartiesDistribution(callback: CallBack<Boolean, Map<String, Double>>)
            = CoroutineScope(Dispatchers.IO).launch{
        val users = db.collection("users").get().await()
        val parties = db.collection("parties").get().await()
        var parties_map = HashMap<String, Int>()
        if(parties.documents.isNotEmpty()){
            for(party in parties){
                parties_map[party.id.toString()] = 0
            }
        }
        var size = 0
        if(users.documents.isNotEmpty()){
            for (user in users){
                val favorite = user["favoritePartyID"].toString()
                parties_map[favorite] = parties_map[favorite] !!+ 1
                size += 1
            }
            var outmap = HashMap<String, Double>()
            for(party in parties_map.keys){
                val count = Integer.parseInt(parties_map[party].toString())
                var value = (count.toDouble() / size.toDouble())
                outmap[party] = value
            }
            callback.setOutput(outmap)
            callback.Call()
        }
    }

    override fun getNumberOfUsersByTime(callBack: CallBack<Int, Int>)
            = CoroutineScope(Dispatchers.IO).launch {
        val result = db.collection("users")
            .whereGreaterThanOrEqualTo("registerDate", callBack.getInput() as Int)
            .get().await()
        if(result.documents.isNotEmpty()){
            callBack.setOutput(result.size())
            callBack.Call()
        }
    }

    override fun getNumberOfUsersByTime(start: Long, end: Long, callBack: CallBack<Int, Int>)
            = CoroutineScope(Dispatchers.IO).launch{
        val result = db.collection("users")
            .whereGreaterThanOrEqualTo("registerDate", start)
            .whereLessThanOrEqualTo("registerDate", end)
            .get().await()
        if(result.documents.isNotEmpty()){
            callBack.setOutput(result.size())
            callBack.Call()
        }
    }

    override fun getNumberOfUsersByYear(callBack: CallBack<Int, Int>)
            = CoroutineScope(Dispatchers.IO).launch{
        val result = db.collection("users")
            .orderBy("registerDate")
            .get().await()
        if(result.documents.isNotEmpty()){
            var users_in_year = 0
            val wanted_year = Integer.parseInt(callBack.getInput().toString())
            for(user in result){
                val long_date = user["registerDate"]
                val year = Integer.parseInt(long_date.toString().substring(0, 4))
                if(year < wanted_year){
                    continue
                }
                else if(year > wanted_year){
                    break
                }
                else{
                    users_in_year += 1
                }
            }
            callBack.setOutput(users_in_year)
            callBack.Call()
        }
    }

    override fun getNumberOfUsersByYear_MonthBasedData(callBack: CallBack<Int, Map<Int, Int>>)
            = CoroutineScope(Dispatchers.IO).launch{
        var year = callBack.getInput() !!* 10000
        val result = db.collection("users")
            .whereGreaterThanOrEqualTo("registerDate", year)
            .orderBy("registerDate")
            .get().await()
        if(result.documents.isNotEmpty()){
            var map = HashMap<Int, Int>()
            for(user in result){
                val long_date = user["registerDate"]
                val register_year = Integer.parseInt(long_date.toString().substring(0, 3))
                val register_month = Integer.parseInt(long_date.toString().substring(4, 5))
                if(!map.containsKey(register_month)){
                    map[register_month] = 0
                }
                if(callBack.getInput() !!< register_year){
                    break
                }
                if(callBack.getInput() == register_year){
                    map[register_month] = map[register_month] !!+ 1
                }
            }
            callBack.setOutput(map)
            callBack.Call()
        }
    }
}