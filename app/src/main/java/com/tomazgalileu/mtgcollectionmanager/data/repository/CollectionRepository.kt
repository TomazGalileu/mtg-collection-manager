package com.tomazgalileu.mtgcollectionmanager.data.repository

import com.tomazgalileu.mtgcollectionmanager.data.local.dao.CardDao
import com.tomazgalileu.mtgcollectionmanager.data.local.entity.CardEntity
import kotlinx.coroutines.flow.Flow

class CollectionRepository(
    private val cardDao: CardDao
) {

    val cards: Flow<List<CardEntity>> =
        cardDao.observeAllCards()

    suspend fun getCardById(cardId: String): CardEntity? {
        return cardDao.getCardById(cardId)
    }

    suspend fun insertNewCard(card: CardEntity): Boolean {
        require(card.id.isNotBlank()) {
            "Card ID cannot be blank."
        }

        require(card.quantity > 0) {
            "Card quantity must be greater than zero."
        }

        return cardDao.insertCard(card) != -1L
    }

    suspend fun increaseQuantity(cardId: String): Boolean {
        return cardDao.increaseQuantity(cardId) > 0
    }

    suspend fun decreaseQuantity(cardId: String): Boolean {
        return cardDao.decreaseQuantity(cardId) > 0
    }

    suspend fun deleteCard(cardId: String): Boolean {
        return cardDao.deleteCardById(cardId) > 0
    }
}
