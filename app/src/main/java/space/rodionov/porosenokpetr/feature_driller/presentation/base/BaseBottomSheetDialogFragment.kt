package space.rodionov.porosenokpetr.feature_driller.presentation.base

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.Constants.MODE_DARK
import space.rodionov.porosenokpetr.Constants.MODE_LIGHT
import space.rodionov.porosenokpetr.MainViewModel
import space.rodionov.porosenokpetr.R

abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {


        val vmMain : MainViewModel by viewModels()

        override fun getTheme(): Int = vmMain.mode.value?.let {
            when (it) {
                MODE_LIGHT -> R.style.Theme_NavBarDay
                MODE_DARK -> R.style.Theme_NavBarNight
                else -> R.style.Theme_NavBarDay
            }
        }

        fun initModeObserver(rootView: View, scope: CoroutineScope) {
            scope.launch {
                vmMain.mode.collectLatest {
//                    Log.d(TAG_PETR, "initModeObserver: isNight = $it")
                    rootView.let { view ->
                        view as ViewGroup
                        // todo create redrawing methods below:
                //                        view.redrawViewGroup(it, true)
                //                        view.redrawAllRecyclerAdapters(it)
                    }
                }
            }
        }
    }
