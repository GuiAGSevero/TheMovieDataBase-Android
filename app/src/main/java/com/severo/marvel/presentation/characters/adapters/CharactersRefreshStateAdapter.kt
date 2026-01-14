package com.severo.marvel.presentation.characters.adapters

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class CharactersRefreshStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<CharactersRefreshStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = CharactersRefreshStateViewHolder.Companion.create(parent, retry)

    override fun onBindViewHolder(
        holder: CharactersRefreshStateViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)
}