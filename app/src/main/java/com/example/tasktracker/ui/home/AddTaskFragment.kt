package com.example.tasktracker.ui.home

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tasktracker.R
import com.example.tasktracker.databinding.FragmentAddTaskBinding
import com.example.tasktracker.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*

class AddTaskFragment : Fragment() {

    private lateinit var binding: FragmentAddTaskBinding
    private lateinit var addTaskBinding: FragmentBottomSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDatePicker()
        binding.iBtnPlus.setOnClickListener { initBottomSheetDialog() }
    }

    private fun setDatePicker() {
        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)
        binding.tvShowDate.text = String.format("%02d-%02d-%04d", year, month + 1, day)


        binding.ivDropDownDate.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                { _, mYear, mMonth, mDayOfMonth ->
                    binding.tvShowDate.text =
                        String.format("%02d-%02d-%04d", mYear, mMonth + 1, mDayOfMonth)
                }, year, month, day
            ).show()
        }
    }

    private fun initBottomSheetDialog() {
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.fragment_bottom_sheet, null)

        dialog.setContentView(view)
        dialog.show()
    }
}