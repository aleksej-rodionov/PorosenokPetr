package space.rodionov.porosenokpetr.feature_driller.presentation.base

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.MainViewModel
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.feature_driller.Constants.TAG_PETR
import javax.inject.Inject

    abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {


        val vmMain : MainViewModel by viewModels()

        override fun getTheme(): Int = vmMain.isNightMainViewModel.value?.let {
            if (it) R.style.Theme_NavBarNight
            else R.style.Theme_NavBarDay
        } ?: R.style.Theme_NavBarDay

        fun initModeObserver(rootView: View, scope: CoroutineScope) {
            scope.launch {
                vmMain.isNightMainViewModel.collectLatest {
                    Log.d(TAG_PETR, "initModeObserver: isNight = $it")
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
