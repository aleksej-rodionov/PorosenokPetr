package space.rodionov.porosenokpetr.feature_driller.presentation.base

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import space.rodionov.porosenokpetr.MainViewModel
import space.rodionov.porosenokpetr.R
import javax.inject.Inject

    abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {


        val vmMain : MainViewModel by viewModels()

        override fun getTheme(): Int = vmMain.isNightMainViewModel.value?.let {
            if (it) R.style.Theme_NavBarNight
            else R.style.Theme_NavBarDay
        } ?: R.style.Theme_NavBarDay

        fun initModeObserver(rootView: View) {
            vmMain.isNightMainViewModel.observe(this) {
                rootView?.let { view ->
                    view as ViewGroup
                    view.redrawViewGroup(it, true)
                    view.redrawAllRecyclerAdapters(it)
                }
            }
        }
    }
