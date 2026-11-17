package com.tomazgalileu.mtgcollectionmanager.data.local

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.tomazgalileu.mtgcollectionmanager.data.local.entity.CardEntity
import com.tomazgalileu.mtgcollectionmanager.data.repository.CollectionRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CollectionPersistenceTest {

    @Test
    fun cardRemainsAfterDatabaseIsReopened() = runBlocking {

        val context =
            InstrumentationRegistry.getInstrumentation().targetContext

        val databaseName = "collection_persistence_test.db"

        context.deleteDatabase(databaseName)

        val firstDatabase = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            databaseName
        ).build()

        try {
            val repository = CollectionRepository(
                firstDatabase.cardDao()
            )

            val card = CardEntity(
                id = "test-card-001",
                name = "Black Lotus",
                layout = "normal",
                quantity = 2
            )

            assertTrue(repository.insertNewCard(card))

            assertTrue(
                repository.increaseQuantity(card.id)
            )

            assertEquals(
                3,
                repository.getCardById(card.id)?.quantity
            )

        } finally {
            firstDatabase.close()
        }

        val reopenedDatabase = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            databaseName
        ).build()

        try {
            val savedCard = reopenedDatabase
                .cardDao()
                .getCardById("test-card-001")

            assertNotNull(savedCard)
            assertEquals("Black Lotus", savedCard?.name)
            assertEquals(3, savedCard?.quantity)

        } finally {
            reopenedDatabase.close()
            context.deleteDatabase(databaseName)
        }
    }
}
