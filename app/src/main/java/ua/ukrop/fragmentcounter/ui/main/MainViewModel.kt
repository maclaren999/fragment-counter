package ua.ukrop.fragmentcounter.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _fragmentCounter: MutableLiveData<List<Int>> =
        MutableLiveData<List<Int>>(listOf(1))

    val fragmentCounter: LiveData<List<Int>> = _fragmentCounter

    fun addNewItem() {
        val size = _fragmentCounter.value!!.size
        _fragmentCounter.postValue(_fragmentCounter.value!!.plus(size + 1))

    }

    fun removeLastItem() {
        _fragmentCounter.postValue(_fragmentCounter.value!!.dropLast(1))
    }

    fun size(): Int = fragmentCounter.value!!.size
}
