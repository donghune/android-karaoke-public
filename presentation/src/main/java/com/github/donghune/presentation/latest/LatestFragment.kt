package com.github.donghune.presentation.latest

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.donghune.presentation.R
import com.github.donghune.presentation.adapter.SongRecyclerAdapter
import com.github.donghune.presentation.base.BaseFragment
import com.github.donghune.presentation.databinding.LatestFragmentBinding
import com.github.donghune.presentation.dialog.GroupSelectDialogViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LatestFragment : BaseFragment<LatestFragmentBinding>(R.layout.latest_fragment) {

    private val viewModel: LatestViewModel by viewModels()
    private val dialogViewModel: GroupSelectDialogViewModel by viewModels()
    private val recyclerAdapter: SongRecyclerAdapter by lazy { SongRecyclerAdapter(dialogViewModel) }

    override fun LatestFragmentBinding.onCreateView() {
        recyclerSong.apply {
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

    override fun LatestFragmentBinding.onViewCreated() {
        viewModel.getLatestSongList()
    }

    companion object {
        fun newInstance() = LatestFragment()
        private val TAG = LatestFragment::class.java.simpleName
    }
}
