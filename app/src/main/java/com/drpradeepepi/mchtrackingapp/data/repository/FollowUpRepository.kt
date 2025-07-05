package com.drpradeepepi.mchtrackingapp.data.repository

import com.drpradeepepi.mchtrackingapp.data.dao.*
import com.drpradeepepi.mchtrackingapp.data.entity.*
import kotlinx.coroutines.flow.Flow

class FollowUpRepository(private val followUpDao: FollowUpDao) {
    fun getFollowUpsByMother(motherName: String): Flow<List<FollowUpEntity>> =
        followUpDao.getFollowUpsByMother(motherName)

    suspend fun insertFollowUp(followUp: FollowUpEntity) {
        followUpDao.insertFollowUp(followUp)
    }

    suspend fun updateFollowUp(followUp: FollowUpEntity) {
        followUpDao.updateFollowUp(followUp)
    }

    suspend fun deleteFollowUp(followUp: FollowUpEntity) {
        followUpDao.deleteFollowUp(followUp)
    }

    suspend fun getFollowUpById(id: Int): FollowUpEntity? {
        return followUpDao.getFollowUpById(id)
    }
}