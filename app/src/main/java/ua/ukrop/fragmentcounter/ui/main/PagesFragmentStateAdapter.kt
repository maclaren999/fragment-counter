package ua.ukrop.fragmentcounter.ui.main

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder

const val TAG2 = "TAG_callbacks"

class PagesFragmentStateAdapter(activity: FragmentActivity) :
    FragmentStateAdapter(activity) {

    private var localPagesList: List<Int> = mutableListOf()
    fun updateList(newlist: List<Int>) {
        localPagesList = newlist
    }

    override fun createFragment(position: Int): MainFragment {
        Log.d(TAG2, "createFragment(position = $position)")
        return MainFragment.newInstance(localPagesList[position])
    }

    override fun getItemCount(): Int = localPagesList.size
        .also { Log.d(TAG2, "getItemCount() = $it") }

    override fun getItemId(position: Int): Long =
        position + 1L.also { Log.d(TAG2, "getItemId() = $it") }

    override fun containsItem(itemId: Long): Boolean =
        localPagesList.contains(itemId.toInt())
            .also { Log.d(TAG2, "containsItem() = $it") }
}
