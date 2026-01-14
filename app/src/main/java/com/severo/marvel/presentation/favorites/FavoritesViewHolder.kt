package com.severo.marvel.presentation.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.severo.marvel.databinding.ItemCharacterBinding
import com.severo.marvel.framework.imageloader.ImageLoader
import com.severo.marvel.presentation.common.GenericViewHolder

class FavoritesViewHolder(
    itemBinding: ItemCharacterBinding,
    private val imageLoader: ImageLoader
) : GenericViewHolder<FavoriteItem>(itemBinding) {

    private val textName: TextView = itemBinding.textName
    private val imageCharacter: ImageView = itemBinding.imageCharacter

    override fun bind(data: FavoriteItem) {
        textName.text = data.name
        imageLoader.load(imageCharacter, data.imageUrl)
    }

    companion object {
        fun create(parent: ViewGroup, imageLoader: ImageLoader): FavoritesViewHolder {
            val itemBinding = ItemCharacterBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

            return FavoritesViewHolder(itemBinding, imageLoader)
        }
    }
}