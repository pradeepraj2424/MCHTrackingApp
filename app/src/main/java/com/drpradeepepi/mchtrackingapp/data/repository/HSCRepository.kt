package com.drpradeepepi.mchtrackingapp.data.repository

import com.drpradeepepi.mchtrackingapp.data.dao.*
import com.drpradeepepi.mchtrackingapp.data.entity.*
import kotlinx.coroutines.flow.Flow

class HSCRepository(private val hscDao: HSCDao) {
    fun getHSCsByPHC(phcName: String): Flow<List<HSCEntity>> = hscDao.getHSCsByPHC(phcName)

    suspend fun insertHSC(hsc: HSCEntity) {
        hscDao.insertHSC(hsc)
    }

    suspend fun updateHSC(hsc: HSCEntity) {
        hscDao.updateHSC(hsc)
    }

    suspend fun deleteHSC(hsc: HSCEntity) {
        hscDao.deleteHSC(hsc)
    }

    suspend fun getHSCByName(name: String): HSCEntity? {
        return hscDao.getHSCByName(name)
    }
}