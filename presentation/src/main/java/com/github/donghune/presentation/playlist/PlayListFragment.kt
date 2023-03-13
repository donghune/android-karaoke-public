package com.github.donghune.presentation.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.donghune.databinding.DialogPlayListAddBinding
import com.github.donghune.databinding.PlaylistFragmentBinding
import com.github.donghune.presentation.adapter.PlayListRecyclerAdapter
import com.github.donghune.presentation.base.BaseFragment
import com.github.donghune.presentation.delegate.autoCleared
import com.github.donghune.presentation.playlist.songs.PlayListSongsActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlayListFragment : BaseFragment() {

    private var binding by autoCleared<PlaylistFragmentBinding>()
    private val viewModel: PlayListViewModel by viewModels()
    private val recyclerAdapter: PlayListRecyclerAdapter by lazy {
        PlayListRecyclerAdapter(
            onClickListener = { PlayListSongsActivity.start(requireContext(), it.id) }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PlaylistFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerPlayList.apply {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, VERTICAL))
        }

        lifecycleScope.launch {
            viewModel.PlayLists.collect {
                recyclerAdapter.submitList(it)
            }
        }

        binding.floating.setOnClickListener {
            val binding: DialogPlayListAddBinding =
                DialogPlayListAddBinding.inflate(
                    LayoutInflater.from(context),
                    null,
                    false
                )

            MaterialAlertDialogBuilder(it.context)
                .setView(binding.root)
                .setTitle("그룹 추가하기")
                .setMessage("추가할 그룹의 이름을 입력해주세요")
                .setPositiveButton("추가") { dialog, _ ->
                    val PlayListName = binding.editInput.text.toString()
                    if (PlayListName.isEmpty()) {
                        Toast.makeText(requireContext(), "그룹이름을 입력해주세요", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }
                    viewModel.addPlayListPlayList(binding.editInput.text.toString())
                    dialog.dismiss()
                }
                .setNegativeButton("취소") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    companion object {
        fun newInstance() = PlayListFragment()
        private val TAG = PlayListFragment::class.java.simpleName
    }
}
