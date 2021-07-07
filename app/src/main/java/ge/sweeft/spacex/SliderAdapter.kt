package ge.sweeft.spacex

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.squareup.picasso.Picasso
import ge.narogava.test.data.Mission
import ge.narogava.test.data.ShipView
import kotlin.coroutines.coroutineContext

class SliderAdapter(
    sliderItems: MutableList<ShipView>,
    viewPager2: ViewPager2
) : RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {


    private val sliderItems: List<ShipView>
    private val viewPager2: ViewPager2

    init {
        this.sliderItems = sliderItems
        this.viewPager2 = viewPager2
    }

    class SliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.img_ship)
        private val shipName: TextView = itemView.findViewById(R.id.ship_name_text)
        private val shipType: TextView = itemView.findViewById(R.id.ship_type_text)
        private val homePort: TextView = itemView.findViewById(R.id.port_text)


        fun image(sliderItem: ShipView) {
            if (sliderItem.image != null) {
                Picasso.get().load(sliderItem.image).into(imageView)
            } else {
                Picasso.get().load(
                    "https://i.kym-cdn.com/entries/icons/original/000/027/100/_103330503_musk3.jpg"
                ).into(imageView)
            }

            shipName.text = sliderItem.ship_name
            shipType.text = sliderItem.ship_type
            homePort.text = sliderItem.home_port

            imageView.setOnClickListener {
                val missionList = arrayOf(sliderItem.missions)
                val imageIntent = Intent("send image missions ")
                imageIntent.putExtra("missions_list", missionList)
                LocalBroadcastManager.getInstance(itemView.context).sendBroadcast(imageIntent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        return SliderViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.slide_item_container,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.image(sliderItems[position])
        if (position == sliderItems.size - 2) {
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return sliderItems.size
    }

    private val runnable = Runnable {
        sliderItems.addAll(sliderItems)
        notifyDataSetChanged()
    }

//    interface OnImageClickListener {
//        fun onImageClick(imageData: String?)
//    }
}