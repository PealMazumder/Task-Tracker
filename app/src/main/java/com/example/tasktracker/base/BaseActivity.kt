package com.example.tasktracker.base

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.tasktracker.R
import dmax.dialog.SpotsDialog
import kotlin.properties.Delegates

abstract class BaseActivity<VM : BaseViewModel, VB : ViewBinding> : AppCompatActivity() {
    protected abstract val mViewModel: VM
    protected lateinit var mViewBinding: VB
    abstract fun getViewBinding(): VB

    var dialog: AlertDialog by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewBinding = getViewBinding()
        dialog = SpotsDialog(this, R.style.LoadingDialog)
        dialog.setCancelable(false)

        mViewModel.showLoader.observe(this) { isShow ->
            if (isShow && !dialog.isShowing) {
                dialog.show()
            } else {
                if (dialog.isShowing)
                    dialog.dismiss()
            }
        }
    }
}