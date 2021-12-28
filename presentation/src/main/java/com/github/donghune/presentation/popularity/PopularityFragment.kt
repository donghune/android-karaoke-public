package com.github.donghune.presentation.popularity

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.donghune.presentation.R
import com.github.donghune.presentation.databinding.PopularityFragmentBinding
import com.github.donghune.presentation.adapter.PopularitySongRecyclerAdapter
import com.github.donghune.presentation.base.BaseFragment
import com.github.donghune.presentation.dialog.GroupSelectDialogViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularityFragment : BaseFragment<PopularityFragmentBinding>(R.layout.popularity_fragment) {

    private val viewModel : PopularityViewModel by viewModels()
    private val dialogViewModel : GroupSelectDialogViewModel by viewModels()
    private val recyclerAdapter: PopularitySongRecyclerAdapter by lazy { PopularitySongRecyclerAdapter(dialogViewModel) }

    override fun PopularityFragmentBinding.onCreateView() {
        binding.recyclerSong.apply {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.songList.observe(requireActivity()) {
            recyclerAdapter.submitList(it)
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

    override fun PopularityFragmentBinding.onViewCreated() {
        viewModel.getPopularitySongList()
    }

    companion object {
        fun newInstance() = PopularityFragment()
        private val TAG = PopularityFragment::class.java.simpleName
    }

}