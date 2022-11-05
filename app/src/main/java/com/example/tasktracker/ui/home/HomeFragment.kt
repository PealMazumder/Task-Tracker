package com.example.tasktracker.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasktracker.R
import com.example.tasktracker.base.BaseFragment
import com.example.tasktracker.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    private val adapter = TaskAdapter()
    override val mViewModel: HomeViewModel by viewModels()
    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.getTaskFromServer()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewBinding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mViewBinding.recyclerView.adapter = adapter
        mViewModel.apiResponse.observe(viewLifecycleOwner) {
            it.data?.let { it ->
                adapter.submitList(it)
                mViewBinding.recyclerView.startLayoutAnimation()
            }
        }
        mViewBinding.btnAddTask.setOnClickListener {
            navigateToAddTaskFragment()
        }
    }

    private fun navigateToAddTaskFragment() {
        Navigation.findNavController(requireView())
            .navigate(R.id.action_homeFragment_to_addTaskFragment)
    }
}
