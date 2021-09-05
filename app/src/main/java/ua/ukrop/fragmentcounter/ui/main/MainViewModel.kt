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
//        _fragmentCounter.value!!.add(size + 1)
        _fragmentCounter.postValue(_fragmentCounter.value!!.plus(size + 1))
//        val current = _fragmentCounter.postValue(_fragmentCounter.value?.plus(1))
//        _fragmentCounter.value = current?.plus(1)
    }

    fun removeLastItem() {
        _fragmentCounter.postValue(_fragmentCounter.value!!.dropLast(1))
//        val current = _fragmentCounter.value
//        if (current != null && current > 1) _fragmentCounter.postValue(current.minus(1))
    }

    fun size(): Int = fragmentCounter.value!!.size
}
