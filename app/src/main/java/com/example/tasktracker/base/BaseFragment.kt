package com.example.tasktracker.base

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.tasktracker.R
import dmax.dialog.SpotsDialog
import kotlin.properties.Delegates

abstract class BaseFragment<VM : BaseViewModel, VB : ViewBinding> : Fragment() {
    protected abstract val mViewModel: VM
    protected lateinit var mViewBinding: VB

    abstract fun getViewBinding(): VB

    var dialog: AlertDialog by Delegates.notNull()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewBinding = getViewBinding()
        dialog = SpotsDialog(requireContext(), R.style.LoadingDialog)
        dialog.setCancelable(false)
        return mViewBinding.root
    }

    //region Description
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.showLoader.observe(viewLifecycleOwner) { isShow ->
            if (isShow && !dialog.isShowing) {
                dialog.show()
            } else {
                if (dialog.isShowing)
                    dialog.dismiss()
            }
        }
    }
    //endregion
}