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
        }
    }

    /**
     * If the user and celeb record exist in the DB
     */

    override fun alreadyVoted(userVote: UserVote, callBack: CallBack<UserVote, Boolean>)
    = CoroutineScope(Dispatchers.IO).launch {
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

    override fun updateVote(userVote: UserVote) = CoroutineScope(Dispatchers.IO).launch {
        val result = UserVoteDB.whereEqualTo("UserID", userVote.getUserID())
            .whereEqualTo("CelebID", userVote.getCelebID())
            .get().await()
        if(result.documents.isNotEmpty()){
            for (document in result){
                val update = hashMapOf("VoteID" to userVote.getVoteID())
                UserVoteDB.document(document.id).set(update, SetOptions.merge()).await()
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

    override fun deleteVote(userVote: UserVote)= CoroutineScope(Dispatchers.IO).launch {
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

    override fun deleteAllVotesByUserID(userVote: UserVote)= CoroutineScope(Dispatchers.IO).launch {
        val result = UserVoteDB.whereEqualTo("UserID", userVote.getUserID()).get().await()
        var counter = 0
        if(result.documents.isNotEmpty()){
            for(document in result){
                UserVoteDB.document(document.id).delete().await()
                counter += 1
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

    override fun deleteAllVotesByCompanyID(userVote: UserVote)= CoroutineScope(Dispatchers.IO).launch {
        val result = UserVoteDB.whereEqualTo("CompanyID", userVote.getCompanyID()).get().await()
        var counter = 0
        if(result.documents.isNotEmpty()){
            for(document in result){
                UserVoteDB.document(document.id).delete().await()
                counter += 1
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


    override fun deleteAllVotesByCategoryID(userVote: UserVote)= CoroutineScope(Dispatchers.IO).launch {
        val result = UserVoteDB.whereEqualTo("CategoryID", userVote.getCategoryID()).get().await()
        var counter = 0
        if(result.documents.isNotEmpty()){
            for(document in result){
                UserVoteDB.document(document.id).delete().await()
                counter += 1
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


    override fun deleteAllVotesByVoteID(userVote: UserVote)= CoroutineScope(Dispatchers.IO).launch {
        val result = UserVoteDB.whereEqualTo("VoteID", userVote.getVoteID()).get().await()
        var counter = 0
        if(result.documents.isNotEmpty()){
            for(document in result){
                UserVoteDB.document(document.id).delete().await()
                counter += 1
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


}
