package com.drpradeepepi.mchtrackingapp

import android.app.Application
import com.drpradeepepi.mchtrackingapp.data.database.MCHDatabase
import com.drpradeepepi.mchtrackingapp.data.repository.*

class MCHApplication : Application() {
    val database by lazy { MCHDatabase.getDatabase(this) }
    val phcRepository by lazy { PHCRepository(database.phcDao()) }
    val hscRepository by lazy { HSCRepository(database.hscDao()) }
    val motherRepository by lazy { MotherRepository(database.motherDao()) }
    val followUpRepository by lazy { FollowUpRepository(database.followUpDao()) }
}