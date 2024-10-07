package com.gcyazilim.artbooktesthilt.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gcyazilim.artbooktesthilt.adapter.ArtRecyclerAdapter
import com.gcyazilim.artbooktesthilt.databinding.FragmentArtsBinding
import com.gcyazilim.artbooktesthilt.viewmodel.ArtViewModel
import javax.inject.Inject


class ArtsFragment
@Inject
constructor(
    val artRecyclerAdapter: ArtRecyclerAdapter,
    val viewModel: ArtViewModel,
) : Fragment() {
    private var _binding: FragmentArtsBinding? = null

    private val swipeCallBack =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val layoutPosition = viewHolder.layoutPosition
                val selectedArt = artRecyclerAdapter.arts[layoutPosition]
                viewModel.deleteArt(selectedArt)
            }
        }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArtsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        subscribeToObservers()
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.recyclerViewArt)
        fabSetOnClickListener()
    }

    private fun setAdapter() {
        binding.recyclerViewArt.apply {
            adapter = artRecyclerAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun subscribeToObservers() = viewModel.artList.observe(viewLifecycleOwner, Observer {
        artRecyclerAdapter.arts = it
    })


    private fun fabSetOnClickListener() {
        binding.fab.setOnClickListener {
            val action = ArtsFragmentDirections.actionArtsFragmentToArtDetailsFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}