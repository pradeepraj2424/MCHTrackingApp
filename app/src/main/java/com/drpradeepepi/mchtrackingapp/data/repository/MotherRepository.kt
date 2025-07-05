package com.drpradeepepi.mchtrackingapp.data.repository

import com.drpradeepepi.mchtrackingapp.data.dao.*
import com.drpradeepepi.mchtrackingapp.data.entity.*
import kotlinx.coroutines.flow.Flow

class MotherRepository(private val motherDao: MotherDao) {
    fun getMothersByHSC(hscName: String): Flow<List<MotherEntity>> = motherDao.getMothersByHSC(hscName)

    suspend fun insertMother(mother: MotherEntity) {
        motherDao.insertMother(mother)
    }

    suspend fun updateMother(mother: MotherEntity) {
        motherDao.updateMother(mother)
    }

    suspend fun deleteMother(mother: MotherEntity) {
        motherDao.deleteMother(mother)
    }

    suspend fun getMotherByName(name: String): MotherEntity? {
        return motherDao.getMotherByName(name)
    }
}