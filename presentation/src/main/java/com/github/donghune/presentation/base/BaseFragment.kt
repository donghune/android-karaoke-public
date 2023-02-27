package com.github.donghune.presentation.base

import androidx.fragment.app.Fragment
import com.github.donghune.presentation.view.LoadingDialog

abstract class BaseFragment : Fragment() {
    val loadingDialog by lazy { LoadingDialog(requireContext()) }
}
