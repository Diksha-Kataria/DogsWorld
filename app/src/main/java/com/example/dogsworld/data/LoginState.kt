package com.example.dogsworld.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DogViewModel : ViewModel(){
    private var _data = MutableLiveData<Dog>().apply {
        value = Dog()
    }
    val data: LiveData<Dog> = _data

    fun increase(){
        _data.value = _data.value.apply {
             this?.cartCounter  = (this?.cartCounter!! + 1)
        }
    }
}
