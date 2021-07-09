package ge.sweeft.spacex.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ge.sweeft.spacex.databinding.PopupItemBinding

class LinksAdapter(private var missionsList: MutableList<String>) :
    RecyclerView.Adapter<LinksAdapter.ListSelectionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSelectionViewHolder {
        val binding =
            PopupItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListSelectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListSelectionViewHolder, position: Int) {
        holder.onBind(missionsList[position])
    }

    override fun getItemCount(): Int {
        return missionsList.size
    }

    class ListSelectionViewHolder(private val binding: PopupItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(url: String) {
            binding.link.text = url
        }
    }
}