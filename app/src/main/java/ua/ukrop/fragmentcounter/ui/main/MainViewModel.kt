package ua.ukrop.fragmentcounter.ui.main

import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class MainViewModel(private val state: SavedStateHandle) : ViewModel() {
    companion object {
        const val KEY_MUTABLE_LIVEDATA = "KEY_MUTABLE_LIVEDATA"
        const val KEY_PAGES_LIST = "KEY_PAGES_LIST"
    }

    private var _pagesList: List<Int>
    private val _pagesLiveData: MutableLiveData<List<Int>>
    init {
        _pagesList= (state.get<IntArray>(KEY_PAGES_LIST) ?: intArrayOf(1)).toList()
        _pagesLiveData= state.getLiveData(KEY_MUTABLE_LIVEDATA, _pagesList)//MutableLiveData<List<Int>>(_pagesList)

        Log.d("TAG3", state.get(KEY_PAGES_LIST) ?: "null")
        Log.d("TAG3", state.getLiveData<List<Int>>(KEY_MUTABLE_LIVEDATA).value.toString())
    }

//        set(value) {
//            field = value
//            state.set(KEY_PAGES_LIST, value)
//        }
//        get() {
//            return state.get<List<Int>>(KEY_PAGES_LIST) ?: listOf<Int>(1)
//        }
//            init {
//        state.setSavedStateProvider("KEY_PROVIDER") {
//            bundleOf(
//                Pair(KEY_MUTABLE_LIVEDATA, _pagesLiveData),
//                Pair(KEY_PAGES_LIST, _pagesList)
//            )
//        }
//            }

//    fun getName(): LiveData<String?>? {
//        return mState.getLiveData(NAME_KEY)
//    }
//
//    fun saveNewName(newName: String?) {
//        mState.set(NAME_KEY, newName)
//    }


    val pagesLiveData: LiveData<List<Int>> = _pagesLiveData

    fun addNewItem() {
        _pagesList = _pagesList.plus(_pagesList.size + 1)
        _pagesLiveData.postValue(_pagesList)
        saveState()
    }

    fun removeLastItem() {
        _pagesList = _pagesList.dropLast(1)
        _pagesLiveData.postValue(_pagesList)
        saveState()
    }

    private fun saveState() {
        state.set(KEY_PAGES_LIST, _pagesList.toIntArray()).also { Log.d("TAG3", _pagesList.toString()) }
        state.set(KEY_MUTABLE_LIVEDATA, _pagesList)
    }
}
