package com.example.dogsworld.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.dogsworld.R
import com.example.dogsworld.data.Dog
import com.example.dogsworld.interfaces.ItemClickListener


class HomeAdapter(private val itemList: ArrayList<Dog>, private val context: FragmentActivity, private val listener: ItemClickListener) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_home, parent, false)
        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.initializeHolder(position)
    }

    inner class HomeViewHolder(itemView : View) : ViewHolder(itemView ) {
        var mIvItem : ImageView
        var mIbIncrement : ImageButton
        var mIbDecrement : ImageButton
        var mTvTotalCount : TextView

        init {
            mIvItem = itemView.findViewById(R.id.ivItem);
            mIbIncrement = itemView.findViewById(R.id.addToCart);
            mIbDecrement= itemView.findViewById(R.id.removeFromCart);
            mTvTotalCount= itemView.findViewById(R.id.totalCount);
        }

        fun initializeHolder( position: Int) {
            Glide.with(context)
                .load(itemList[position].message)
                .into(mIvItem)
            mIbDecrement.setOnClickListener{
                listener.onItemRemovedFromCart(position, itemList[position])
            }
            mIbIncrement.setOnClickListener{
                listener.onItemAddedToCart(position, itemList[position])
            }
            mTvTotalCount.text = itemList[position].cartCounter.toString()
        }
    }
}