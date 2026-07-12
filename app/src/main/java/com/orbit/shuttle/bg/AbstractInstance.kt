package com.orbit.shuttle.bg

import java.io.Closeable

interface AbstractInstance : Closeable {

    fun launch()

}