package com.orbit.shuttle.bg.proto

import com.orbit.shuttle.database.DataStore
import com.orbit.shuttle.database.ProxyEntity

class UrlTest {

    val link = DataStore.connectionTestURL
    private val timeout = 5000

    suspend fun doTest(profile: ProxyEntity): Int {
        return TestInstance(profile, link, timeout).doTest()
    }

}