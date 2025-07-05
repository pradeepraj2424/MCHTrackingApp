package com.drpradeepepi.mchtrackingapp.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "hsc_table",
    foreignKeys = [
        ForeignKey(
            entity = PHCEntity::class,
            parentColumns = ["name"],
            childColumns = ["phcName"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class HSCEntity(
    @PrimaryKey val name: String,
    val phcName: String,
    val location: String,
    val contactNumber: String,
    val anmName: String
)