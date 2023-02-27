package com.github.donghune.presentation.search

import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.donghune.domain.entity.SearchType
import com.github.donghune.presentation.R
import com.github.donghune.presentation.adapter.SongRecyclerAdapter
import com.github.donghune.presentation.base.BaseFragment
import com.github.donghune.presentation.databinding.SearchFragmentBinding
import com.github.donghune.presentation.dialog.GroupSelectDialogViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import com.github.donghune.presentation.search.SearchViewModel.SearchType as Type

@AndroidEntryPoint
class SearchFragment : BaseFragment<SearchFragmentBinding>(R.layout.search_fragment) {

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

    override fun SearchFragmentBinding.onCreateView() {
        recyclerSong.apply {
            adapter = recyclerViewAdapter
            layoutManager = LinearLayoutManager(context)
        }

        (dropdownMenu.editText as? AutoCompleteTextView)?.apply {
            setAdapter(searchTypeAdapter)
            setText(adapter.getItem(0).toString(), false)
        }

        this@SearchFragment.viewModel.songList.observe(requireActivity()) {
            recyclerViewAdapter.submitList(it)
        }

        dialogViewModel.items.observe(requireActivity()) {
            Log.d(TAG, "onCreateView() called it = [$it]")

            val items = it.keys.map { groupModel -> groupModel.name }.toTypedArray()
            val values = it.values.toBooleanArray()

            MaterialAlertDialogBuilder(requireContext())
                .setTitle("추가할 그룹을 선택해주세요")
                .setPositiveButton("완료") { dialog, _ -> dialog.dismiss() }
                .setMultiChoiceItems(items, values) { _, index, value ->
                    Log.d(TAG, "onClick: $index. $value")
                }
                .show()
        }
    }

    override fun SearchFragmentBinding.onViewCreated() {
        fieldKeyword.editText?.addTextChangedListener {
            searchSong(it.toString())
        }
        dropdownMenu.editText?.addTextChangedListener {
            searchSong(fieldKeyword.editText?.text.toString())
        }
        fieldKeyword.editText?.setText("")
    }

    private fun searchSong(keyword: String) {
        Log.d(TAG, "searchSong() called  with: keyword = [$keyword]")
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
