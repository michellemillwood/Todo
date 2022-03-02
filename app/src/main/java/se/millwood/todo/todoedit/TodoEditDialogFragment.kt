package se.millwood.todo.todoedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import se.millwood.todo.R
import se.millwood.todo.databinding.FragmentEditDialogBinding
import se.millwood.todo.datetimepicker.DateAndTimePickerDialogFragment
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
        setupClearAlarmButton()
        lifecycleScope.launch {
            binding.editTodo.setText(viewModel.getTodoTitle())
            observeSetAlarm()
        }
        setFragmentResultListener(
            DateAndTimePickerDialogFragment.DATE_TIME_KEY
        ) { _, bundle ->
            val alarmDateTime = bundle.getSerializable("alarmTime") as Calendar
            viewModel.updateTodoAlarm(alarmDateTime.toInstant())
        }
        return binding.root
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

}
