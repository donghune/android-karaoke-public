package com.github.donghune.presentation.latest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.donghune.databinding.LatestFragmentBinding
import com.github.donghune.presentation.adapter.SongRecyclerAdapter
import com.github.donghune.presentation.base.BaseActivity
import com.github.donghune.presentation.dialog.PlayListSelectDialogUiState
import com.github.donghune.presentation.dialog.PlayListSelectDialogViewModel
import com.github.donghune.presentation.entity.PlayListModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LatestActivity : BaseActivity() {

    private val binding by lazy { LatestFragmentBinding.inflate(layoutInflater) }
    private val viewModel: LatestViewModel by viewModels()
    private val dialogViewModel: PlayListSelectDialogViewModel by viewModels()
    private val recyclerAdapter: SongRecyclerAdapter by lazy {
        SongRecyclerAdapter(
            openOnClickListener = dialogViewModel::getPlayListWithIncludeWhether
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.recyclerSong.apply {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    this@LatestActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
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
                    is PlayListSelectDialogUiState.Empty -> loadingDialog.dismiss()
                    is PlayListSelectDialogUiState.Error -> loadingDialog.dismiss()
                    PlayListSelectDialogUiState.Loading -> loadingDialog.show()
                    is PlayListSelectDialogUiState.Success -> {
                        loadingDialog.dismiss()
                        MaterialAlertDialogBuilder(this@LatestActivity)
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
                }
            }
        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    companion object {
        private const val TAG = "LatestFragment"

        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, LatestActivity::class.java)
            context.startActivity(starter)
        }
    }
}
