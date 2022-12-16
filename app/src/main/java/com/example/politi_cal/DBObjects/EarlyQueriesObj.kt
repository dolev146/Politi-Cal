package com.example.politi_cal.DBObjects

import com.example.politi_cal.MainActivity
import com.example.politi_cal.data.queries_Interfaces.EarlyQueriesInterface
import com.example.politi_cal.db
import com.example.politi_cal.models.CallBack
import com.example.politi_cal.models.Category
import com.example.politi_cal.models.Celeb
import com.example.politi_cal.models.Company
import com.example.politi_cal.models.Party
import com.example.politi_cal.models.User
import com.example.politi_cal.models.UserVote
import com.example.politi_cal.models.VoteOption
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class EarlyQueriesObj(val name: String = ""): EarlyQueriesInterface {
    override fun getAllParties(callback: CallBack<Int, Map<String, Party>>)
    =CoroutineScope(Dispatchers.IO).launch{
        val result =db.collection("parties").get().await()
        if(result.documents.isNotEmpty()){
            var map = HashMap<String, Party>()
            for(document in result){
                val partyname = document["PartyName"].toString()
                val partyID = document.id
                var party = Party(PartyID =partyID, PartyName =partyname )
                map.put(partyname, party)
            }
            callback.setOutput(map)
            callback.Call()
        }
        else{
            callback.setOutput(null)
            callback.Call()
        }
    }

    override fun getAllCompanies(): Map<String, Company> {
        TODO("Not yet implemented")
    }

    override fun getAllCategories(): Map<String, Category> {
        TODO("Not yet implemented")
    }

    override fun getAllVoteOptions(callback: CallBack<Int, Map<String, VoteOption>>)
    = CoroutineScope(Dispatchers.IO).launch  {
        val result =db.collection("voteOptions").get().await()
        if(result.documents.isNotEmpty()){
            var map = HashMap<String, VoteOption>()
            for(document in result){
                val option = document["VoteDesc"].toString()
                val voteID = document.id
                var vote = VoteOption("")
                vote.init(voteID, option)
                map[option] = vote
            }
            callback.setOutput(map)
            callback.Call()
        }
        else{
            callback.setOutput(null)
            callback.Call()
        }
    }

    override fun getCelebsByPreferences(userData: User): Map<String, Celeb> {
        TODO("Not yet implemented")
    }

    override fun getAllCelebs(): Map<String, Celeb> {
        TODO("Not yet implemented")
    }

    override fun getUserVotes(callback: CallBack<Int, Map<String, UserVote>>)
    = CoroutineScope(Dispatchers.IO).launch  {
        val result =db.collection("userVotes").get().await()
        if(result.documents.isNotEmpty()){
            var map = HashMap<String, UserVote>()
            for(document in result){
                val voteID = document["VoteID"].toString()
                val userID = document["UserID"].toString()
                val celebID = document["CelebID"].toString()
                val companyID = document["CompanyID"].toString()
                val categoryID = document["CompanyID"].toString()
                val recordID = document.id
                var uservote = UserVote(recordID, userID, celebID, companyID, categoryID, voteID)
                map.put(recordID, uservote)
            }
            callback.setOutput(map)
            callback.Call()
        }
        else{
            callback.setOutput(null)
            callback.Call()
        }
    }
}
