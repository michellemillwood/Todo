package se.millwood.todo.todoedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import se.millwood.todo.R
import se.millwood.todo.databinding.FragmentEditDialogBinding
import se.millwood.todo.datetimepicker.DateAndTimePickerDialogFragment
import java.util.*

@AndroidEntryPoint
class TodoEditDialogFragment : DialogFragment() {

    private val viewModel: TodoEditViewModel by viewModels()

    private lateinit var binding: FragmentEditDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditDialogBinding.inflate(inflater, container, false)
        setupAlarmButton()
        if (viewModel.todoId == null) {
            setupSaveButtonCreate()
        }
        else {
            setupSaveButtonUpdate()
        }
        return binding.root
    }

    private fun setupAlarmButton() {
        setFragmentResultListener(
            DateAndTimePickerDialogFragment.DATE_TIME_KEY
        ) { _, bundle ->
            val alarmDateTime = bundle.getSerializable("alarmTime") as Calendar
            viewModel.alarm = alarmDateTime.toInstant()
        }
        binding.addAlarmButton.setOnClickListener {
            findNavController().navigate(R.id.dateAndTimePickerDialogFragment)
        }
    }

    private fun setupSaveButtonUpdate() {
        lifecycleScope.launch {
            binding.editTodo.setText(viewModel.getTodoTitle())
        }
        viewModel.getTodoAlarm()
        binding.saveButton.setOnClickListener {
            viewModel.updateTodoTitleAndAlarm(
                title = binding.editTodo.text.toString()
            )
            dismiss()
        }
    }

    private fun setupSaveButtonCreate() {
        binding.saveButton.setOnClickListener {
                viewModel.createTodo(
                    title = binding.editTodo.text.toString()
                )
            dismiss()
        }
    }

}