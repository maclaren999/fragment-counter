package ua.ukrop.fragmentcounter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import ua.ukrop.fragmentcounter.ui.main.MainFragment

private const val KEY_ITEM_TEXT = "androidx.viewpager2.integration.testapp.KEY_ITEM_TEXT"
private const val KEY_CLICK_COUNT = "androidx.viewpager2.integration.testapp.KEY_CLICK_COUNT"

class CounterActivity : CounterBaseActivity() {


    override fun createViewPagerAdapter(): RecyclerView.Adapter<*> {
        val items = items // avoids resolving the ViewModel multiple times
        return object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): MainFragment {
                val itemId = items.itemId(position)
                val itemText = items.getItemById(itemId)
                return MainFragment.newInstance(position+1)
            }
            override fun getItemCount(): Int = items.size
            override fun getItemId(position: Int): Long = items.itemId(position)
            override fun containsItem(itemId: Long): Boolean = items.contains(itemId)
        }
    }
}
