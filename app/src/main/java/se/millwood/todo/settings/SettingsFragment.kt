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
        val sharedPrefs = requireContext().getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        setSortOrder(sharedPrefs)
        saveSortOrder(editor)
        setupUpButton()
        return binding.root
    }

    private fun setSortOrder(sharedPrefs: SharedPreferences) {
        when (sharedPrefs.getString(SORT_ORDER, "")) {
            SORT_ALPHABETICAL -> binding.radioButtonAlphabetical.isChecked = true
            SORT_LAST_EDITED -> binding.radioButtonLastEdited.isChecked = true
        }
    }

    private fun saveSortOrder(editor: SharedPreferences.Editor) {
        binding.sortOrderRadioButtons.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.radioButtonAlphabetical.id -> {
                    editor.putString(SORT_ORDER, SORT_ALPHABETICAL)
                }
                binding.radioButtonLastEdited.id -> {
                    editor.putString(SORT_ORDER, SORT_LAST_EDITED)
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
        const val SORT_ORDER = "sort_order"
        const val SORT_ALPHABETICAL = "alphabetical"
        const val SORT_LAST_EDITED = "last_edited"
    }

}