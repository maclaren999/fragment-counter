package ua.ukrop.fragmentcounter.ui.main

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder

const val TAG2 = "TAG_callbacks"

class CounterFragmentStateAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    companion object{
        fun getInstance(){

        }
    }
    private var _pagesList = listOf<Int>()

    fun updatePagesList(newList: List<Int>) {
        _pagesList
    }

    override fun createFragment(position: Int): MainFragment {
        Log.d(TAG2, "createFragment(position = $position)")
        return MainFragment.newInstance(itemsViewModel.pagesLiveData.value?.get(position))
    }

    override fun onBindViewHolder(
        holder: FragmentViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
        Log.d(TAG2, "onBindViewHolder(position = $position)")
    }

    override fun getItemCount(): Int =
        itemsViewModel.pagesLiveData.value?.size
            .also {
                Log.d(TAG2, "getItemCount() = $it")
            } ?: 3

    override fun getItemId(position: Int): Long =
        position + 1L.also { Log.d(TAG2, "getItemId() = $it") }

    override fun containsItem(itemId: Long): Boolean =
        itemsViewModel.pagesLiveData.value!!.contains(itemId.toInt())
            .also { Log.d(TAG2, "containsItem() = $it") }
}
