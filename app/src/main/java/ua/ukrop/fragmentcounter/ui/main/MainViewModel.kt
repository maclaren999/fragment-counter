package ua.ukrop.fragmentcounter.ui.main

import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class MainViewModel(private val state: SavedStateHandle) : ViewModel() {

    private var _pagesList: List<Int>
        get() {
            return (state.get<IntArray>(KEY_PAGES_LIST) ?: intArrayOf(1)).toList()
        }
        set(value) {
            state[KEY_PAGES_LIST] = value.toIntArray()
        }

    private val _pagesLiveData: MutableLiveData<List<Int>> = MutableLiveData()
    val pagesLiveData: LiveData<List<Int>> = _pagesLiveData

    init {
        populate()
        Log.d("TAG4", "_pagesList = $_pagesList")
    }

    fun addNewItem() {
        _pagesList = _pagesList.plus(_pagesList.size + 1)
        populate()
    }

    fun removeLastItem() {
        _pagesList = _pagesList.dropLast(1)
        populate()
    }

    private fun populate() {
        _pagesLiveData.postValue(_pagesList)
    }

    fun setList(size: Int){
        _pagesList = (1 until size+1).toList()
    }

    private fun saveState() {
    }

    companion object {
        const val KEY_PAGES_LIST = "KEY_PAGES_LIST"
    }
}
