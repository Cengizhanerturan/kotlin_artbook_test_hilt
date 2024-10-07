package com.gcyazilim.artbooktesthilt.repository

import androidx.lifecycle.LiveData
import com.gcyazilim.artbooktesthilt.model.ImageResponse
import com.gcyazilim.artbooktesthilt.roomdb.Art
import com.gcyazilim.artbooktesthilt.util.Resource

interface ArtRepositoryInterface {
    suspend fun insertArt(art: Art)

    suspend fun deleteArt(art: Art)

    fun getArt(): LiveData<List<Art>>

    suspend fun searchImage(imageString: String): Resource<ImageResponse>
}