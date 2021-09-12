package com.denofprogramming.mycoroutineapplication.network

import android.graphics.Bitmap
import com.denofprogramming.mycoroutineapplication.shared.uilt.logMessage
import java.io.IOException

//Mock Network library not supporting Coroutines... simulates Main Thread Blocking style.
class MockNetworkService : NetworkService {


    override fun cancel() {
    }

    override fun getImage(id: String): Bitmap? {
        logMessage("MockNetworkService.getImage() downloading...")
        Thread.sleep(5000) // Simulate network call and download...
        return allImages[id.toInt()]
    }


    companion object {

        fun build(): MockNetworkService {
            return MockNetworkService()
        }

    }
}