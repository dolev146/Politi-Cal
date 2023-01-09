package com.example.politi_cal.models

class Notification(
    private val once: Boolean,
    private var times: Int,
    private val titleText: String,
    private val notificationText: String,
    private val channelId: String,
    private val notificationId: Int
) {
    fun getOnce(): Boolean {
        return once
    }

    fun getTimes(): Int{
        return times
    }

    fun getTitle(): String{
        return titleText
    }

    fun getText(): String{
        return notificationText
    }

    fun getChannelID(): String{
        return channelId
    }

    fun getID(): Int{
        return notificationId
    }

    fun setTimes(times: Int){
        this.times=times
    }
}