package se.millwood.todo.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import se.millwood.todo.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater)
        val sharedPrefs = requireContext().getSharedPreferences(
            SETTINGS,
            Context.MODE_PRIVATE
        )
        val editor = sharedPrefs.edit()
        setSortOrder(sharedPrefs)
        saveSortOrder(editor)
        setupUpButton()
        return binding.root
    }

    private fun setSortOrder(sharedPrefs: SharedPreferences) {
        when (sharedPrefs.getString(SORT_ORDER_KEY, SortOrder.LAST_EDITED.name)) {

            SortOrder.ALPHABETICAL.name -> {
                binding.radioButtonAlphabetical.isChecked = true
            }
            SortOrder.LAST_EDITED.name -> {
                binding.radioButtonLastEdited.isChecked = true
            }
            SortOrder.TODO_LIST_SIZE.name -> {
                binding.radioButtonTodoSize.isChecked = true
            }
        }
    }

    private fun saveSortOrder(editor: SharedPreferences.Editor) {
        binding.sortOrderRadioButtons.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.radioButtonAlphabetical.id -> {
                    editor.putString(
                        SORT_ORDER_KEY,
                        SortOrder.ALPHABETICAL.name)
                }
                binding.radioButtonLastEdited.id -> {
                    editor.putString(
                        SORT_ORDER_KEY,
                        SortOrder.LAST_EDITED.name)
                }
                binding.radioButtonTodoSize.id -> {
                    editor.putString(
                        SORT_ORDER_KEY,
                        SortOrder.TODO_LIST_SIZE.name)
                }
            }
            editor.apply()
        }
    }

    private fun setupUpButton() {
        binding.settingsFragmentToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    companion object {
        const val SETTINGS = "settings"
        const val SORT_ORDER_KEY = "sort_order"

        enum class SortOrder {
            ALPHABETICAL,
            LAST_EDITED,
            TODO_LIST_SIZE
        }
    }
}