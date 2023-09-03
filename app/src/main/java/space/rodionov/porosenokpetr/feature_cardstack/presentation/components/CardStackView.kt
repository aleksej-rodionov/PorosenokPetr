package space.rodionov.porosenokpetr.feature_cardstack.presentation.components

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import com.yuyakaido.android.cardstackview.*
import space.rodionov.porosenokpetr.core.util.Constants.WORD_LEARNED
import space.rodionov.porosenokpetr.databinding.LayoutCardstackBinding
import space.rodionov.porosenokpetr.feature_cardstack.presentation.CardstackState
import space.rodionov.porosenokpetr.feature_cardstack.presentation.TAG_CARDSTACK
import space.rodionov.porosenokpetr.feature_cardstack.presentation.adapter.CardStackAdapter
import space.rodionov.porosenokpetr.feature_cardstack.presentation.model.CardStackItem

class CardStackView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    attrStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, attrStyle), CardStackListener {

    private val binding = LayoutCardstackBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    private val cardstackAdapter = CardStackAdapter(
        onSpeakWordClick = { word ->
            onSpeakWordClick?.invoke(word)
        },
        onEditWordClick = { word ->
            onEditWordClick?.invoke(word)
        }
    )

    private var onWordAppeared: ((Int) -> Unit)? = null
    fun setOnWordAppearedListener(callback: (Int) -> Unit) {
        onWordAppeared = callback
    }

    private var onWordDesappeared:  ((Int) -> Unit)? = null
    fun setOnWordDisappearedListener (callback: (Int) -> Unit) {
        onWordDesappeared = callback
    }

    private var onWordSwiped: ((Int) -> Unit)? = null
    fun setOnWordSwipedListener(callback: (Int) -> Unit) {
        onWordSwiped = callback
    }

    private var onSpeakWordClick: ((String) -> Unit)? = null
    fun setOnSpeakWordListener(callback: (String) -> Unit) {
        onSpeakWordClick = callback
    }

    private var onEditWordClick: ((CardStackItem.WordUi) -> Unit)? = null
    fun setOnEditWordListener(callback: (CardStackItem.WordUi) -> Unit) {
        onEditWordClick = callback
    }

    private var onRefillBtnClick: (() -> Unit)? = null
    fun setOnRefillBtnClick(callback: () -> Unit) {
        onRefillBtnClick = callback
    }

    fun initView(
        state: CardstackState
    ) {
        Log.d(TAG_CARDSTACK, "initView: currentList = ${cardstackAdapter.currentList.size}")
        Log.d(TAG_CARDSTACK, "initView: state = ${state.words.size}")

        binding.apply {
            if (cardStack.adapter == null) {
                cardStack.adapter = cardstackAdapter
                cardStack.layoutManager = createCardStackLayoutManager(context, this@CardStackView)
            }

            if (cardstackAdapter.currentList.size != state.words.size) {
                cardstackAdapter.submitList(state.words)
                cardStack.scrollToPosition(state.currentPosition)
            }

            btnRefill.setOnClickListener {
                onRefillBtnClick?.invoke()
            }
        }
    }

    fun updateView(state: CardstackState) {
        cardstackAdapter.submitList(state.words)
        binding.apply {
            if (state.stackFinished) {
                ivThumb.visibility = View.VISIBLE
                btnRefill.visibility = View.VISIBLE
                cardStack.visibility = View.GONE
            } else {
                ivThumb.visibility = View.GONE
                btnRefill.visibility = View.GONE
                cardStack.visibility = View.VISIBLE
            }
        }
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
        Log.d(TAG_CARDSTACK, "onCardAppeared: $position")
        onWordAppeared?.invoke(position)
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        Log.d(TAG_CARDSTACK, "onCardDisappeared: $position")
        onWordDesappeared?.invoke(position)
    }
}

fun createCardStackLayoutManager(
    context: Context,
    listener: CardStackListener
): CardStackLayoutManager {
    val cardStackLayoutManager = CardStackLayoutManager(context, listener)
    cardStackLayoutManager.apply {
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
    return cardStackLayoutManager
}


