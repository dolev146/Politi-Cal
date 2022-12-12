package com.example.politi_cal.models

import com.example.politi_cal.data.object_interfaces.UserVoteInterface
import com.google.firebase.firestore.Exclude

data class UserVote(
    var RecordID: String,
    private var UserID: String,
    private var CelebID: String,
    private var CategoryID: String,
    private var CompanyID: String,
    private var VoteID: String
): UserVoteInterface{

    fun init(record: String, user: String, celeb: String, category: String, company: String, vote: String){
        this.RecordID=record
        this.UserID=user
        this.CelebID=celeb
        this.CategoryID=category
        this.CompanyID=company
        this.VoteID=vote
    }

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
