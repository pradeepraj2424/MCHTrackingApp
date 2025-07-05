package com.drpradeepepi.mchtrackingapp.data.dao

import androidx.room.*
import com.drpradeepepi.mchtrackingapp.data.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface HSCDao {
    @Query("SELECT * FROM hsc_table WHERE phcName = :phcName ORDER BY name ASC")
    fun getHSCsByPHC(phcName: String): Flow<List<HSCEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHSC(hsc: HSCEntity)

    @Update
    suspend fun updateHSC(hsc: HSCEntity)

    @Delete
    suspend fun deleteHSC(hsc: HSCEntity)

    @Query("SELECT * FROM hsc_table WHERE name = :name")
    suspend fun getHSCByName(name: String): HSCEntity?
}