package space.rodionov.porosenokpetr.feature_cardstack.presentation.componensts

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.toDrawable
import com.yuyakaido.android.cardstackview.*
import space.rodionov.porosenokpetr.databinding.LayoutCardstackBinding
import space.rodionov.porosenokpetr.feature_cardstack.presentation.CardstackState

class CardStackView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    attrStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, attrStyle), CardStackListener {

    private val binding = LayoutCardstackBinding.inflate(LayoutInflater.from(context), this, true)

    lateinit var cardstackState: CardstackState

    private val cardstackAdapter = CardStackAdapter(
        onSpeakWord = { word ->
//            vmDriller.speakWord(word)
        }
    )

    fun initView(
        state: CardstackState
    ) {
        this.cardstackState = state

        binding.root.background = resources.getColor(androidx.appcompat.R.color.material_grey_100).toDrawable()
        binding.cardStack.adapter = cardstackAdapter
        binding.cardStack.layoutManager = createCardStackLayoutManager(context, this)
        cardstackAdapter.submitList(state.words)
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
        // empty
    }

    override fun onCardSwiped(direction: Direction?) {
//        if (direction == Direction.Bottom) {
//            vmDriller.inactivateCurrentWord()
//        }
    }

    override fun onCardRewound() {
        // empty
    }

    override fun onCardCanceled() {
        // empty
    }

    override fun onCardAppeared(view: View?, position: Int) {
//        binding?.tvOnCardAppeared?.text = getString(com.yuyakaido.android.cardstackview.R.string.on_card_appeared, position)
//        vmDriller.onCardAppeared(position)
//        if (position == drillerAdapter.itemCount - 3 && position < Constants.MAX_STACK_SIZE - 10) {
//            vmDriller.addTenWords()
//        }
//        if (position == drillerAdapter.itemCount - 1) binding?.tvComplete?.visibility = View.VISIBLE
    }

    override fun onCardDisappeared(view: View?, position: Int) {
//        binding.tvOnCardDisappeared?.text = getString(com.yuyakaido.android.cardstackview.R.string.on_card_disappeared, position)
//        if (position == drillerAdapter.itemCount - 1) {
//            binding.btnNewRound.visibility = View.VISIBLE
//        } else {
//            binding.btnNewRound.visibility = View.GONE // todo bug сделать нормально чтоб было чтоб кнопка исчезала вовремя или вообще не появлялась
//        }
    }
}

fun createCardStackLayoutManager(context: Context, listener: CardStackListener): CardStackLayoutManager {
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


