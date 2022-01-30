package space.rodionov.porosenokpetr.feature_driller.presentation.driller

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.databinding.BottomsheetFilterBinding
import space.rodionov.porosenokpetr.feature_driller.Constants.TAG_PETR
import space.rodionov.porosenokpetr.feature_driller.presentation.base.BaseBottomSheetDialogFragment

@AndroidEntryPoint
class FilterBottomSheet : BaseBottomSheetDialogFragment(), CompoundButton.OnCheckedChangeListener {

    companion object {
        const val FILTER_BOTTOM_SHEET = "filterBottomSheet"
    }

    private val binding: BottomsheetFilterBinding by lazy {
        BottomsheetFilterBinding.inflate(layoutInflater)
    }

    private val vmDriller: DrillerViewModel by viewModels({
        requireParentFragment()
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        Log.d(TAG_PETR, "onCreateView: CALLED")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Log.d(TAG_PETR, "onViewCreated: CALLED")
        initModeObserver(binding.root, viewLifecycleOwner.lifecycleScope)

        binding.apply {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                vmDriller.categories.collectLatest { cats ->
                    val categories = cats ?: return@collectLatest
                    val activeCats = categories.filter { cat -> cat.isCategoryActive }
//                    Log.d(TAG_PETR, "OBSERVER: cats.size = ${cats.size}, activecats.size = ${activeCats.size}")

                    cbActivateAll.setOnCheckedChangeListener(null)

                    chipGroupCategories.removeAllViews()
                    categories.forEach { cat ->
                        val newChip = Chip(requireContext())
                        newChip.text = cat.name
                        newChip.isChecked = cat.isCategoryActive
                        chipGroupCategories.addView(newChip)
                    }
                    chipGroupCategories.children.forEach { chip ->
                        (chip as Chip).setOnCheckedChangeListener(null)
                    }
                    cbActivateAll.isChecked = activeCats.size == categories.size

                    chipGroupCategories.children.forEach { chip ->
                        (chip as Chip).setOnCheckedChangeListener(this@FilterBottomSheet)
                    }

                    cbActivateAll.setOnCheckedChangeListener { cb, isChecked ->
                        if (isChecked) {
                            vmDriller.onCheckBoxTurnedOn()
                        } else {
                            vmDriller.onCheckBoxTurnedOff()
                        }
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                vmDriller.eventFlow.collectLatest { event ->
                    when (event) {
                        is DrillerViewModel.DrillerEvent.ShowSnackbar -> {
                            Snackbar.make(binding.root, event.msg, Snackbar.LENGTH_LONG).show()
                        }
                        is DrillerViewModel.DrillerEvent.ScrollToCurrentPosition -> {
                            // пусто
                        }
                    }
                }
            }
        }
    }

    override fun onCheckedChanged(chip: CompoundButton, isChecked: Boolean) {
        if (isChecked) {
            vmDriller.onChipTurnedOn(chip.text.toString())
        } else {
            if (binding.chipGroupCategories.checkedChipIds.size < 1) {
                chip.isChecked = true
                vmDriller.showNotLessThanOneCategory(getString(R.string.cannot_turn_all_cats_off))
            } else {
                vmDriller.onChipTurnedOff(chip.text.toString())
            }
        }
    }

//============BELOW METHODS JUST FOR TESTING BOTTOMSHEETDIALOGFRGMENT LIFECYCLE====================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Log.d(TAG_PETR, "onCreate: CALLED")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        Log.d(TAG_PETR, "onCreateDialog: CALLED")
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
//        Log.d(TAG_PETR, "onStart: CALLED")
    }

    override fun onResume() {
        super.onResume()
//        Log.d(TAG_PETR, "onResume: CALLED")
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        vmDriller.acceptCatListChange()
    }

    override fun onDestroy() {
        super.onDestroy()
//        Log.d(TAG_PETR, "onDestroy: CALLED")
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        Log.d(TAG_PETR, "onDestroyView: CALLED")
    }
}





