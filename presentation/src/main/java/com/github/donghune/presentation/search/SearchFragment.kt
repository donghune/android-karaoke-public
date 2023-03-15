package com.github.donghune.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.github.donghune.R
import com.github.donghune.databinding.SearchFragmentBinding
import com.github.donghune.presentation.adapter.SongRecyclerAdapter
import com.github.donghune.presentation.base.BaseFragment
import com.github.donghune.presentation.delegate.autoCleared
import com.github.donghune.presentation.dialog.PlayListSelectDialogUiState
import com.github.donghune.presentation.dialog.PlayListSelectDialogViewModel
import com.github.donghune.presentation.entity.PlayListModel
import com.github.donghune.presentation.latest.LatestActivity
import com.github.donghune.presentation.popularity.PopularityActivity
import com.github.donghune.presentation.util.addTextChangeStateFlow
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@AndroidEntryPoint
class SearchFragment : BaseFragment() {

    private var binding by autoCleared<SearchFragmentBinding>()
    private val viewModel: SearchViewModel by viewModels()
    private val dialogViewModel: PlayListSelectDialogViewModel by viewModels()

    private val latestAdapter: SongRecyclerAdapter by lazy {
        SongRecyclerAdapter(
            openOnClickListener = dialogViewModel::getPlayListWithIncludeWhether
        )
    }
    private val searchAdapter: SongRecyclerAdapter by lazy {
        SongRecyclerAdapter(
            openOnClickListener = dialogViewModel::getPlayListWithIncludeWhether
        )
    }
    private val popularityAdapter: SongRecyclerAdapter by lazy {
        SongRecyclerAdapter(
            openOnClickListener = dialogViewModel::getPlayListWithIncludeWhether
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @OptIn(FlowPreview::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerLatestSong.apply {
            adapter = latestAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
        binding.recyclerPopularitySong.apply {
            adapter = popularityAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
        binding.recyclerSearchResults.apply {
            adapter = searchAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        lifecycleScope.launch {
            viewModel.uiState.collect {
                when (it) {
                    is SearchUiState.Error -> loadingDialog.dismiss()
                    SearchUiState.Loading -> {
                        showSkeletonUi(true)
                    }
                    is SearchUiState.InitLoad -> {
                        showSkeletonUi(false)
                        binding.recyclerSearchResults.isVisible = false
                        binding.layoutRecommend.isVisible = true
                        binding.textSearchEmptyMessage.isVisible = false
                        latestAdapter.submitList(it.latestSongs)
                        popularityAdapter.submitList(it.popularitySongs)
                    }
                    is SearchUiState.SearchResult -> {
                        binding.recyclerSearchResults.isVisible = true
                        binding.layoutRecommend.isVisible = false
                        searchAdapter.submitList(it.searchSongs) {
                            binding.recyclerSearchResults.layoutManager
                                ?.scrollToPosition(0)
                        }
                        binding.textSearchEmptyMessage.isVisible = it.searchSongs.isEmpty()
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.searchType.collect {
                when (it) {
                    SearchViewModel.SearchType.TITLE -> R.drawable.baseline_title_24
                    SearchViewModel.SearchType.SINGER -> R.drawable.baseline_person_24
                    SearchViewModel.SearchType.TITLE_WITH_SINGER -> R.drawable.all
                }.also { drawableId ->
                    binding.imageSearchType.setBackgroundDrawable(
                        ResourcesCompat.getDrawable(
                            resources,
                            drawableId,
                            null
                        )
                    )
                }
            }
        }

        lifecycleScope.launch {
            dialogViewModel.uiState.collect {
                when (it) {
                    is PlayListSelectDialogUiState.Success -> {
                        MaterialAlertDialogBuilder(requireContext())
                            .setTitle("추가할 그룹을 선택해주세요")
                            .setPositiveButton("완료") { dialog, _ -> dialog.dismiss() }
                            .setMultiChoiceItems(
                                it.playListList.keys.map(PlayListModel::name).toTypedArray(),
                                it.playListList.values.toBooleanArray()
                            ) { _, index, value ->
                                dialogViewModel.setPlayListForSong(
                                    songModel = it.songModel,
                                    playListId = it.playListList.keys.toList()[index].id,
                                    isChecked = value
                                )
                            }
                            .setOnDismissListener { dialogViewModel.dismissDialog() }
                            .show()
                    }
                    else -> return@collect
                }
            }
        }

        binding.imageSearchType.setOnClickListener {
            viewModel.updateSearchType(viewModel.searchType.value.next())
            viewModel.search(viewModel.searchType.value, binding.fieldSearch.text.toString())
        }

        binding.fieldSearch.addTextChangeStateFlow()
            .onEach {
                if (it?.isEmpty() == true) {
                    viewModel.loadLatestAndPopularitySongs()
                }
            }
            .filter { !it.isNullOrEmpty() }
            .debounce(500L)
            .onEach { viewModel.search(viewModel.searchType.value, it.toString()) }
            .launchIn(lifecycleScope)

        binding.textPopularityTitleViewAll.setOnClickListener {
            PopularityActivity.start(requireContext())
        }

        binding.textLatestTitleViewAll.setOnClickListener {
            LatestActivity.start(requireContext())
        }
    }

    private fun showSkeletonUi(isVisible: Boolean) {
        binding.sfLatestSong.isVisible = isVisible
        binding.sfPopularitySong.isVisible = isVisible
        if (isVisible) {
            binding.sfLatestSong.startShimmer()
            binding.sfPopularitySong.startShimmer()
        } else {
            binding.sfLatestSong.stopShimmer()
            binding.sfPopularitySong.stopShimmer()
        }
    }

    companion object {
        fun newInstance() = SearchFragment()
        private val TAG = SearchFragment::class.java.simpleName
    }
}
