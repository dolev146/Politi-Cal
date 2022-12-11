package com.example.politi_cal.models

import com.example.politi_cal.data.object_interfaces.UserVoteInterface
import com.google.firebase.firestore.Exclude

data class UserVote(
    @Exclude val RecordID: String,
    private val UserID: String,
    private val CelebID: String,
    private val CategoryID: String,
    private val CompanyID: String,
    private val VoteID: String
): UserVoteInterface{
    override fun getRecoredID(): String {
        return this.RecordID
    }

    override fun getUserID(): String {
        return this.UserID
    }

    override fun getCelebID(): String {
        return this.CelebID
    }

    override fun getCompanyID(): String {
        return this.CompanyID
    }

    override fun getCategoryID(): String {
        return this.CategoryID
    }

    override fun getVoteID(): String {
        return this.VoteID
    }

}
