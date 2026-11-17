package com.tomazgalileu.mtgcollectionmanager.data.remote

import com.google.gson.annotations.SerializedName

data class ScryfallCard(
    val id: String,
    val name: String,
    val lang: String,
    val layout: String,

    @SerializedName("printed_name")
    val printedName: String? = null,

    @SerializedName("mana_cost")
    val manaCost: String? = null,

    @SerializedName("type_line")
    val typeLine: String? = null,

    @SerializedName("oracle_text")
    val oracleText: String? = null,

    @SerializedName("image_uris")
    val imageUris: ScryfallImageUris? = null,

    @SerializedName("card_faces")
    val cardFaces: List<ScryfallCardFace>? = null
)

data class ScryfallImageUris(
    val small: String? = null,
    val normal: String? = null,
    val large: String? = null,
    val png: String? = null
)

data class ScryfallCardFace(
    val name: String,

    @SerializedName("mana_cost")
    val manaCost: String? = null,

    @SerializedName("type_line")
    val typeLine: String? = null,

    @SerializedName("oracle_text")
    val oracleText: String? = null,

    @SerializedName("image_uris")
    val imageUris: ScryfallImageUris? = null
)

data class ScryfallSearchResponse(
    val data: List<ScryfallCard>,

    @SerializedName("has_more")
    val hasMore: Boolean,

    @SerializedName("next_page")
    val nextPage: String? = null
)
