package com.denofprogramming.mycoroutineapplication.ui.main


import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.denofprogramming.mycoroutineapplication.network.MockNetworkService
import com.denofprogramming.mycoroutineapplication.repository.image.BlockingNetworkCallRepository
import com.denofprogramming.mycoroutineapplication.repository.time.DefaultClock
import com.denofprogramming.mycoroutineapplication.shared.Resource
import com.denofprogramming.mycoroutineapplication.shared.uilt.logMessage


class MainViewModel : ViewModel() {


    private val _clock = DefaultClock.build()

    private val _imageRepository =
        BlockingNetworkCallRepository.build(MockNetworkService.build())


    val image: LiveData<Resource<Bitmap>> get() = _image

    private val _image = MutableLiveData<Resource<Bitmap>>()

    val currentTimeTransformed = _clock.time.switchMap {
        val timeFormatted = MutableLiveData<String>()
        val time = _clock.timeStampToTime(it)
        logMessage("currentTimeTransformed time is $time")
        timeFormatted.value = time
        timeFormatted
    }

    init {
        startClock()
    }

    fun onButtonClicked() {
        logMessage("Start onButtonClicked()")
        loadImage()
    }

    private fun loadImage() {
        logMessage("Start loadImage()")
        val imageResource = try {
            _imageRepository.fetchImage(_imageRepository.nextImageId())
        } catch (e: Exception) {
            logMessage("onButtonClicked() exception $e")
            Resource.error(e.localizedMessage ?: "No Message")
        }
        showImage(imageResource)
        logMessage("End onButtonClicked()")
    }

    private fun showImage(imageResource: Resource<Bitmap>) {
        logMessage("Start showImage()")
        _image.value = imageResource
        logMessage("End showImage()")
    }

    private fun startClock() {
        logMessage("Start startClock()")
        _clock.start()
    }
}