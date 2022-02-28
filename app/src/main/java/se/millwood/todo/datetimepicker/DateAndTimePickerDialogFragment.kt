package se.millwood.todo.datetimepicker

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class DateAndTimePickerDialogFragment : DialogFragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private val viewModel: DateAndTimePickerDialogViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return DatePickerDialog(
            requireContext(),
            this,
            viewModel.calendar.get(Calendar.YEAR),
            viewModel.calendar.get(Calendar.MONTH),
            viewModel.calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        viewModel.calendar.set(Calendar.YEAR, year)
        viewModel.calendar.set(Calendar.MONTH, month)
        viewModel.calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        createTimePickerDialog()
    }

    private fun createTimePickerDialog() {
        TimePickerDialog(
            requireContext(),
            this,
            viewModel.calendar.get(Calendar.HOUR_OF_DAY),
            viewModel.calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        viewModel.calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        viewModel.calendar.set(Calendar.MINUTE, minute)
        setFragmentResult(
            DATE_TIME_KEY,
            bundleOf("alarmTime" to viewModel.calendar)
        )
    }

    // so that this function is not called after setting date
    override fun onDismiss(dialog: DialogInterface) {
        //super.onDismiss(dialog)
    }

    companion object {
        const val DATE_TIME_KEY = "date_time_key"
    }
}