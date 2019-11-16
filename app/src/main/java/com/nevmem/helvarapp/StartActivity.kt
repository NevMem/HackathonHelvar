package com.nevmem.helvarapp

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.nevmem.helvarapp.activities.AddRoomActivity
import com.nevmem.helvarapp.activities.SmartModeActivity
import com.nevmem.helvarapp.data.Room
import com.nevmem.helvarapp.permissions.ListenableActivity
import kotlinx.android.synthetic.main.room_page.view.*
import kotlinx.android.synthetic.main.start_activity.*

interface PagePickerListener {
    fun prevPageCalled()
    fun nextPageCalled()
}

class StartActivity : ListenableActivity(), PagePickerListener {
    private val rooms = listOf(
        Room("Living room", R.drawable.tv),
        Room("Kitchen", R.drawable.food),
        Room("Entrance", R.drawable.walk))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_activity)

        window.statusBarColor = resources.getColor(R.color.white)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

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

        smartMode.setOnClickListener {
            gotoSmartModePage()
        }

        addButton.setOnClickListener {
            gotoAddRoomPage()
        }
    }

    private fun gotoSmartModePage() {
        startActivity(
            Intent(this, SmartModeActivity::class.java),
            ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }

    private fun gotoAddRoomPage() {
        startActivity(
            Intent(this, AddRoomActivity::class.java),
            ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
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
            val room = rooms[position]
            holder.heading.text = room.name
            holder.image.setImageResource(room.imageId)
            val padding = 28
            holder.temperature.setPadding(padding, 0, padding, 0)
            holder.brightness.setPadding(padding, 0, padding, 0)
            holder.mode.text = room.modeString()
            holder.checkbox.setPadding(0, 0, 0, 0)
            holder.checkboxLabel.setOnClickListener {
                val newValue = !holder.checkbox.isChecked
                holder.checkbox.isChecked = newValue
                room.bySensor = newValue
            }
            holder.checkbox.isChecked = room.bySensor

            holder.temperature.progress = (room.temperature - 2000) * 100 / 5000
            holder.temperature.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(bar: SeekBar?, value: Int, byUser: Boolean) {
                    room.temperature = 2000 + 5000 * value / 100
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {}
                override fun onStopTrackingTouch(p0: SeekBar?) {}
            })

            holder.brightness.progress = room.brightness
            holder.brightness.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(bar: SeekBar?, value: Int, byUser: Boolean) {
                    room.brightness = value
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {}
                override fun onStopTrackingTouch(p0: SeekBar?) {}
            })
        }

        class VH(view: View) : RecyclerView.ViewHolder(view) {
            val heading = view.headingText
            val image = view.roomImage
            val temperature = view.temperature
            val brightness = view.brightness
            val mode = view.mode
            val checkbox = view.sensorCheckbox
            val checkboxLabel = view.checkboxLabel
        }
    }
}
