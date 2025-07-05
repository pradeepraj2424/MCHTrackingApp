package com.drpradeepepi.mchtrackingapp.domain.model

data class Mother(
    val name: String,
    val age: Int,
    val phone: String,
    val address: String,
    val husbandName: String,
    val lmp: String,
    val edd: String,
    val gravida: Int,
    val para: Int,
    val riskFactor: Boolean = false,
    val anc1: String = "",
    val anc2: String = "",
    val anc3: String = ""
)