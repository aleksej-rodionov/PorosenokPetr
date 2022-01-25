package space.rodionov.porosenokpetr.feature_driller.presentation.driller

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import space.rodionov.porosenokpetr.MainViewModel
import space.rodionov.porosenokpetr.R

@AndroidEntryPoint
class FilterBottomSheet: BottomSheetDialogFragment() {

    //==============TO BASE CLASS============================
    private val vmMain : MainViewModel by viewModels()
    override fun getTheme(): Int = vmMain.isNightMainViewModel.value?.let {
        if (it) R.style.Theme_NavBarNight
        else R.style.Theme_NavBarDay
    } ?: R.style.Theme_NavBarDay
    //===========================================================

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }
}





