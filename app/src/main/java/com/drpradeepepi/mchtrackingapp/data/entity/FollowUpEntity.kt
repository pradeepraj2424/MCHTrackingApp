package com.drpradeepepi.mchtrackingapp.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "follow_up_table",
    foreignKeys = [
        ForeignKey(
            entity = MotherEntity::class,
            parentColumns = ["name"],
            childColumns = ["motherName"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class FollowUpEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val motherName: String,
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