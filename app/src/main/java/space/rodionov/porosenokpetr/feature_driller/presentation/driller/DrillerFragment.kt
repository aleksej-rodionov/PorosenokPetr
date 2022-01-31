package space.rodionov.porosenokpetr.feature_driller.presentation.driller

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.yuyakaido.android.cardstackview.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.databinding.FragmentDrillerBinding
import space.rodionov.porosenokpetr.Constants
import space.rodionov.porosenokpetr.Constants.TAG_PETR
import space.rodionov.porosenokpetr.feature_driller.presentation.base.BaseFragment
import java.lang.Exception
import java.util.*

@AndroidEntryPoint
class DrillerFragment : BaseFragment(R.layout.fragment_driller), CardStackListener {

    private val vmDriller: DrillerViewModel by viewModels()
    private var _binding: FragmentDrillerBinding? = null
    private val binding get() = _binding

    private val drillerAdapter: DrillerAdapter by lazy {
        DrillerAdapter(
            onSpeakWord = { word ->
                onSpeakWord(word)
            }
        )
    }

//    private val drillerLayoutManager: CardStackLayoutManager by lazy {
//        CardStackLayoutManager(requireContext(), this)
//    }

    private val textToSpeech: TextToSpeech by lazy {
        TextToSpeech(requireContext()) { status ->
            if (status == TextToSpeech.SUCCESS) {
                try {
                    textToSpeech.language = Locale.ENGLISH
                } catch (e: Exception) {
                    Log.d(TAG_PETR, "TTS: Exception: ${e.localizedMessage}")
                }
            } else {
                Log.d(TAG_PETR, "TTS Language initialization failed")
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDrillerBinding.bind(view)
        initViewModel()

        binding?.apply {
            cardStackView.apply {
                adapter = drillerAdapter
                layoutManager = createLayoutManager()
                setHasFixedSize(false)
                itemAnimator = null
            }

            btnNewRound.setOnClickListener {
                vmDriller.newRound()
                tvComplete.visibility = View.GONE
                btnNewRound.visibility = View.GONE
            }

            btnFilter.setOnClickListener {
                FilterBottomSheet().show(
                    childFragmentManager,
                    FilterBottomSheet.FILTER_BOTTOM_SHEET
                )
                // todo pass it through the viewModel and SharedFLow
            }

            btnCollection.setOnClickListener {
                vmDriller.navigateToCollectionScreen()
            }
        }

        vmDriller.newRound()
    }

    private fun initViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vmDriller.wordsState.collectLatest { wordsState ->
                binding?.apply {
                    progressBar.isVisible = wordsState.isLoading
                    ivCheck.isVisible = !wordsState.isLoading // а если ошибка то другой iv ?
                    tvItemCount.text = getString(R.string.item_count, wordsState.words.size)
                }

                val list = wordsState.words
                drillerAdapter.submitList(list)
//                vmDriller.scrollToCurPos()
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vmDriller.currentPosition.collectLatest { pos ->
                binding?.tvCurrentItem?.text = getString(R.string.current_position, pos)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vmDriller.eventFlow.collectLatest { event ->
                when (event) {
                    is DrillerViewModel.DrillerEvent.ShowSnackbar -> {
                        // пусто
                    }
                    is DrillerViewModel.DrillerEvent.ScrollToCurrentPosition -> {
                        binding?.cardStackView?.scrollToPosition(vmDriller.currentPosition.value)
                    }
                    is DrillerViewModel.DrillerEvent.NavigateToCollectionScreen -> {
                        val navAction = DrillerFragmentDirections.actionDrillerFragmentToCollectionFragment()
                        findNavController().navigate(navAction)
                    }
                }
            }
        }

    // other Observers
    }


    fun createLayoutManager(): CardStackLayoutManager {
        val drillerLayoutManager = CardStackLayoutManager(requireContext(), this)
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
        return drillerLayoutManager
    }

    private fun onSpeakWord(word: String) {
        textToSpeech.speak(word, TextToSpeech.QUEUE_FLUSH, null)
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
        binding?.tvOnCardAppeared?.text = getString(R.string.on_card_appeared, position)
        if (vmDriller.rememberPositionAfterChangingStack) {
            vmDriller.scrollToCurPos()
            vmDriller.updateCurrentPosition(vmDriller.currentPosition.value)
            vmDriller.rememberPositionAfterChangingStack = false
        } else {
            vmDriller.updateCurrentPosition(position)
        }
        if (position == drillerAdapter.itemCount - 3 && position < Constants.MAX_STACK_SIZE - 10) {
            vmDriller.addTenWords()
        }
        if (position == drillerAdapter.itemCount - 1) binding?.tvComplete?.visibility = View.VISIBLE
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        binding?.tvOnCardDisappeared?.text = getString(R.string.on_card_disappeared, position)
        if (position == drillerAdapter.itemCount - 1) binding?.btnNewRound?.visibility =
            View.VISIBLE
    }
}





