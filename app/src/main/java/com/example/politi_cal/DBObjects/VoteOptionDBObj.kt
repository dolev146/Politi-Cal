package com.example.politi_cal.DBObjects

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.annotation.MainThread
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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.cancel
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.newCoroutineContext
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

private val voteDB = db.collection("voteOptions")

data class VoteOptionDBObj(
    private val map: HashMap<String, VoteOption>,
    private val context: Context
) : VoteOptionDBInterface {

    constructor(input_context: Context) : this(
        map = HashMap<String, VoteOption>(),
        context = input_context
    )

    fun init() = CoroutineScope(Dispatchers.IO).launch {
        val results = voteDB.get().await()
        for (document in results) {
            val desc = document.getField<String>("VoteDesc").toString()
            val id = document.id
            val vote = VoteOption(voteID = id, voteDescription = desc)
            map[desc] = vote
        }
    }

    override fun isVoteExist(option: VoteOption) = CoroutineScope(Dispatchers.IO).launch {
            val result = voteDB.whereEqualTo("VoteDesc", option.getVoteDescription())
                .get().await()
            if (result.documents.isNotEmpty()) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context, option.getVoteDescription() + " does exist!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "No vote was found", Toast.LENGTH_LONG).show()
                }
            }
        }

        override fun isVoteExist(option: String)= CoroutineScope(Dispatchers.IO).launch {
            val result = voteDB.whereEqualTo("VoteDesc", option)
                .get().await()
            if (result.documents.isNotEmpty()) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context, "$option does exist!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "No vote was found", Toast.LENGTH_LONG).show()
                }
            }
        }

        override fun addVoteOption(option: VoteOption) = CoroutineScope(Dispatchers.IO).launch {
            val vote = hashMapOf("VoteDesc" to option.getVoteDescription())

            voteDB.add(vote).await()
        }

        override fun addVoteOption(option: String): Boolean {
            val vote = hashMapOf("VoteDesc" to option)
            var success = true
            val result = voteDB.add(vote).addOnSuccessListener { doc ->
                Log.d(MainActivity.TAG, "DocumentSnapshot added with ID: ${doc.id}")
            }.addOnFailureListener { e ->
                Log.w(MainActivity.TAG, "Error adding document", e)
                success = false
            }
            return success
        }

        override fun updateVoteOption(original_option: VoteOption, new_option: VoteOption) =
            CoroutineScope(Dispatchers.IO).launch {
                val result = voteDB
                    .whereEqualTo("VoteDesc", original_option.getVoteDescription()).get().await()
                if(result.documents.isNotEmpty()) {
                    for (document in result) {
                        voteDB.document(document.id).set(
                            hashMapOf("VoteDesc" to new_option.getVoteDescription()),
                            SetOptions.merge()
                        ).await()
                        break
                    }
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Finished updating", Toast.LENGTH_LONG).show()
                    }
                }
                else{
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context,
                            "No vote described as ${original_option.getVoteDescription()} exists",
                            Toast.LENGTH_LONG).show()
                    }
                }
            }

        override fun updateVoteOption(original_option: String, new_option: String) =
            CoroutineScope(Dispatchers.IO).launch {
                updateVoteOption(VoteOption(original_option), VoteOption(new_option))
            }

        override fun deleteVoteOption(vote: VoteOption) {
            GlobalScope.launch(Dispatchers.IO) {
                val result = voteDB
                    .whereEqualTo("VoteDesc", vote.getVoteDescription()).get().await()
                if (result.documents.isNotEmpty()) {
                    for (document in result) {
                        voteDB.document(document.id).delete().await()
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context,
                                "${vote.getVoteDescription()} deleted from the db",
                                Toast.LENGTH_LONG).show()
                        }
                        break
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context,
                            "No such vote described as ${vote.getVoteDescription()} found",
                            Toast.LENGTH_LONG).show()
                    }
                }
            }
        }


        override fun deleteVoteOption(vote: String): Job = CoroutineScope(Dispatchers.IO).launch {
            deleteVoteOption(VoteOption(vote))
        }
    }