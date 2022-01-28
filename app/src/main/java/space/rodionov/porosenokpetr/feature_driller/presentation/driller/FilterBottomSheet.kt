package space.rodionov.porosenokpetr.feature_driller.presentation.driller

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import space.rodionov.porosenokpetr.MainViewModel
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.databinding.BottomsheetFilterBinding
import space.rodionov.porosenokpetr.feature_driller.Constants.TAG_PETR
import space.rodionov.porosenokpetr.feature_driller.presentation.base.BaseBottomSheetDialogFragment

@AndroidEntryPoint
class FilterBottomSheet: BaseBottomSheetDialogFragment() {

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
        Log.d(TAG_PETR, "onCreateView: CALLED")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG_PETR, "onViewCreated: CALLED")
        initModeObserver(binding.root, viewLifecycleOwner.lifecycleScope)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vmDriller.categories.collectLatest {
                // todo здесь заполнять ЧипГруп
            }
        }
    }





//============BELOW METHODS JUST FOR TESTING BOTTOMSHEETDIALOGFRGMENT LIFECYCLE====================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG_PETR, "onCreate: CALLED")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.d(TAG_PETR, "onCreateDialog: CALLED")
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG_PETR, "onStart: CALLED")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG_PETR, "onResume: CALLED")
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        Log.d(TAG_PETR, "onDismiss: CALLED")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG_PETR, "onDestroy: CALLED")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG_PETR, "onDestroyView: CALLED")

    }
}





