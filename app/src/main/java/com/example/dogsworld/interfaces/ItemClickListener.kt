package com.example.dogsworld.interfaces

import com.example.dogsworld.data.Dog

interface ItemClickListener {
    fun onItemAddedToCart(pos: Int, dog: Dog)
    fun onItemRemovedFromCart(pos: Int, dog: Dog)
}