package se.millwood.todo.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import se.millwood.todo.dataStore
import se.millwood.todo.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    lateinit var binding: FragmentSettingsBinding

    private fun saveSortOrderPreferences(sortOrder: SortOrder) {
        lifecycleScope.launch {
            requireContext().dataStore.edit { preferences ->
                preferences[sortOrderKey] = sortOrder.name
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater)
        setSavedSortOrder()
        setupSortOrderListener()
        setupUpButton()
        return binding.root
    }

    private fun setSavedSortOrder() {
        lifecycleScope.launch {
            val preferences = requireContext().dataStore.data.first()
            when (preferences[sortOrderKey]) {
                SortOrder.ALPHABETICAL.name -> {
                    binding.radioButtonAlphabetical.isChecked = true
                }
                SortOrder.LAST_EDITED.name -> {
                    binding.radioButtonLastEdited.isChecked = true
                }
                SortOrder.TODO_LIST_SIZE.name -> {
                    binding.radioButtonTodoSize.isChecked = true
                }
                else -> {
                    saveSortOrderPreferences(SortOrder.LAST_EDITED)
                    binding.radioButtonLastEdited.isChecked = true
                }
            }
        }
    }

    private fun setupSortOrderListener() {
        binding.sortOrderRadioButtons.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.radioButtonAlphabetical.id -> {
                    saveSortOrderPreferences(SortOrder.ALPHABETICAL)
                }
                binding.radioButtonLastEdited.id -> {
                    saveSortOrderPreferences(SortOrder.LAST_EDITED)
                }
                binding.radioButtonTodoSize.id -> {
                    saveSortOrderPreferences(SortOrder.TODO_LIST_SIZE)
                }
            }
        }
    }

    private fun setupUpButton() {
        binding.settingsFragmentToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    companion object {
        enum class SortOrder {
            ALPHABETICAL,
            LAST_EDITED,
            TODO_LIST_SIZE
        }
        val sortOrderKey = stringPreferencesKey("sort_order")
    }
}