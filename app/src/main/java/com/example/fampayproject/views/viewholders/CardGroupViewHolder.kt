package com.example.fampayproject.views.viewholders


import androidx.recyclerview.widget.RecyclerView
import com.example.fampayproject.databinding.LayoutCardGroupBinding



/** A ViewHolder that exposes the recyclerView associated to it and helps completing the Adapter Pattern
 * (for displaying views in recyclerViews using adapters & ViewHolders)
 * */
class CardGroupViewHolder(cardGroupBinding: LayoutCardGroupBinding): RecyclerView.ViewHolder(cardGroupBinding.root) {
    val cardGroupRecyclerView = cardGroupBinding.rvCardGroup
}
