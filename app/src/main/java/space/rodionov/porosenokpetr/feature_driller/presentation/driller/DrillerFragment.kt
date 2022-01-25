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
import space.rodionov.porosenokpetr.feature_driller.Constants
import space.rodionov.porosenokpetr.feature_driller.Constants.TAG_PETR

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
        Log.d(TAG_PETR, "onViewCreated: CALLED")
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

            btnNewRound.setOnClickListener {
                vmDriller.newRound()
                tvComplete.visibility = View.GONE
                btnNewRound.visibility = View.GONE
            }
        }

        vmDriller.newRound()
    }

    private fun initViewModel() {
        Log.d(TAG_PETR, "initViewModel: CALLED")
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vmDriller.wordsState.collectLatest { wordsState ->
                binding?.apply {
                    progressBar.isVisible = wordsState.isLoading
                    ivCheck.isVisible = !wordsState.isLoading // а если ошибка то другой iv ?
                    tvItemCount.text = getString(R.string.item_count, wordsState.words.size)
                }

                val list = wordsState.words
                drillerAdapter.submitList(list)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vmDriller.currentPosition.collectLatest { pos ->
                Log.d(TAG_PETR, "initViewModel: currentPos = $pos")
                binding?.tvCurrentItem?.text = getString(R.string.current_position, pos)
            }
        }

        // other Observers
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
        // empty
    }

    override fun onCardSwiped(direction: Direction?) {
        if (direction == Direction.Bottom) {
            vmDriller.inactivateCurrentWord()
        }
    }

    override fun onCardRewound() {
        // empty
    }

    override fun onCardCanceled() {
        // empty
    }

    override fun onCardAppeared(view: View?, position: Int) {
//        binding?.tvOnCardAppeared?.text = getString(R.string.on_card_appeared, position)
        vmDriller.updateCurrentPosition(position)
        if (position == drillerAdapter.itemCount - 3 && position < Constants.MAX_STACK_SIZE - 10) {
             vmDriller.addTenWords()
        }
        if (position == drillerAdapter.itemCount - 1) binding?.tvComplete?.visibility = View.VISIBLE
    }

    override fun onCardDisappeared(view: View?, position: Int) {
//        binding?.tvOnCardDisappeared?.text = getString(R.string.on_card_disappeared, position)
        if (position == drillerAdapter.itemCount - 1) binding?.btnNewRound?.visibility = View.VISIBLE
    }
}





