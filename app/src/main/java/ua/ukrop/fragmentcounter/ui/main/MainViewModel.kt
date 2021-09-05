package ua.ukrop.fragmentcounter.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private var _pagesList = listOf<Int>(1)
    private val _pagesLiveData: MutableLiveData<List<Int>> = MutableLiveData<List<Int>>(_pagesList)
    val pagesLiveData: LiveData<List<Int>> = _pagesLiveData

    fun addNewItem() {
        _pagesList = _pagesList.plus(_pagesList.size + 1)
        _pagesLiveData.postValue(_pagesList)
    }

    fun removeLastItem() {
        _pagesList = _pagesList.dropLast(1)
        _pagesLiveData.postValue(_pagesList)
    }

}
