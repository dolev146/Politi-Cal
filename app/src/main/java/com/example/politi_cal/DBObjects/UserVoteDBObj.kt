package com.example.politi_cal.DBObjects


import android.content.Context
import android.widget.Toast
import com.example.politi_cal.data.queries_Interfaces.UserVoteDBInterface
import com.example.politi_cal.db
import com.example.politi_cal.models.CallBack
import com.example.politi_cal.models.UserVote
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


private val UserVoteDB = db.collection("userVotes")

data class UserVoteDBObj(
    private var context: Context
) : UserVoteDBInterface {

    override fun vote(userVote: UserVote) = CoroutineScope(Dispatchers.IO).launch {
        val new_vote = hashMapOf(
            "userEmail" to userVote.UserEmail,
            "celebFullName" to userVote.CelebFullName,
            "companyName" to userVote.CompanyName,
            "categoryName" to userVote.CategoryName,
            "voteDirection" to userVote.VoteDirection
        )
        var callback = CallBack<UserVote, Boolean>(userVote)
        alreadyVoted(userVote, callback)
        while (!callback.getStatus()) {
            continue
        }
        if (callback.getOutput()!!) {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context, "You cannot vote for the same celeb twice", Toast.LENGTH_LONG
                ).show()
            }
        } else {
            UserVoteDB.add(new_vote).await()
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context, "Your vote successfully added to the DB", Toast.LENGTH_LONG
                ).show()
            }
            val votes = db.collection("voteOptions").get().await()
            var vote_type = ""
            for (option in votes) {
                if (option.id == userVote.VoteDirection) {
                    vote_type = option["voteDesc"].toString()
                    break
                }
            }
            val celebs = db.collection("celebs").get().await()
            for (celeb in celebs) {
                if (celeb.id == userVote.CelebFullName) {
                    var left = Integer.parseInt(celeb["leftVotes"].toString())
                    var right = Integer.parseInt(celeb["rightVotes"].toString())
                    if (vote_type.equals("left")) {
                        left += 1
                    } else {
                        right += 1
                    }
                    val map = hashMapOf("leftVotes" to left, "rightVotes" to right)
                    db.collection("celebs").document(celeb.id).set(map, SetOptions.merge()).await()
                }
            }
        }
    }

    /**
     * If the user and celeb record exist in the DB
     */
    override fun alreadyVoted(userVote: UserVote, callBack: CallBack<UserVote, Boolean>) =
        CoroutineScope(Dispatchers.IO).launch {
            val result = UserVoteDB.whereEqualTo("userEmail", userVote.UserEmail)
                .whereEqualTo("celebFullName", userVote.CelebFullName).get().await()
            if (result.documents.isNotEmpty()) {
                callBack.setOutput(true)
                callBack.Call()
            }
        }

    override fun addVote(userVote: UserVote) {
        vote(userVote = userVote)
    }

    override fun updateVote(userVote: UserVote) = CoroutineScope(Dispatchers.IO).launch {
        val result = UserVoteDB.whereEqualTo("userEmail", userVote.UserEmail)
            .whereEqualTo("celebFullName", userVote.CelebFullName).get().await()
        val celeb = db.collection("celebs").document(userVote.CelebFullName).get().await()
        val votes = db.collection("voteOptions").get().await()
        if (result.documents.isNotEmpty()) {
            var new_vote = ""
            for (option in votes) {
                if (option.id == userVote.VoteDirection) {
                    new_vote = option["voteDesc"].toString()
                }
            }
            for (document in result) {
                var left = Integer.parseInt(celeb["leftVotes"].toString())
                var right = Integer.parseInt(celeb["rightVotes"].toString())
                if (new_vote == "left") {
                    left += 1
                    right -= 1
                } else {
                    left -= 1
                    right += 1
                }
                val update = hashMapOf("userEmail" to userVote.VoteDirection)
                UserVoteDB.document(document.id).set(update, SetOptions.merge()).await()
                val celeb_update_votes = hashMapOf("leftVotes" to left, "rightVotes" to right)
                db.collection("celebs").document(celeb.id)
                    .set(celeb_update_votes, SetOptions.merge()).await()
            }
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context, "Vote updated successfully", Toast.LENGTH_LONG
                ).show()
            }
        } else {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context, "No such vote found", Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun deleteVote(userVote: UserVote) = CoroutineScope(Dispatchers.IO).launch {
        val result = UserVoteDB.whereEqualTo("userEmail", userVote.UserEmail)
            .whereEqualTo("celebFullName", userVote.CelebFullName)
            .whereEqualTo("companyName", userVote.CompanyName)
            .whereEqualTo("categoryName", userVote.CategoryName)
            .whereEqualTo("voteDirection", userVote.VoteDirection).get().await()
        if (result.documents.isNotEmpty()) {
            for (document in result) {
                val celeb = db.collection("celebs")
                    .document(userVote.CelebFullName).get().await()
                if(!celeb.exists()){
                    println("problem")
                }
                var left = Integer.parseInt(celeb["leftVotes"].toString())
                var right = Integer.parseInt(celeb["rightVotes"].toString())
                if ("left" == userVote.VoteDirection) {
                    left -= 1
                } else {
                    right -= 1
                }
                val map = hashMapOf("leftVotes" to left, "rightVotes" to right)
                db.collection("celebs").document(celeb.id).set(map, SetOptions.merge())
                    .await()
                UserVoteDB.document(document.id).delete().await()
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context, "The vote deleted from the db", Toast.LENGTH_LONG
                    ).show()
                }
            }

        } else {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context, "No such vote found", Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun deleteAllVotesByUserID(userEmail: String) =
        CoroutineScope(Dispatchers.IO).launch {
            val result = UserVoteDB.whereEqualTo("userEmail", userEmail).get().await()
            var counter = 0
            val userID = userEmail
            // convert userID from String? to String
            if (userID != null) {
                if (result.documents.isNotEmpty()) {
                    for (document in result) {
                        val celebID = document["celebFullName"].toString()
                        val companyID = document["companyName"].toString()
                        val categoryID = document["categoryName"].toString()
                        val voteID = document["voteDirection"].toString()
                        val deleted_userVote = UserVote(
                            userEmail, celebID, categoryID, companyID, voteID
                        )
                        deleteVote(deleted_userVote)
                    }
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            context, "Deleted $counter votes from the DB", Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            context, "Deleted $counter votes from the DB", Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

    override fun deleteAllVotesByCompanyID(userVote: UserVote) =
        CoroutineScope(Dispatchers.IO).launch {
            val result = UserVoteDB.whereEqualTo("companyName", userVote.CompanyName).get().await()
            val companyID = userVote.CompanyName
            if (result.documents.isNotEmpty()) {
                for (document in result) {
                    val userID = document["userEmail"].toString()
                    val celebID = document["celebFullName"].toString()
                    val categoryID = document["categoryName"].toString()
                    val voteID = document["voteDirection"].toString()
                    val deleted_userVote = UserVote(userID, celebID, categoryID, companyID, voteID)
                    deleteVote(deleted_userVote)
                }
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context, "Deleted ${result.size()} votes from the DB", Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context, "Deleted ${result.size()} votes from the DB", Toast.LENGTH_LONG
                    ).show()
                }
            }
        }


    override fun deleteAllVotesByCategoryID(userVote: UserVote) =
        CoroutineScope(Dispatchers.IO).launch {
            val result =
                UserVoteDB.whereEqualTo("categoryName", userVote.CategoryName).get().await()
            val categoryID = userVote.CategoryName
            if (result.documents.isNotEmpty()) {
                for (document in result) {
                    val userID = document["userEmail"].toString()
                    val celebID = document["celebFullName"].toString()
                    val companyID = document["companyName"].toString()
                    val voteID = document["voteDirection"].toString()
                    val deleted_userVote = UserVote(
                        userID, celebID, categoryID, companyID, voteID
                    )
                    deleteVote(deleted_userVote)
                }
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context, "Deleted ${result.size()} votes from the DB", Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context, "Deleted ${result.size()} votes from the DB", Toast.LENGTH_LONG
                    ).show()
                }
            }
        }


    override fun deleteAllVotesByVoteID(userVote: UserVote) =
        CoroutineScope(Dispatchers.IO).launch {
            val result =
                UserVoteDB.whereEqualTo("voteDirection", userVote.VoteDirection).get().await()
            val voteID = userVote.VoteDirection
            if (result.documents.isNotEmpty()) {
                for (document in result) {
                    val userID = document["userEmail"].toString()
                    val celebID = document["celebFullName"].toString()
                    val companyID = document["companyName"].toString()
                    val categoryID = document["CategoryID"].toString()
                    val deleted_userVote = UserVote(
                        userID, celebID, categoryID, companyID, voteID
                    )
                    deleteVote(deleted_userVote)
                }
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context, "Deleted ${result.size()} votes from the DB", Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context, "Deleted ${result.size()} votes from the DB", Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
}