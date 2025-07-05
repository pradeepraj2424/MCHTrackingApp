package com.drpradeepepi.mchtrackingapp.data.dao

import androidx.room.*
import com.drpradeepepi.mchtrackingapp.data.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FollowUpDao {
    @Query("SELECT * FROM follow_up_table WHERE motherName = :motherName ORDER BY date ASC")
    fun getFollowUpsByMother(motherName: String): Flow<List<FollowUpEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFollowUp(followUp: FollowUpEntity)

    @Update
    suspend fun updateFollowUp(followUp: FollowUpEntity)

    @Delete
    suspend fun deleteFollowUp(followUp: FollowUpEntity)

    @Query("SELECT * FROM follow_up_table WHERE id = :id")
    suspend fun getFollowUpById(id: Int): FollowUpEntity?
}