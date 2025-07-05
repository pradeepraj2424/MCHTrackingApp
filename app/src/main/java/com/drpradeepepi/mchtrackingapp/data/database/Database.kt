package com.drpradeepepi.mchtrackingapp.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.drpradeepepi.mchtrackingapp.data.dao.*
import com.drpradeepepi.mchtrackingapp.data.entity.*

@Database(
    entities = [PHCEntity::class, HSCEntity::class, MotherEntity::class, FollowUpEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MCHDatabase : RoomDatabase() {
    abstract fun phcDao(): PHCDao
    abstract fun hscDao(): HSCDao
    abstract fun motherDao(): MotherDao
    abstract fun followUpDao(): FollowUpDao

    companion object {
        @Volatile
        private var INSTANCE: MCHDatabase? = null

        fun getDatabase(context: Context): MCHDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MCHDatabase::class.java,
                    "mch_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
