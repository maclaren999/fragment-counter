package ua.ukrop.fragmentcounter

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import androidx.viewpager2.widget.ViewPager2
import ua.ukrop.fragmentcounter.ui.main.MainFragment
import ua.ukrop.fragmentcounter.ui.main.MainViewModel

private const val KEY_ITEM_TEXT = "androidx.viewpager2.integration.testapp.KEY_ITEM_TEXT"
private const val KEY_CLICK_COUNT = "androidx.viewpager2.integration.testapp.KEY_CLICK_COUNT"
private const val TAG = "TAG_CounterActivity"

class CounterActivity : FragmentActivity() {

    private lateinit var viewPager: ViewPager2
    val itemsViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counter)

        viewPager = findViewById(R.id.pager)
        viewPager.adapter = createViewPagerAdapter()

        itemsViewModel.fragmentCounter.observe(this){
            Log.d(TAG, "observe(this){it = ${it.toString()}}")
            viewPager.adapter!!.notifyDataSetChanged()
        }
    }

    fun createViewPagerAdapter(): RecyclerView.Adapter<*> {
        val items = itemsViewModel.fragmentCounter.value // avoids resolving the ViewModel multiple times

        return object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): MainFragment {
                Log.d(TAG, "createFragment(position = $position)")
                return MainFragment.newInstance(position + 1)
            }

            override fun getItemCount(): Int = items!!.size
//            override fun getItemId(position: Int): Long = items.itemId(position)
//            override fun containsItem(itemId: Long): Boolean = items.contains(itemId)
        }
    }

}
