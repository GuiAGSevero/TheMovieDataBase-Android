package com.severo.marvel.presentation.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.severo.marvel.databinding.FragmentFavoritesBinding
import com.severo.marvel.framework.imageloader.ImageLoader
import com.severo.marvel.presentation.common.getGenericAdapterOf
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding: FragmentFavoritesBinding get() = _binding!!

    private val viewModel: FavoritesViewModel by viewModel()

    private val imageLoader: ImageLoader by inject()

    private val favoritesAdapter by lazy {
        getGenericAdapterOf {
            FavoritesViewHolder.Companion.create(it, imageLoader)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentFavoritesBinding.inflate(
        inflater,
        container,
        false
    ).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFavoritesAdapter()

        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            binding.flipperFavorites.displayedChild = when (uiState) {
                is FavoritesViewModel.UiState.ShowFavorite -> {
                    favoritesAdapter.submitList(uiState.favorites)
                    FLIPPER_CHILD_CHARACTERS
                }

                FavoritesViewModel.UiState.ShowEmpty -> {
                    favoritesAdapter.submitList(emptyList())
                    FLIPPER_CHILD_EMPTY
                }
            }
        }

        viewModel.getAll()
    }

    private fun initFavoritesAdapter() {
        binding.recyclerFavorites.run {
            setHasFixedSize(true)
            adapter = favoritesAdapter
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val FLIPPER_CHILD_CHARACTERS = 0
        private const val FLIPPER_CHILD_EMPTY = 1
    }
}