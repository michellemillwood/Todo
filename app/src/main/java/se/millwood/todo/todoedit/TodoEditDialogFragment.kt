package se.millwood.todo.todoedit

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import se.millwood.todo.R
import se.millwood.todo.databinding.FragmentEditDialogBinding
import se.millwood.todo.datetimepicker.DateAndTimePickerDialogFragment
import se.millwood.todo.tododelete.TodoDeleteDialogFragment
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
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
        setupSaveButton()
        setupAlarmButton()
        setupDeleteButton()
        setupClearAlarmButton()
        lifecycleScope.launch {
            binding.editTodo.setText(viewModel.getTodoTitle())
            observeSetAlarm()
        }
        setFragmentResultListener(
            DateAndTimePickerDialogFragment.DATE_TIME_FRAGMENT_RESULT_KEY
        ) { _, bundle ->
            val alarmDateTime = bundle.getSerializable("alarmTime") as Calendar
            viewModel.updateTodoAlarm(alarmDateTime.toInstant())
        }
        setFragmentResultListener(
            TodoDeleteDialogFragment.TODO_DELETE_FRAGMENT_RESULT_KEY
        ) { _, bundle ->
            val shouldBeDeleted = bundle.getBoolean(TodoDeleteDialogFragment.SHOULD_DELETE_KEY)
            if (shouldBeDeleted) {
                viewModel.todoId?.let { viewModel.deleteTodo(it) }
                dismiss()
            }
        }
        return binding.root
    }

    private fun setupDeleteButton() {
        binding.deleteTodo.setOnClickListener {
            lifecycleScope.launch {
                val bundle = bundleOf(
                    TodoDeleteDialogFragment.TODO_DELETE_ARGS to
                            TodoDeleteDialogFragment.TodoDeleteArguments(
                                todoId = viewModel.todoId.toString(),
                                title = viewModel.getTodoTitle()
                            )
                )
                findNavController().navigate(R.id.todoDeleteDialogFragment, bundle)
            }
        }
    }

    private fun setupAlarmButton() {
        binding.alarmContainer.setOnClickListener {
            findNavController().navigate(R.id.dateAndTimePickerDialogFragment)
        }
    }

    private fun setupClearAlarmButton() {
        binding.clearAlarm.setOnClickListener {
            viewModel.updateTodoAlarm(alarm = null)
        }
    }

    private suspend fun observeSetAlarm() {
        viewModel.alarm.collect { alarmTime ->
            if (alarmTime != null) {
                val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withZone(
                    ZoneId.systemDefault()
                )
                binding.alarmDate.text = formatter.format(alarmTime)
                binding.alarmIcon.setImageDrawable(
                    AppCompatResources.getDrawable(
                        binding.alarmIcon.context,
                        R.drawable.ic_baseline_notifications_24
                    )
                )
                binding.clearAlarm.visibility = View.VISIBLE
            } else {
                binding.alarmDate.text = "Add alarm"
                binding.alarmIcon.setImageDrawable(
                    AppCompatResources.getDrawable(
                        binding.alarmIcon.context,
                        R.drawable.ic_baseline_notifications_none_24
                    )
                )
                binding.clearAlarm.visibility = View.GONE
            }
        }
    }

    private fun setupSaveButton() {
        binding.saveButton.setOnClickListener {
            viewModel.updateTodoTitle(
                title = binding.editTodo.text.toString()
            )
            dismiss()
        }
    }

    @Parcelize
    data class TodoEditArguments(
        val cardId: String,
        val todoId: String
    ) : Parcelable


    companion object {
        const val TODO_EDIT_ARGS = "todo_edit_args"
    }

}
