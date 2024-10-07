package com.gcyazilim.artbooktesthilt.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gcyazilim.artbooktesthilt.model.ImageResponse
import com.gcyazilim.artbooktesthilt.repository.ArtRepositoryInterface
import com.gcyazilim.artbooktesthilt.roomdb.Art
import com.gcyazilim.artbooktesthilt.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArtViewModel
@Inject
constructor(
    private val repository: ArtRepositoryInterface
) : ViewModel() {

    // Art Fragment

    val artList = repository.getArt()

    // Image API Fragment

    private val images = MutableLiveData<Resource<ImageResponse>>()
    val imageList: LiveData<Resource<ImageResponse>>
        get() = images

    private val selectedImage = MutableLiveData<String>()
    val selectedImageUrl: LiveData<String>
        get() = selectedImage


    //Art Details Fragment

    private var insertArtMsg = MutableLiveData<Resource<Art>>()
    val insertArtMessage: LiveData<Resource<Art>>
        get() = insertArtMsg

    fun resetInsertArtMsg() {
        insertArtMsg = MutableLiveData<Resource<Art>>()
    }

    fun setSelectedImage(url: String) {
        selectedImage.value = url
    }

    fun deleteArt(art: Art) = viewModelScope.launch {
        repository.deleteArt(art)
    }

    fun insertArt(art: Art) = viewModelScope.launch {
        repository.insertArt(art)
    }

    fun makeArt(name: String, artistName: String, year: String) {
        if (name.isEmpty() || artistName.isEmpty() || year.isEmpty()) {
            insertArtMsg.value = Resource.error("Enter name, artist, year", null)
            return
        }

        val yearInt = try {
            year.toInt()
        } catch (e: Exception) {
            insertArtMsg.value = Resource.error("Year should be number", null)
            return
        }

        val art = Art(name, artistName, yearInt, selectedImageUrl.value ?: "", null)
        insertArt(art)
        setSelectedImage("")
        insertArtMsg.value = Resource.success(art)
    }

    fun searchForImage(searchString: String) {
        if (searchString.isEmpty()) {
            return
        }
        images.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.searchImage(searchString)
            images.value = response
        }
    }
}