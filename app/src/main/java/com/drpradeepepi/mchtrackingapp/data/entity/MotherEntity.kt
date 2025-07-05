package com.drpradeepepi.mchtrackingapp.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "mother_table",
    foreignKeys = [
        ForeignKey(
            entity = HSCEntity::class,
            parentColumns = ["name"],
            childColumns = ["hscName"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MotherEntity(
    @PrimaryKey val name: String,
    val hscName: String,
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