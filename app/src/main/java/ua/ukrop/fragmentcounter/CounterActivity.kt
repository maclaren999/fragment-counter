package ua.ukrop.fragmentcounter

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import androidx.viewpager2.widget.ViewPager2
import ua.ukrop.fragmentcounter.ui.main.MainFragment
import ua.ukrop.fragmentcounter.ui.main.MainViewModel

private const val KEY_ITEM_TEXT = "androidx.viewpager2.integration.testapp.KEY_ITEM_TEXT"
private const val KEY_CLICK_COUNT = "androidx.viewpager2.integration.testapp.KEY_CLICK_COUNT"
private const val TAG = "TAG_CounterActivity"
private const val TAG2 = "TAG_callbacks"

class CounterActivity : FragmentActivity() {
    private lateinit var viewPager: ViewPager2
    private val itemsViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counter)

        viewPager = findViewById(R.id.pager)
        viewPager.adapter = createViewPagerAdapter()

        itemsViewModel.pagesLiveData.observe(this) {
            Log.d(TAG, "observe(this){it = ${it.toString()}}")
            viewPager.adapter!!.notifyDataSetChanged()
        }
    }

    fun createViewPagerAdapter(): RecyclerView.Adapter<*> {
        val items = itemsViewModel.pagesLiveData.value // avoids resolving the ViewModel multiple times

        return object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): MainFragment {
                Log.d(TAG, "createFragment(position = $position)")
                return MainFragment.newInstance(position + 1)
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
                itemsViewModel.pagesLiveData.value?.size.also { Log.d(TAG2, "getItemCount() = $it") } ?: 3

            override fun getItemId(position: Int): Long = position + 1L.also { Log.d(TAG2, "getItemId() = $it") }
            override fun containsItem(itemId: Long): Boolean = itemsViewModel.pagesLiveData.value!!.contains(itemId.toInt()).also { Log.d(TAG2, "containsItem() = $it") }
        }
    }
}
