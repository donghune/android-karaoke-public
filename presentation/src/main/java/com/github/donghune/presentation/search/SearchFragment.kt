package com.github.donghune.presentation.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.donghune.R
import com.github.donghune.databinding.SearchFragmentBinding
import com.github.donghune.domain.entity.SearchType
import com.github.donghune.presentation.adapter.SongRecyclerAdapter
import com.github.donghune.presentation.base.BaseFragment
import com.github.donghune.presentation.delegate.autoCleared
import com.github.donghune.presentation.dialog.GroupSelectDialogUiState
import com.github.donghune.presentation.dialog.GroupSelectDialogViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.github.donghune.presentation.search.SearchViewModel.SearchType as Type

@AndroidEntryPoint
class SearchFragment : BaseFragment() {

    private var binding by autoCleared<SearchFragmentBinding>()
    private val viewModel: SearchViewModel by viewModels()
    private val dialogViewModel: GroupSelectDialogViewModel by viewModels()

    private val recyclerViewAdapter: SongRecyclerAdapter by lazy {
        SongRecyclerAdapter(dialogViewModel)
    }
    private val searchTypeAdapter by lazy {
        ArrayAdapter(
            requireContext(),
            R.layout.item_expose_list,
            SearchType.values().map { it.korName }
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
        binding.recyclerSong.apply {
            adapter = recyclerViewAdapter
            layoutManager = LinearLayoutManager(context)
        }

        (binding.dropdownMenu.editText as? AutoCompleteTextView)?.apply {
            setAdapter(searchTypeAdapter)
            setText(adapter.getItem(0).toString(), false)
        }

        lifecycleScope.launch {
            viewModel.uiState.collect {
                when (it) {
                    is SearchUiState.Error -> loadingDialog.dismiss()
                    SearchUiState.Loading -> loadingDialog.show()
                    is SearchUiState.Success -> {
                        loadingDialog.dismiss()
                        recyclerViewAdapter.submitList(it.songs)
                    }
                }
            }
        }

        lifecycleScope.launch {
            dialogViewModel.uiState.collect {
                when (it) {
                    is GroupSelectDialogUiState.Error -> loadingDialog.dismiss()
                    GroupSelectDialogUiState.Loading -> loadingDialog.show()
                    is GroupSelectDialogUiState.Success -> {
                        loadingDialog.dismiss()
                        val items =
                            it.groupList.keys.map { groupModel -> groupModel.name }.toTypedArray()
                        val values = it.groupList.values.toBooleanArray()

                        MaterialAlertDialogBuilder(requireContext())
                            .setTitle("추가할 그룹을 선택해주세요")
                            .setPositiveButton("완료") { dialog, _ -> dialog.dismiss() }
                            .setMultiChoiceItems(items, values) { _, index, value ->
                                Log.d(TAG, "onClick: $index. $value")
                            }
                            .show()
                    }
                }
            }
        }

        binding.fieldKeyword.editText?.addTextChangedListener {
            searchSong(it.toString())
        }

        binding.dropdownMenu.editText?.addTextChangedListener {
            searchSong(binding.fieldKeyword.editText?.text.toString())
        }

        binding.fieldKeyword.editText?.setText("")
    }

    private fun searchSong(keyword: String) {
        val selectItem = (binding.dropdownMenu.editText?.text?.toString() ?: "")

        val searchType = when (val type = SearchType.values().find { it.korName == selectItem }) {
            SearchType.TITLE -> Type.Keyword("%$keyword%", 0, 100)
            SearchType.SINGER -> Type.Singer("%$keyword%", 0, 100)
            SearchType.TITLE_WITH_SINGER -> Type.TitleWithSinger("%$keyword%", 0, 100)
            else -> throw UnsupportedOperationException("Unsupported search type : $type")
        }

        viewModel.search(searchType)
    }

    companion object {
        fun newInstance() = SearchFragment()
        private val TAG = SearchFragment::class.java.simpleName
    }
}
