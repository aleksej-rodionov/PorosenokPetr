package space.rodionov.porosenokpetr.feature_cardstack.presentation.componensts

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.toDrawable
import com.yuyakaido.android.cardstackview.*
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.core.util.Constants.MODE_DARK
import space.rodionov.porosenokpetr.core.util.Constants.WORD_LEARNED
import space.rodionov.porosenokpetr.databinding.LayoutCardstackBinding
import space.rodionov.porosenokpetr.feature_cardstack.presentation.CardstackState
import space.rodionov.porosenokpetr.feature_cardstack.presentation.TAG_CARDSTACK
import space.rodionov.porosenokpetr.feature_cardstack.presentation.model.CardStackItem

class CardStackView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    attrStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, attrStyle), CardStackListener {

    private val binding = LayoutCardstackBinding.inflate(LayoutInflater.from(context), this, true)

//    lateinit var cardstackState: CardstackState

    private val cardstackAdapter = CardStackAdapter(
        onSpeakWord = { word ->
            onSpeakWord?.invoke(word)
        }
    )

    private var onWordAppeared: ((Int) -> Unit)? = null
    fun setOnWordAppearedListener(callback: (Int) -> Unit) {
        onWordAppeared = callback
    }

    private var onWordSwiped: ((Int) -> Unit)? = null
    fun setOnWordSwipedListener(callback: (Int) -> Unit) {
        onWordSwiped = callback
    }

    private var onSpeakWord: ((String) -> Unit)? = null
    fun setOnSpeakWordListener(callback: (String) -> Unit) {
        onSpeakWord = callback
    }

    fun initView(
        state: CardstackState
    ) {
        Log.d(TAG_CARDSTACK, "initView: currentList = ${cardstackAdapter.currentList.size}")
        Log.d(TAG_CARDSTACK, "initView: state = ${state.words.size}")
//        this.cardstackState = state

        binding.apply {
            if (cardStack.adapter == null) {
                cardStack.adapter = cardstackAdapter
                cardStack.layoutManager = createCardStackLayoutManager(context, this@CardStackView)
            }

            if (cardstackAdapter.currentList.size != state.words.size) {
                cardstackAdapter.submitList(state.words)
                cardStack.scrollToPosition(state.currentPosition)
            }

//            cardStack.background = if (state.mode == MODE_DARK) {
//                resources.getColor(R.color.gray900).toDrawable()
//            } else {
//                resources.getColor(R.color.gray200).toDrawable()
//            }
        }
    }

    fun submitList(items: List<CardStackItem.WordUi>) {
        cardstackAdapter.submitList(items)
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {}

    override fun onCardSwiped(direction: Direction?) {
        Log.d(TAG_CARDSTACK, "onCardSwiped")
        if (direction == Direction.Bottom) {
            onWordSwiped?.invoke(WORD_LEARNED)
        }
    }

    override fun onCardRewound() {}

    override fun onCardCanceled() {}

    override fun onCardAppeared(view: View?, position: Int) {
        Log.d(TAG_CARDSTACK, "onCardAppeared: ${cardstackAdapter.currentList[position].swe}")
        onWordAppeared?.invoke(position)
//        if (position == cardstackAdapter.itemCount - 3 && position < Constants.MAX_STACK_SIZE - 10) {
//            vmDriller.addTenWords()
//        }
//        if (position == drillerAdapter.itemCount - 1) binding?.tvComplete?.visibility = View.VISIBLE
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        Log.d(TAG_CARDSTACK, "onCardDisappeared: ${cardstackAdapter.currentList[position].swe}")
//        if (position == drillerAdapter.itemCount - 1) {
//            binding.btnNewRound.visibility = View.VISIBLE
//        } else {
//            binding.btnNewRound.visibility = View.GONE // todo bug сделать нормально чтоб было чтоб кнопка исчезала вовремя или вообще не появлялась
//        }
    }
}

fun createCardStackLayoutManager(
    context: Context,
    listener: CardStackListener
): CardStackLayoutManager {
    val drillerLayoutManager = CardStackLayoutManager(context, listener) // todo pass this not null
    drillerLayoutManager.apply {
        setOverlayInterpolator(LinearInterpolator())
        setStackFrom(StackFrom.Top)
        setVisibleCount(3)
        setTranslationInterval(8.0f)
        setScaleInterval(0.95f)
        setMaxDegree(20.0f)
        setDirections(com.yuyakaido.android.cardstackview.Direction.FREEDOM)
        setSwipeThreshold(0.3f)
        setCanScrollHorizontal(true)
        setCanScrollVertical(true)
        setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
    }
    return drillerLayoutManager
}


