package ge.sweeft.spacex

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import ge.narogava.test.data.ShipView

class SliderAdapter(
    sliderItems:MutableList<ShipView>,
    viewPager:ViewPager2
):RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    private val sliderItems:List<ShipView>

    init {
        this.sliderItems=sliderItems
    }

    class SliderViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        private val imageView:ImageView=itemView.findViewById(R.id.img_ship)

        fun image(sliderItem:ShipView){
            val imageString=Uri.parse(sliderItem.image)
            imageView.setImageURI(imageString)
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
    }

    override fun getItemCount(): Int {
        return sliderItems.size
    }
}