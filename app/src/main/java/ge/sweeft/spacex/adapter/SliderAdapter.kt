package ge.sweeft.spacex.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.squareup.picasso.Picasso
import ge.sweeft.spacex.MissionsActivity
import ge.sweeft.spacex.R
import ge.sweeft.spacex.data.ShipView
import ge.sweeft.spacex.databinding.SlideItemContainerBinding

class SliderAdapter(
    sliderItems: MutableList<ShipView>,
    viewPager2: ViewPager2
) : RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    private val sliderItems: List<ShipView>
    private val viewPager2: ViewPager2

    init {
        this.sliderItems = sliderItems
        this.viewPager2 = viewPager2
        viewPager2.adapter = this
    }

    class SliderViewHolder(binding: SlideItemContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val imageView: ImageView = itemView.findViewById(R.id.img_ship)
        private val shipName: TextView = itemView.findViewById(R.id.ship_name_text)
        private val shipType: TextView = itemView.findViewById(R.id.ship_type_text)
        private val homePort: TextView = itemView.findViewById(R.id.port_text)

        init {
            binding.imgShip.setOnClickListener {
                val int = Intent(itemView.context, MissionsActivity::class.java)
                itemView.context.startActivity(int)
            }
        }

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

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val binding = SlideItemContainerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SliderViewHolder(binding)
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

}