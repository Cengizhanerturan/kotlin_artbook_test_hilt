package com.gcyazilim.artbooktesthilt.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.gcyazilim.artbooktesthilt.adapter.ArtRecyclerAdapter
import com.gcyazilim.artbooktesthilt.adapter.ImageRecyclerAdapter
import com.gcyazilim.artbooktesthilt.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtFragmentFactory
@Inject
constructor(
    private val glide: RequestManager,
    private val artRecyclerAdapter: ArtRecyclerAdapter,
    private val imageRecyclerAdapter: ImageRecyclerAdapter,
    private val artViewModel: ArtViewModel,
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            ArtDetailsFragment::class.java.name -> ArtDetailsFragment(glide, artViewModel)
            ArtsFragment::class.java.name -> ArtsFragment(artRecyclerAdapter, artViewModel)
            ImageApiFragment::class.java.name -> ImageApiFragment(
                imageRecyclerAdapter,
                artViewModel
            )

            else -> super.instantiate(classLoader, className)
        }
    }

}