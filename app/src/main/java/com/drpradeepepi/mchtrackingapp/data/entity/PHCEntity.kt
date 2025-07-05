package com.drpradeepepi.mchtrackingapp.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "phc_table")
data class PHCEntity(
    @PrimaryKey val name: String,
    val location: String,
    val contactNumber: String,
    val doctorName: String
)