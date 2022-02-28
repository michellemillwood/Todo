package se.millwood.todo.datetimepicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import se.millwood.todo.databinding.FragmentDateAndTimePickerBinding
import java.util.*

@AndroidEntryPoint
class DateAndTimePickerDialogFragment : DialogFragment(), DatePicker.OnDateChangedListener, TimePicker.OnTimeChangedListener {

    private val viewModel: DateAndTimePickerDialogViewModel by viewModels()
    private lateinit var binding: FragmentDateAndTimePickerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDateAndTimePickerBinding.inflate(inflater, container, false)
        binding.datePicker.visibility = View.VISIBLE
        binding.datePicker.setOnDateChangedListener(this)
        binding.timePicker.setIs24HourView(true)
        binding.timePicker.setOnTimeChangedListener(this)
        binding.setAlarmButton.setOnClickListener {
            setFragmentResult(
                DATE_TIME_KEY,
                bundleOf("alarmTime" to viewModel.calendar)
            )
            findNavController().popBackStack()
        }
        return binding.root
    }

    override fun onDateChanged(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        viewModel.calendar.set(Calendar.YEAR, year)
        viewModel.calendar.set(Calendar.MONTH, month)
        viewModel.calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        binding.datePicker.visibility = View.GONE
        binding.timepickerContainer.visibility = View.VISIBLE
    }


    override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {
        viewModel.calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        viewModel.calendar.set(Calendar.MINUTE, minute)
    }

    companion object {
        const val DATE_TIME_KEY = "date_time_key"
    }
}