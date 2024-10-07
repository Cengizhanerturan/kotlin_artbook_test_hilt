package com.gcyazilim.artbooktesthilt.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.gcyazilim.artbooktesthilt.databinding.FragmentArtDetailsBinding
import com.gcyazilim.artbooktesthilt.util.Resource
import com.gcyazilim.artbooktesthilt.util.Status
import com.gcyazilim.artbooktesthilt.viewmodel.ArtViewModel
import javax.inject.Inject


class ArtDetailsFragment
@Inject
constructor(
    val glide: RequestManager,
    private val viewModel: ArtViewModel
) : Fragment() {
    private var _binding: FragmentArtDetailsBinding? = null

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
        _binding = FragmentArtDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickImage()
        clickSaveButton()
        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer { url ->
            glide.load(url).into(binding.artImageView)
        })

        viewModel.insertArtMessage.observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    Toast.makeText(context, "Success", Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                    viewModel.resetInsertArtMsg()
                }

                Status.ERROR -> {
                    Toast.makeText(context, resource.message ?: "Error!", Toast.LENGTH_LONG).show()
                }

                Status.LOADING -> {

                }

            }
        })
    }

    private fun clickSaveButton() = binding.saveButton.setOnClickListener {
        viewModel.makeArt(
            binding.nameText.text.toString(),
            binding.artistText.text.toString(),
            binding.yearText.text.toString()
        )
    }


    private fun clickImage() {
        binding.artImageView.setOnClickListener {
            val action = ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApiFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}