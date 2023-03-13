package com.github.donghune.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.github.donghune.presentation.search.SearchViewModel.SearchType as Type

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
                    SearchUiState.Loading -> loadingDialog.show()
                    is SearchUiState.InitLoad -> {
                        loadingDialog.dismiss()
                        binding.recyclerSearchResults.isVisible = false
                        binding.layoutRecommend.isVisible = true
                        latestAdapter.submitList(it.latestSongs)
                        popularityAdapter.submitList(it.popularitySongs)
                    }
                    is SearchUiState.SearchResult -> {
                        loadingDialog.dismiss()
                        binding.recyclerSearchResults.isVisible = true
                        binding.layoutRecommend.isVisible = false
                        searchAdapter.submitList(it.searchSongs)
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
                    SearchViewModel.SearchType.TITLE_WITH_SINGER -> R.drawable.baseline_title_24
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
                    is PlayListSelectDialogUiState.Empty -> loadingDialog.dismiss()
                    is PlayListSelectDialogUiState.Error -> loadingDialog.dismiss()
                    PlayListSelectDialogUiState.Loading -> loadingDialog.show()
                    is PlayListSelectDialogUiState.Success -> {
                        loadingDialog.dismiss()
                        MaterialAlertDialogBuilder(requireContext())
                            .setTitle("추가할 그룹을 선택해주세요")
                            .setPositiveButton("완료") { dialog, _ -> dialog.dismiss() }
                            .setMultiChoiceItems(
                                it.playListList.keys.map(PlayListModel::name).toTypedArray(),
                                it.playListList.values.toBooleanArray()
                            ) { _, index, value ->
                                dialogViewModel.setPlayListForSong(
                                    songId = it.songId,
                                    playListId = it.playListList.keys.toList()[index].id,
                                    isChecked = value
                                )
                            }
                            .setOnDismissListener { dialogViewModel.dismissDialog() }
                            .show()
                    }
                }
            }
        }

        binding.imageSearchType.setOnClickListener {
            viewModel.updateSearchType(viewModel.searchType.value.next())
            searchSong(viewModel.searchType.value, binding.fieldSearch.text.toString())
        }

        binding.fieldSearch.addTextChangedListener {
            searchSong(viewModel.searchType.value, it.toString())
        }

        binding.textPopularityTitleViewAll.setOnClickListener {
            PopularityActivity.start(requireContext())
        }

        binding.textLatestTitleViewAll.setOnClickListener {
            LatestActivity.start(requireContext())
        }

        binding.fieldSearch.setText("")
        searchSong(viewModel.searchType.value, binding.fieldSearch.text.toString())
    }

    private fun searchSong(searchType: Type, keyword: String) {
        if (keyword.isEmpty()) {
            return
        }
        viewModel.search(searchType, "%$keyword%")
    }

    companion object {
        fun newInstance() = SearchFragment()
        private val TAG = SearchFragment::class.java.simpleName
    }
}
