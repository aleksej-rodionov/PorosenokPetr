package space.rodionov.porosenokpetr.feature_driller.presentation.driller

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.yuyakaido.android.cardstackview.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.databinding.FragmentDrillerBinding

private const val TAG = "DrillerFragment"
@AndroidEntryPoint
class DrillerFragment : Fragment(R.layout.fragment_driller), CardStackListener {

    private val vmDriller: DrillerViewModel by viewModels()
    private var _binding: FragmentDrillerBinding? = null
    private val binding get() = _binding

    private val drillerAdapter: DrillerAdapter by lazy {
        DrillerAdapter()
    }

    private val drillerLayoutManager: CardStackLayoutManager by lazy {
        CardStackLayoutManager(requireContext(), this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDrillerBinding.bind(view)
        Log.d(TAG, "onViewCreated: CALLED")
        initViewModel()

        drillerLayoutManager.apply {
            setOverlayInterpolator(LinearInterpolator())
            setStackFrom(StackFrom.Top)
            setVisibleCount(3)
            setTranslationInterval(8.0f)
            setScaleInterval(0.95f)
            setMaxDegree(20.0f)
            setDirections(Direction.FREEDOM)
            setSwipeThreshold(0.3f)
            setCanScrollHorizontal(true)
            setCanScrollVertical(true)
            setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        }

        binding?.apply {
            cardStackView.apply {
                adapter = drillerAdapter
                layoutManager = drillerLayoutManager
                setHasFixedSize(false)
                itemAnimator = null
            }
        }

        vmDriller.loadTenWords()
    }

    private fun initViewModel() {
        Log.d(TAG, "initViewModel: CALLED")
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vmDriller.wordsState.collectLatest { wordsState ->
                binding?.apply {
                    progressBar.isVisible = wordsState.isLoading
                    ivCheck.isVisible = !wordsState.isLoading // а если ошибка то другой iv ?
                    tvItemCount.text = wordsState.words.size.toString()
                }

                val list = wordsState.words
                drillerAdapter.repl(wordsState)
            }
        }

        // other Observers
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
        TODO("Not yet implemented")
    }

    override fun onCardSwiped(direction: Direction?) {
        TODO("Not yet implemented")
    }

    override fun onCardRewound() {
        TODO("Not yet implemented")
    }

    override fun onCardCanceled() {
        TODO("Not yet implemented")
    }

    override fun onCardAppeared(view: View?, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        TODO("Not yet implemented")
    }
}





