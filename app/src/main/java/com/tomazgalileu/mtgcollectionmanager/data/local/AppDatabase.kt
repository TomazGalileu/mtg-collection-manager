package com.tomazgalileu.mtgcollectionmanager.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tomazgalileu.mtgcollectionmanager.data.local.dao.CardDao
import com.tomazgalileu.mtgcollectionmanager.data.local.entity.CardEntity

@Database(
    entities = [CardEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cardDao(): CardDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mtg_collection_manager.db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}
