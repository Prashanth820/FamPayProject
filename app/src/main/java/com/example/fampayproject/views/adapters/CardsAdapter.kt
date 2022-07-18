package com.example.fampayproject.views.adapters


import com.example.fampayproject.models.Card
import com.example.fampayproject.models.CardGroup.DesignType
import com.example.fampayproject.models.CardGroup.DesignType.SMALL_CARD_WITH_ARROW
import com.example.fampayproject.models.CardGroup.DesignType.SMALL_DISPLAY_CARD
import com.example.fampayproject.models.CardGroup.DesignType.IMAGE_CARD
import com.example.fampayproject.models.CardGroup.DesignType.DYNAMIC_WIDTH_CARD
import com.example.fampayproject.models.CardGroup.DesignType.BIG_DISPLAY_CARD
import com.example.fampayproject.utils.SharedPreferenceUtils
import com.example.fampayproject.views.viewholders.CardViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.fampayproject.databinding.*


/**
 * An [Adapter] that inflates & binds cardLayouts
 * It also takes care of itemView type and displaying the menu accordingly
 * It takes
 * @param [designType] in order to parse what type of card is obtained from server response and bind/inflate layout accordingly
 * @param [groupId] in order to take note of the preference selected by user on HC3 card for displaying in the next run or never
 */
class CardsAdapter(private val designType: DesignType, private val groupId: Long) :
    RecyclerView.Adapter<CardViewHolder>() {
    private val TAG = "CardsAdapter"
    private val cardData = ArrayList<Card>()

    /** Sequenced on the basis of their HC values, increasing order, top to bottom */
    private lateinit var layoutSmallCardBinding: LayoutSmallCardBinding       // HC1
    private lateinit var layoutBigCardBinding: LayoutBigCardBinding           // HC3
    private lateinit var layoutImageCardBinding: LayoutImageCardBinding       // HC5
    private lateinit var layoutSmallCardWithArrowBinding: LayoutSmallCardWithArrowBinding // HC6
    private lateinit var layoutDynamicWidthCardBinding: LayoutDynamicWidthCardBinding // HC9

    private lateinit var layoutBigCardMenuBinding: LayoutBigCardMenuBinding

    /** Handling menu display for BigCard */
    private val SHOW_MENU = 1
    private val HIDE_MENU = 2

    /**
     * Initialize binding and inflate the layout depending upon the [DesignType] of the [CardGroup]
     * observed, one by one. without wasting resources
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        lateinit var binding: ViewBinding

        binding = when (designType) {
            SMALL_DISPLAY_CARD -> {
                layoutSmallCardBinding = LayoutSmallCardBinding.inflate(inflater, parent, false)
                layoutSmallCardBinding
            }

            BIG_DISPLAY_CARD -> {
                if (viewType == SHOW_MENU) {
                    layoutBigCardMenuBinding =
                        LayoutBigCardMenuBinding.inflate(inflater, parent, false)
                    layoutBigCardMenuBinding
                } else {
                    layoutBigCardBinding = LayoutBigCardBinding.inflate(inflater, parent, false)
                    layoutBigCardBinding
                }
            }

            IMAGE_CARD -> {
                layoutImageCardBinding = LayoutImageCardBinding.inflate(inflater, parent, false)
                layoutImageCardBinding
            }
            SMALL_CARD_WITH_ARROW -> {
                layoutSmallCardWithArrowBinding =
                    LayoutSmallCardWithArrowBinding.inflate(inflater, parent, false)
                layoutSmallCardWithArrowBinding
            }

            DYNAMIC_WIDTH_CARD -> {
                layoutDynamicWidthCardBinding = LayoutDynamicWidthCardBinding.inflate(inflater, parent, false)
                layoutDynamicWidthCardBinding
            }
        }

        return CardViewHolder(binding)
    }

    /**
     * Bind those inflated layouts on the same ideology mentioned above
     */
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val cardItem = cardData[position]
        with(cardItem) item@{
            with(holder) {
                when (designType) {
                    SMALL_DISPLAY_CARD -> SmallCardViewHolder(layoutSmallCardBinding).bindSmallCard(
                        this@item
                    )

                    BIG_DISPLAY_CARD -> {
                        if (!cardItem.swipeMenu) {
                            BigCardViewHolder(layoutBigCardBinding).bindBigCard(this@item)
                            itemView.setOnLongClickListener {
                                showMenu(position)
                                true
                            }
                        } else {
                            with(layoutBigCardMenuBinding) {
                                menuRemindIcon.setOnClickListener { deleteCard(position + 1) }
                                menuDismissIcon.setOnClickListener { deleteCard(position + 1) }
                            }
                        }
                    }

                    IMAGE_CARD -> ImageCardViewHolder(layoutImageCardBinding).bindImageCard(this@item)

                    SMALL_CARD_WITH_ARROW -> SmallCardWithArrowViewHolder(
                        layoutSmallCardWithArrowBinding
                    ).bindSmallCardWithArrow(this@item)

                    DYNAMIC_WIDTH_CARD -> DynamicWidthCardViewHolder(layoutDynamicWidthCardBinding).bindDynamicWidthCard(this@item)

                }
            }
        }
    }

    override fun getItemCount(): Int {
        return cardData.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (cardData[position].swipeMenu) SHOW_MENU else HIDE_MENU
    }

    fun setCardData(cardList: List<Card>) {
        cardData.clear()
        cardData.addAll(cardList)
        notifyDataSetChanged()
    }

    private fun showMenu(position: Int) {
        if (cardData.isNotEmpty() && !cardData[0].swipeMenu) {
            val menuCard = Card("menu_card")
            menuCard.swipeMenu = true
            cardData.add(position, menuCard)
            notifyDataSetChanged()
        }
    }

    fun hideMenu() {
        if (cardData.isNotEmpty() && cardData[0].swipeMenu) {
            cardData.removeAt(0)
            notifyDataSetChanged()
        }
    }

    private fun deleteCard(position: Int) {
        if (cardData.size > position) {
            cardData.removeAt(position)
            notifyDataSetChanged()
            SharedPreferenceUtils.addGroupId(groupId.toString())
        }
        hideMenu()
    }
}
