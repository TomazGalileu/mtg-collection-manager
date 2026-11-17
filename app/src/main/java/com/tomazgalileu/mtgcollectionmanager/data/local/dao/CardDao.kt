package com.tomazgalileu.mtgcollectionmanager.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tomazgalileu.mtgcollectionmanager.data.local.entity.CardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {

    @Query("SELECT * FROM cards ORDER BY name COLLATE NOCASE ASC")
    fun observeAllCards(): Flow<List<CardEntity>>

    @Query("SELECT * FROM cards WHERE id = :cardId LIMIT 1")
    suspend fun getCardById(cardId: String): CardEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCard(card: CardEntity): Long

    @Query(
        "UPDATE cards SET quantity = quantity + 1 " +
                "WHERE id = :cardId AND quantity < 2147483647"
    )
    suspend fun increaseQuantity(cardId: String): Int

    @Query(
        "UPDATE cards SET quantity = quantity - 1 " +
                "WHERE id = :cardId AND quantity > 1"
    )
    suspend fun decreaseQuantity(cardId: String): Int

    @Query("DELETE FROM cards WHERE id = :cardId")
    suspend fun deleteCardById(cardId: String): Int
}
