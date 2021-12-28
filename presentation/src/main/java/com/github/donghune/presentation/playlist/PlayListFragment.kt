package com.github.donghune.presentation.playlist

import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.donghune.presentation.R
import com.github.donghune.presentation.databinding.DialogGroupAddBinding
import com.github.donghune.presentation.databinding.PlaylistFragmentBinding
import com.github.donghune.presentation.adapter.GroupRecyclerAdapter
import com.github.donghune.presentation.base.BaseFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayListFragment : BaseFragment<PlaylistFragmentBinding>(R.layout.playlist_fragment) {

    private val viewModel: PlayListViewModel by viewModels()
    private val recyclerAdapter: GroupRecyclerAdapter by lazy { GroupRecyclerAdapter() }

    override fun PlaylistFragmentBinding.onCreateView() {
        recyclerGroup.apply {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, VERTICAL))
        }

        viewModel.playList.observe(requireActivity()) {
            recyclerAdapter.submitList(it)
        }
    }

    override fun PlaylistFragmentBinding.onViewCreated() {
        floating.setOnClickListener {
            val binding: DialogGroupAddBinding =
                DataBindingUtil.inflate(LayoutInflater.from(context),
                    R.layout.dialog_group_add,
                    null,
                    false)

            MaterialAlertDialogBuilder(it.context)
                .setView(binding.root)
                .setTitle("그룹 추가하기")
                .setMessage("추가할 그룹의 이름을 입력해주세요")
                .setPositiveButton("추가") { dialog, _ ->
                    viewModel.addPlayListGroup(binding.editInput.text.toString())
                    viewModel.getPlayListGroup()
                    dialog.dismiss()
                }
                .setNegativeButton("취소") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        viewModel.getPlayListGroup()
    }

    companion object {
        fun newInstance() = PlayListFragment()
        private val TAG = PlayListFragment::class.java.simpleName
    }

}