package ge.sweeft.spacex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ge.sweeft.spacex.adapter.LinksAdapter
import ge.sweeft.spacex.databinding.PopupBinding

class LinksPopup(var adapter: LinksAdapter) : Fragment() {

    private lateinit var binding: PopupBinding
    private lateinit var linksAdapter: LinksAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PopupBinding.inflate(inflater, container, false)
        return binding.root


        binding.linkRecycler.adapter=linksAdapter
    }
}