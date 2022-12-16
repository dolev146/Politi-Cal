package com.example.politi_cal.DBObjects

import android.content.Context
import android.widget.Toast
import com.example.politi_cal.data.queries_Interfaces.UserVoteDBInterface
import com.example.politi_cal.db
import com.example.politi_cal.models.CallBack
import com.example.politi_cal.models.User
import com.example.politi_cal.models.UserVote
import com.example.politi_cal.user
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


private val UserVoteDB = db.collection("userVotes")
data class UserVoteDBObj(
    private  val map: HashMap<String, UserVote>,
    private val context: Context
): UserVoteDBInterface{

    constructor(context: Context): this(map= HashMap(), context=context)

    override fun vote(userVote: UserVote)= CoroutineScope(Dispatchers.IO).launch {
        val new_vote = hashMapOf("UserID" to userVote.getUserID(),
        "CelebID" to userVote.getCelebID(),
        "CompanyID" to userVote.getCompanyID(),
        "CategoryID" to userVote.getCategoryID(),
        "VoteID" to userVote.getVoteID())
        var callback = CallBack<UserVote, Boolean>(userVote)
        alreadyVoted(userVote, callback)
        while(!callback.getStatus()){
            continue
        }
        if(callback.getOutput()!!) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "You cannot vote for the same celeb twice",
                    Toast.LENGTH_LONG).show()
            }
        }
        else{
            UserVoteDB.add(new_vote).await()
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Your vote successfully added to the DB",
                    Toast.LENGTH_LONG).show()
            }
            val votes = db.collection("voteOptions").get().await()
            var vote_type = ""
            for(option in votes){
                if(option.id == userVote.getVoteID()){
                    vote_type = option["VoteDesc"].toString()
                    break
                }
            }
            val celebs = db.collection("celebs").get().await()
            for(celeb in celebs){
                if(celeb.id == userVote.getCelebID()){
                    var left = Integer.parseInt(celeb["LeftVotes"].toString())
                    var right = Integer.parseInt(celeb["RightVotes"].toString())
                    if(vote_type.equals("Left")){
                        left += 1
                    }
                    else{
                        right += 1
                    }
                    val map = hashMapOf("LeftVotes" to left, "RightVotes" to right)
                    db.collection("celebs")
                        .document(celeb.id).set(map, SetOptions.merge()).await()
                }
            }
        }
    }

    /**
     * If the user and celeb record exist in the DB
     */

    override fun alreadyVoted(userVote: UserVote, callBack: CallBack<UserVote, Boolean>)
    =CoroutineScope(Dispatchers.IO).launch {
        val result = UserVoteDB.whereEqualTo("UserID", userVote.getUserID())
            .whereEqualTo("CelebID", userVote.getCelebID()).get().await()
        if(result.documents.isNotEmpty()) {
            callBack.setOutput(true)
            callBack.Call()
        }
    }

    override fun addVote(userVote: UserVote) {
        vote(userVote = userVote)
    }

    override fun updateVote(userVote: UserVote)
    =CoroutineScope(Dispatchers.IO).launch {
        val result = UserVoteDB.whereEqualTo("UserID", userVote.getUserID())
            .whereEqualTo("CelebID", userVote.getCelebID())
            .get().await()
        val celeb = db.collection("celebs")
            .document(userVote.getCelebID()).get().await()
        val votes = db.collection("voteOptions")
            .get().await()
        if(result.documents.isNotEmpty()){
            var new_vote = ""
            for(option in votes){
                if(option.id == userVote.getVoteID()){
                    new_vote = option["VoteDesc"].toString()
                }
            }
            for (document in result){
                var left = Integer.parseInt(celeb["LeftVotes"].toString())
                var right = Integer.parseInt(celeb["RightVotes"].toString())
                if(new_vote == "Left"){
                    left += 1
                    right -= 1
                }
                else{
                    left -= 1
                    right += 1
                }
                val update = hashMapOf("VoteID" to userVote.getVoteID())
                UserVoteDB.document(document.id)
                    .set(update, SetOptions.merge()).await()
                val celeb_update_votes = hashMapOf("LeftVotes" to left, "RightVotes" to right)
                db.collection("celebs")
                    .document(celeb.id).set(celeb_update_votes, SetOptions.merge()).await()
            }
            withContext(Dispatchers.Main) {
                Toast.makeText(context,
                    "Vote updated successfully",
                    Toast.LENGTH_LONG).show()
            }
        }
        else{
            withContext(Dispatchers.Main) {
                Toast.makeText(context,
                    "No such vote found",
                    Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun deleteVote(userVote: UserVote)
    =CoroutineScope(Dispatchers.IO).launch {
        val result = UserVoteDB.whereEqualTo("UserID", userVote.getUserID())
            .whereEqualTo("CelebID", userVote.getCelebID())
            .whereEqualTo("CompanyID", userVote.getCompanyID())
            .whereEqualTo("CategoryID", userVote.getCategoryID())
            .whereEqualTo("VoteID", userVote.getVoteID()).get().await()
        if(result.documents.isNotEmpty()){
            for(document in result){
                UserVoteDB.document(document.id).delete().await()
                withContext(Dispatchers.Main) {
                    Toast.makeText(context,
                        "The vote deleted from the db",
                        Toast.LENGTH_LONG).show()
                }
                val celeb = db.collection("celebs").document("CelebID")
                    .get().await()
                var left = Integer.parseInt(celeb["LeftVotes"].toString())
                var right = Integer.parseInt(celeb["RightVotes"].toString())
                val votes = db.collection("voteOptions")
                    .get().await()
                for(vote in votes){
                    if(vote.id.equals(userVote.getVoteID())){
                        if(vote["VoteDesc"].toString().equals("Left")){
                            left += 1
                        }
                        else{
                            right += 1
                        }
                        val map = hashMapOf("LeftVotes" to left, "RightVotes" to right)
                        db.collection("celebs")
                            .document(celeb.id).set(map, SetOptions.merge()).await()
                    }
                }
            }
        }
        else{
            withContext(Dispatchers.Main) {
                Toast.makeText(context,
                    "No such vote found",
                    Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun deleteAllVotesByUserID(userVote: UserVote)
    =CoroutineScope(Dispatchers.IO).launch {
        val result = UserVoteDB.whereEqualTo("UserID", userVote.getUserID()).get().await()
        var counter = 0
        val userID = userVote.getUserID()
        if(result.documents.isNotEmpty()){
            for(document in result){
                val celebID = document["CelebID"].toString()
                val companyID = document["CompanyID"].toString()
                val categoryID = document["CategoryID"].toString()
                val voteID = document["VoteID"].toString()
                val deleted_userVote = UserVote(document.id, userID,
                    celebID, categoryID, companyID, voteID)
                deleteVote(deleted_userVote)
            }
            withContext(Dispatchers.Main){
                Toast.makeText(context, "Deleted $counter votes from the DB",
                    Toast.LENGTH_LONG).show()
            }
        }
        else{
            withContext(Dispatchers.Main){
                Toast.makeText(context, "Deleted $counter votes from the DB",
                    Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun deleteAllVotesByCompanyID(userVote: UserVote)
    =CoroutineScope(Dispatchers.IO).launch {
        val result = UserVoteDB.whereEqualTo("CompanyID", userVote.getCompanyID())
            .get().await()
        val companyID = userVote.getCompanyID()
        if(result.documents.isNotEmpty()){
            for(document in result){
                val userID = document["UserID"].toString()
                val celebID = document["CelebID"].toString()
                val categoryID = document["CategoryID"].toString()
                val voteID = document["VoteID"].toString()
                val deleted_userVote = UserVote(document.id, userID, celebID, categoryID, companyID, voteID)
                deleteVote(deleted_userVote)
            }
            withContext(Dispatchers.Main){
                Toast.makeText(context, "Deleted ${result.size()} votes from the DB",
                    Toast.LENGTH_LONG).show()
            }
        }
        else{
            withContext(Dispatchers.Main){
                Toast.makeText(context, "Deleted ${result.size()} votes from the DB",
                    Toast.LENGTH_LONG).show()
            }
        }
    }


    override fun deleteAllVotesByCategoryID(userVote: UserVote)= CoroutineScope(Dispatchers.IO).launch {
        val result = UserVoteDB.whereEqualTo("CategoryID", userVote.getCategoryID())
            .get().await()
        val categoryID = userVote.getCategoryID()
        if(result.documents.isNotEmpty()){
            for(document in result){
                val userID = document["UserID"].toString()
                val celebID = document["CelebID"].toString()
                val companyID = document["CompanyID"].toString()
                val voteID = document["VoteID"].toString()
                val deleted_userVote = UserVote(document.id, userID, celebID,
                    categoryID, companyID, voteID)
                deleteVote(deleted_userVote)
            }
            withContext(Dispatchers.Main){
                Toast.makeText(context, "Deleted ${result.size()} votes from the DB",
                    Toast.LENGTH_LONG).show()
            }
        }
        else{
            withContext(Dispatchers.Main){
                Toast.makeText(context, "Deleted ${result.size()} votes from the DB",
                    Toast.LENGTH_LONG).show()
            }
        }
    }


    override fun deleteAllVotesByVoteID(userVote: UserVote)
    = CoroutineScope(Dispatchers.IO).launch {
        val result = UserVoteDB.whereEqualTo("VoteID", userVote.getVoteID()).get().await()
        val voteID = userVote.getVoteID()
        if(result.documents.isNotEmpty()){
            for(document in result){
                val userID = document["UserID"].toString()
                val celebID = document["CelebID"].toString()
                val companyID = document["CompanyID"].toString()
                val categoryID = document["CategoryID"].toString()
                val deleted_userVote = UserVote(document.id, userID, celebID,
                    categoryID, companyID, voteID)
                deleteVote(deleted_userVote)
            }
            withContext(Dispatchers.Main){
                Toast.makeText(context, "Deleted ${result.size()} votes from the DB",
                    Toast.LENGTH_LONG).show()
            }
        }
        else{
            withContext(Dispatchers.Main){
                Toast.makeText(context, "Deleted ${result.size()} votes from the DB",
                    Toast.LENGTH_LONG).show()
            }
        }
    }
}
