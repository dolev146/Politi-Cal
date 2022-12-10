package com.example.politi_cal.DBObjects

import android.util.Log
import android.widget.Toast
import com.example.politi_cal.MainActivity
import com.example.politi_cal.MainActivity.Companion.TAG
import com.example.politi_cal.data.static_collection_interfaces.VoteOptionDBInterface
import com.example.politi_cal.db
import com.example.politi_cal.models.Party
import com.example.politi_cal.models.VoteOption
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.getField
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class VoteOptionDBObj(
    private val map: HashMap<String, VoteOption>
): VoteOptionDBInterface {

    constructor():this(map=HashMap<String, VoteOption>())

    fun init()= CoroutineScope(Dispatchers.IO).launch{
        val results = db.collection("voteOptions").get().await()
        for(document in results){
            val desc = document.getField<String>("VoteDesc").toString()
            val id = document.id
            val vote = VoteOption(voteID = id, voteDescription = desc)
            map[desc]=vote
        }
    }

    override fun isVoteExist(option: VoteOption): Boolean {
        return this.map.containsKey(option.getVoteDescription())
    }

    override fun isVoteExist(option: String): Boolean{
        return this.map.containsKey(option)
    }

    override fun addVoteOption(option: VoteOption)= CoroutineScope(Dispatchers.IO).launch {
        val vote = hashMapOf("VoteDesc" to option.getVoteDescription())
        db.collection("voteOptions").add(vote).await()
    }

    override fun addVoteOption(option: String): Boolean {
        val vote = hashMapOf("VoteDesc" to option)
        var success = true
        val result = db.collection("voteOptions").add(vote).addOnSuccessListener { doc ->
            Log.d(MainActivity.TAG, "DocumentSnapshot added with ID: ${doc.id}")
        }.addOnFailureListener { e ->
            Log.w(MainActivity.TAG, "Error adding document", e)
            success = false
        }
        return success
    }

    override fun updateVoteOption(original_option: VoteOption, new_option: VoteOption)
    = CoroutineScope(Dispatchers.IO).launch    {
        val result = db.collection("voteOptions")
            .whereEqualTo("VoteDesc", original_option.getVoteDescription()).get().await()
        for(document in result){
            db.collection("voteOptions").document(document.id).set(
                hashMapOf("VoteDesc" to new_option.getVoteDescription()),
                SetOptions.merge()
            ).await()
        }
    }

    override fun updateVoteOption(original_option: String, new_option: String)
            = CoroutineScope(Dispatchers.IO).launch{
        updateVoteOption(VoteOption(original_option), VoteOption(new_option))
    }

    override fun deleteVoteOption(vote: VoteOption)=CoroutineScope(Dispatchers.IO).launch{
        val result = db.collection("voteOptions")
            .whereEqualTo("VoteDesc", vote.getVoteDescription()).get().await()
        for (document in result){
            db.collection("voteOptions").document(document.id).delete().await()
        }
    }

    override fun deleteVoteOption(vote: String): Job=CoroutineScope(Dispatchers.IO).launch{
        deleteVoteOption(VoteOption(vote))
    }
}

fun main() {
    val vopj = VoteOptionDBObj()
    vopj.addVoteOption("Extreme Left")
}