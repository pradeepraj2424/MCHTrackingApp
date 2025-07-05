package com.drpradeepepi.mchtrackingapp.data.dao

import androidx.room.*
import com.drpradeepepi.mchtrackingapp.data.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PHCDao {
    @Query("SELECT * FROM phc_table ORDER BY name ASC")
    fun getAllPHCs(): Flow<List<PHCEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPHC(phc: PHCEntity)

    @Update
    suspend fun updatePHC(phc: PHCEntity)

    @Delete
    suspend fun deletePHC(phc: PHCEntity)

    @Query("SELECT * FROM phc_table WHERE name = :name")
    suspend fun getPHCByName(name: String): PHCEntity?
}
