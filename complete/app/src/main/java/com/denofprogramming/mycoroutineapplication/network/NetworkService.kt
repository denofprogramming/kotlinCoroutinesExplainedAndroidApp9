package com.denofprogramming.mycoroutineapplication.network

import android.graphics.Bitmap

interface NetworkService {


    fun cancel()

    fun getImage(id: String): Bitmap?

}