package com.drpradeepepi.mchtrackingapp.domain.model

data class FollowUp(
    val id: Int = 0,
    val motherName: String = "",
    val date: String,
    val gestationalAge: Int,
    val weight: String,
    val bloodPressure: String,
    val hemoglobin: String,
    val complaints: String,
    val examination: String,
    val advice: String,
    val nextVisit: String,
    val remarks: String = ""
)