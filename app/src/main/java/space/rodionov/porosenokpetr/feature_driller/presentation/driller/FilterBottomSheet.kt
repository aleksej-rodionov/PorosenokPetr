package space.rodionov.porosenokpetr.feature_driller.presentation.driller

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import space.rodionov.porosenokpetr.MainViewModel
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.databinding.BottomsheetFilterBinding
import space.rodionov.porosenokpetr.feature_driller.presentation.base.BaseBottomSheetDialogFragment

@AndroidEntryPoint
class FilterBottomSheet: BaseBottomSheetDialogFragment() {

    private val binding: BottomsheetFilterBinding by lazy {
        BottomsheetFilterBinding.inflate(layoutInflater)
    }
    private val behavior: BottomSheetBehavior<View> by lazy {
        BottomSheetBehavior.from(binding.root.parent as View)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog


//        bottomSheet.setContentView(binding.root)
//        (binding.root.parent as View).setBackgroundResource(android.R.color.transparent) // todo проверить че это такое ваще

//        initModeObserver(binding.root, viewLifecycleOwner.lifecycleScope)

        return bottomSheet
    }

    override fun onStart() {
        super.onStart()
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.skipCollapsed = true
    }
}





