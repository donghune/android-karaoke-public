package com.github.donghune.presentation.latest

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.donghune.databinding.LatestFragmentBinding
import com.github.donghune.presentation.adapter.SongRecyclerAdapter
import com.github.donghune.presentation.base.BaseFragment
import com.github.donghune.presentation.delegate.autoCleared
import com.github.donghune.presentation.dialog.GroupSelectDialogUiState
import com.github.donghune.presentation.dialog.GroupSelectDialogViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LatestFragment : BaseFragment() {

    private var binding by autoCleared<LatestFragmentBinding>()
    private val viewModel: LatestViewModel by viewModels()
    private val dialogViewModel: GroupSelectDialogViewModel by viewModels()
    private val recyclerAdapter: SongRecyclerAdapter by lazy { SongRecyclerAdapter(dialogViewModel) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LatestFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerSong.apply {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(context)
        }

        lifecycleScope.launch {
            viewModel.uiState.collect {
                when (it) {
                    is LatestUiState.Error -> loadingDialog.dismiss()
                    LatestUiState.Loading -> loadingDialog.show()
                    is LatestUiState.Success -> {
                        loadingDialog.dismiss()
                        recyclerAdapter.submitList(it.songs)
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
    }

    companion object {
        fun newInstance() = LatestFragment()
        private val TAG = LatestFragment::class.java.simpleName
    }
}
