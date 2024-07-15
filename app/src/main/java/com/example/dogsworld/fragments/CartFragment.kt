package com.example.dogsworld.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dogsworld.R
import com.example.dogsworld.activities.HomeActivity
import com.example.dogsworld.adapters.CartAdapter
import com.example.dogsworld.data.Dog
import com.example.dogsworld.interfaces.ItemClickListener
import java.util.ArrayList

class CartFragment : Fragment(), ItemClickListener {

    private lateinit var mRvCart : RecyclerView
    private lateinit var mRvAdapter : CartAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRvCart = view.findViewById(R.id.rvCart)
        mRvCart.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        mRvAdapter = CartAdapter(getCartItems() , requireActivity(), this);
        mRvCart.adapter = mRvAdapter
        mRvCart.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))
    }

    override fun onResume() {
        super.onResume()
//        mRvAdapter.notifyDataSetChanged()
    }

    private fun getCartItems(): ArrayList<Dog> {
        return ((requireActivity()) as HomeActivity).dogList.filter { s -> s.cartCounter > 0 } as ArrayList<Dog>
    }

    override fun onItemAddedToCart(pos: Int, dog: Dog) {
        val index = ((requireActivity()) as HomeActivity).dogList.indexOfFirst{
            it.message == dog.message
        }
        var aDog = ((requireActivity()) as HomeActivity).dogList[index]
        aDog.cartCounter = aDog.cartCounter +1
        ((requireActivity()) as HomeActivity).dogList[index]= aDog
        mRvAdapter.notifyItemChanged(pos, aDog)
        (requireActivity() as HomeActivity).setListInStorage()
    }

    override fun onItemRemovedFromCart(pos: Int, dog: Dog) {
        val index = ((requireActivity()) as HomeActivity).dogList.indexOfFirst{
            it.message == dog.message
        }
        var aDog = ((requireActivity()) as HomeActivity).dogList[index]
        if(aDog.cartCounter > 1) {
            aDog.cartCounter = aDog.cartCounter - 1
            ((requireActivity()) as HomeActivity).dogList[index] = aDog
            mRvAdapter.notifyItemChanged(pos, aDog)
            (requireActivity() as HomeActivity).setListInStorage()
        }else if(aDog.cartCounter == 1) {
            aDog.cartCounter = 0

            ((requireActivity()) as HomeActivity).dogList[index] = aDog
            mRvAdapter.notifyItemRemoved(pos)
            mRvAdapter.updateList(pos)
            (requireActivity() as HomeActivity).setListInStorage()
        }else{
            Toast.makeText(requireActivity(), "Can't remove what you don't have!", Toast.LENGTH_SHORT).show()
        }
    }
}