package com.tomazgalileu.mtgcollectionmanager.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards")
data class CardEntity(
    @PrimaryKey
    val id: String,

    val name: String,
    val layout: String,
    val quantity: Int,

    val imageUrl: String? = null,
    val manaCost: String? = null,
    val typeLine: String? = null,
    val oracleText: String? = null,

    val backName: String? = null,
    val backImageUrl: String? = null,
    val backManaCost: String? = null,
    val backTypeLine: String? = null,
    val backOracleText: String? = null
)
