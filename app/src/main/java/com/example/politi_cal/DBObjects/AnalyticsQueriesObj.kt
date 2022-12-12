package com.example.politi_cal.DBObjects

import com.example.politi_cal.data.queries_Interfaces.AnalyticsQueriesInterface
import com.example.politi_cal.db
import com.example.politi_cal.models.CallBack
import com.example.politi_cal.models.Category
import com.example.politi_cal.models.Celeb
import com.example.politi_cal.models.Company
import com.example.politi_cal.models.VoteOption
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AnalyticsQueriesObj: AnalyticsQueriesInterface {
    override fun getCelebDistribution(callBack: CallBack<Celeb, Map<String, Int>>)
    = CoroutineScope(Dispatchers.IO).launch{
        val result = db.collection("celebs")
            .whereEqualTo("CelebName", callBack.getInput()!!.Name)
            .get().await()
        if(result.documents.isNotEmpty()){
            var map = HashMap<String, Int>()
            for(document in result){
                var left = Integer.parseInt(document["LeftVotes"].toString())
                val right = Integer.parseInt(document["RightVotes"].toString())
                map["Left"] = left
                map["Right"] = right
            }
            callBack.setOutput(map)
            callBack.Call()
        }
    }

    override fun getCompanyDistribution(callBack: CallBack<Company, Map<VoteOption, Int>>): Job {
        TODO("Not yet implemented")
    }

    override fun getCategoryStatistics(callBack: CallBack<Category, Map<VoteOption, Int>>): Job {
        TODO("Not yet implemented")
    }

    override fun getTotalDistribution(callBack: CallBack<Int, Map<VoteOption, Int>>): Job {
        TODO("Not yet implemented")
    }

    override fun getNumberOfUsersByTime(callBack: CallBack<Int, Int>): Job {
        TODO("Not yet implemented")
    }

    override fun getNumberOfUsersByTime(start: Long, end: Long, callBack: CallBack<Int, Int>): Job {
        TODO("Not yet implemented")
    }

    override fun getNumberOfUsersByYear(callBack: CallBack<Int, Int>): Job {
        TODO("Not yet implemented")
    }

    override fun getNumberOfUsersByYear_MonthBasedData(callBack: CallBack<Int, Map<Int, Int>>): Job {
        TODO("Not yet implemented")
    }
}