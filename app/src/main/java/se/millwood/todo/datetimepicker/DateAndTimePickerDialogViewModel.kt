package se.millwood.todo.datetimepicker

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DateAndTimePickerDialogViewModel @Inject constructor(val calendar: Calendar) : ViewModel()