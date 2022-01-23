package space.rodionov.porosenokpetr.feature_driller.presentation.base

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.collectLatest
import space.rodionov.porosenokpetr.MainViewModel
import space.rodionov.porosenokpetr.feature_driller.presentation.base.adapter.BaseAdapter
import space.rodionov.porosenokpetr.feature_driller.presentation.base.adapter.BasePorosenokPetrAdapter

abstract class BaseFragment: Fragment() {

    var isNightBaseMainFragment = false
    open val binding: ViewBinding? = null
    private val vmMain: MainViewModel by viewModels()

    open val recyclerAdapter: BaseAdapter by lazy {
        BasePorosenokPetrAdapter(
            changeMode = {
                updateMode(it)
            }
        )
    }

    open fun updateMode(isNight: Boolean) {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vmMain.isNightMainViewModel.collectLatest {
                binding?.apply {
//                    (root as ViewGroup).redrawViewGroup(it)
                }
//                recyclerAdapter.updateMode(it)

//                if (!vmMain.fullScreenVideo && ! vmMain.clipVideo) setDefaultBarsColors(it)
//                if (vmMain.fullScreenVideo) setWhiteBothBars()
//                if (vmMain.clipVideo) setBlackBgAndWhiteText()
            }
        }
    }

    //===================================NIGHT MODE=====================================
    fun setDefaultBarsColors(isNight: Boolean) {
        when (isNight) {
            true -> {
                activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

            }
            else -> {
                activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            }
        }
    }

    fun setBlackBgAndWhiteText() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        }
    }

    fun setWhiteBothBars() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        }
    }
}