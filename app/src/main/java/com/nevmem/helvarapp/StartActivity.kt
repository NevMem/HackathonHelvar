package com.nevmem.helvarapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.nevmem.helvarapp.data.Room
import kotlinx.android.synthetic.main.room_page.view.*
import kotlinx.android.synthetic.main.start_activity.*

interface PagePickerListener {
    fun prevPageCalled()
    fun nextPageCalled()
}

class StartActivity : AppCompatActivity(), PagePickerListener {
    private val rooms = listOf(
        Room("Living room", R.drawable.ic_launcher_background),
        Room("Not a living room", R.drawable.ic_launcher_background))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_activity)

        pager.adapter = PagerAdapter(this, rooms)

        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                checkButtons()
            }
        })

        prevButton.setOnClickListener {
            prevPageCalled()
        }
        nextButton.setOnClickListener {
            nextPageCalled()
        }
    }

    private fun checkButtons() {
        val position = pager.currentItem
        prevButton.isEnabled = position != 0
        nextButton.isEnabled = position != rooms.size - 1
    }

    override fun prevPageCalled() {
        if (pager.currentItem != 0) {
            pager.currentItem = pager.currentItem - 1
        }
    }

    override fun nextPageCalled() {
        if (pager.currentItem != rooms.size - 1) {
            pager.currentItem = pager.currentItem + 1
        }
    }

    override fun onBackPressed() {
        // TODO: (handle this shit)
    }

    class PagerAdapter(
            private val ctx: Context,
            private val rooms: List<Room>) : RecyclerView.Adapter<PagerAdapter.VH>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val inflater = LayoutInflater.from(ctx)
            val view = inflater.inflate(R.layout.room_page, parent, false)
            return VH(view)
        }

        override fun getItemCount(): Int = rooms.size

        override fun onBindViewHolder(holder: VH, position: Int) {
            holder.heading.text = rooms[position].name
            holder.image.setImageResource(rooms[position].imageId)
        }

        class VH(view: View) : RecyclerView.ViewHolder(view) {
            val heading = view.headingText
            val image = view.roomImage
        }
    }
}
