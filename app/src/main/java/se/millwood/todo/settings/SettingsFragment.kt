package se.millwood.todo.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import se.millwood.todo.DataStoreManager
import se.millwood.todo.SortOrder
import se.millwood.todo.databinding.FragmentSettingsBinding
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    @Inject
    lateinit var dataStoreManager: DataStoreManager

    lateinit var binding: FragmentSettingsBinding


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
            when (dataStoreManager.sortOrder.first()) {
                SortOrder.ALPHABETICAL -> {
                    binding.radioButtonAlphabetical.isChecked = true
                }
                SortOrder.LAST_EDITED -> {
                    binding.radioButtonLastEdited.isChecked = true
                }
                SortOrder.TODO_LIST_SIZE -> {
                    binding.radioButtonTodoSize.isChecked = true
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

    private fun saveSortOrderPreferences(sortOrder: SortOrder) {
        lifecycleScope.launch {
            dataStoreManager.updateSortKey(sortOrder)
        }
    }

    private fun setupUpButton() {
        binding.settingsFragmentToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

}