package com.example.politi_cal.models

import com.example.politi_cal.data.object_interfaces.VoteOptionInterface
import com.google.firebase.firestore.Exclude

data class VoteOption(
    @Exclude private var voteID: String,
    private var voteDescription: String
): VoteOptionInterface{
    constructor(votedesc: String) : this(voteID="", voteDescription = votedesc)

    fun init(voteID: String, voteName: String){
        this.voteID=voteID
        this.voteDescription=voteDescription
    }

    override fun getVoteID(): String {
        return this.voteID
    }

    override fun getVoteDescription(): String {
        return this.voteDescription
    }

}
