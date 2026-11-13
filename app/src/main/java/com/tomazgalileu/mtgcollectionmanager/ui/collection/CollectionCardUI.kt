package com.tomazgalileu.mtgcollectionmanager.ui.collection

data class CollectionCardUi(
    val id: String,
    val name: String,
    val quantity: Int,
    val imageUrl: String? = null
)
