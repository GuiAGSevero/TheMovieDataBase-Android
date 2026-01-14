package com.severo.marvel.framework.network.response

import com.google.gson.annotations.SerializedName
import com.severo.core.domain.model.Character

data class CharacterResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("thumbnail")
    val thumbnail: ThumbnailResponse
)

fun CharacterResponse.toCharacterModel(): Character {
    return Character(
        id = this.id,
        name = this.name,
        imageUrl = this.thumbnail.getHttpsUrl()
    )
}
