package com.example.politi_cal.models

import com.example.politi_cal.data.object_interfaces.VoteOptionInterface
import com.google.firebase.firestore.Exclude

data class VoteOption(
    @Exclude private val voteID: String,
    private val voteDescription: String
): VoteOptionInterface{
    constructor(votedesc: String) : this(voteID="", voteDescription = votedesc)

    override fun getVoteID(): String {
        return this.voteID
    }

    override fun getVoteDescription(): String {
        return this.voteDescription
    }

}
