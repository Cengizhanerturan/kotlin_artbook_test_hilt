package com.gcyazilim.artbooktesthilt.repository

import androidx.lifecycle.LiveData
import com.gcyazilim.artbooktesthilt.api.RetrofitAPI
import com.gcyazilim.artbooktesthilt.model.ImageResponse
import com.gcyazilim.artbooktesthilt.roomdb.Art
import com.gcyazilim.artbooktesthilt.roomdb.ArtDao
import com.gcyazilim.artbooktesthilt.util.Resource
import javax.inject.Inject

class ArtRepository
@Inject
constructor(
    private val artDao: ArtDao,
    private val retrofitAPI: RetrofitAPI
) : ArtRepositoryInterface {
    override suspend fun insertArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeArts()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        try {
            val response = retrofitAPI.imageSearch(imageString)
            if (response.isSuccessful) {
                response.body()?.let {
                    return Resource.success(it)
                } ?: return Resource.error("Error", null)
            } else {
                return Resource.error("Error", null)
            }
        } catch (e: Exception) {
            return Resource.error("No data! $e", null)
        }
    }
}