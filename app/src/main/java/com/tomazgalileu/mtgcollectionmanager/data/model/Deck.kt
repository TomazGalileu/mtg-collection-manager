package com.tomazgalileu.mtgcollectionmanager.data.model

data class Deck(
    val id: Long,
    val name: String,
    val cardIds: List<String> = emptyList()
)
