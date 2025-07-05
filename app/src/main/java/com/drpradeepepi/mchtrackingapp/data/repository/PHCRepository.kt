package com.drpradeepepi.mchtrackingapp.data.repository

import com.drpradeepepi.mchtrackingapp.data.dao.*
import com.drpradeepepi.mchtrackingapp.data.entity.*
import kotlinx.coroutines.flow.Flow

class PHCRepository(private val phcDao: PHCDao) {
    fun getAllPHCs(): Flow<List<PHCEntity>> = phcDao.getAllPHCs()

    suspend fun insertPHC(phc: PHCEntity) {
        phcDao.insertPHC(phc)
    }

    suspend fun updatePHC(phc: PHCEntity) {
        phcDao.updatePHC(phc)
    }

    suspend fun deletePHC(phc: PHCEntity) {
        phcDao.deletePHC(phc)
    }

    suspend fun getPHCByName(name: String): PHCEntity? {
        return phcDao.getPHCByName(name)
    }
}