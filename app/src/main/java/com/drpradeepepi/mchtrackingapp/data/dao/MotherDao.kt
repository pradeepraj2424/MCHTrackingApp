package com.drpradeepepi.mchtrackingapp.data.dao

import androidx.room.*
import com.drpradeepepi.mchtrackingapp.data.entity.*
import kotlinx.coroutines.flow.Flow


@Dao
interface MotherDao {
    @Query("SELECT * FROM mother_table WHERE hscName = :hscName ORDER BY name ASC")
    fun getMothersByHSC(hscName: String): Flow<List<MotherEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMother(mother: MotherEntity)

    @Update
    suspend fun updateMother(mother: MotherEntity)

    @Delete
    suspend fun deleteMother(mother: MotherEntity)

    @Query("SELECT * FROM mother_table WHERE name = :name")
    suspend fun getMotherByName(name: String): MotherEntity?

    @Query("SELECT * FROM mother_table")
    fun getAllMothers(): Flow<List<MotherEntity>>
}