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


class CartAdapter(private val itemList: ArrayList<Dog>, private val context: FragmentActivity, private val listener: ItemClickListener) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.initializeHolder(position)
    }

    public fun updateList(position: Int){
        val newItems = ArrayList(itemList)
        newItems.removeAt(position)
        itemList.clear()
        itemList.addAll(newItems)
        notifyItemRemoved(position)
    }

    inner class CartViewHolder(itemView : View) : ViewHolder(itemView ) {
        var mIvItem : ImageView
        var mIbIncrement : ImageButton
        var mIbDecrement : ImageButton
        var mTvTotalCount : TextView

        init {
            mIvItem = itemView.findViewById(R.id.ivCartItem);
            mIbIncrement = itemView.findViewById(R.id.incrementCart);
            mIbDecrement= itemView.findViewById(R.id.decrementCart);
            mTvTotalCount= itemView.findViewById(R.id.cartValue);
        }

        fun initializeHolder( position: Int) {
            Glide.with(context)
                .load(itemList[position].message)
                .into(mIvItem)
            mIbDecrement.setOnClickListener{
                listener.onItemRemovedFromCart(position, itemList[position])
            }
            mIbIncrement.setOnClickListener{

//                notifyItemRemoved(position)
//                val itemChangedCount = itemList.size - position
//                notifyItemRangeChanged(position, itemChangedCount)
                listener.onItemAddedToCart(position, itemList[position])
            }
            mTvTotalCount.text = itemList[position].cartCounter.toString()
        }
    }
}