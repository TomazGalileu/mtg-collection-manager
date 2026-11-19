package com.tomazgalileu.mtgcollectionmanager.data.mapper

import com.tomazgalileu.mtgcollectionmanager.data.local.entity.CardEntity
import com.tomazgalileu.mtgcollectionmanager.data.remote.ScryfallCard

fun ScryfallCard.toCardEntity(): CardEntity {

    val frontFace = cardFaces?.getOrNull(0)
    val backFace = cardFaces?.getOrNull(1)

    return CardEntity(
        id = id,
        name = name,
        layout = layout,
        quantity = 1,

        imageUrl = imageUris?.normal
            ?: frontFace?.imageUris?.normal,

        manaCost = manaCost
            ?: frontFace?.manaCost,

        typeLine = typeLine
            ?: frontFace?.typeLine,

        oracleText = oracleText
            ?: frontFace?.oracleText,

        backName = backFace?.name,
        backImageUrl = backFace?.imageUris?.normal,
        backManaCost = backFace?.manaCost,
        backTypeLine = backFace?.typeLine,
        backOracleText = backFace?.oracleText
    )
}
