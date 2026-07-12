package com.orbit.shuttle.bg.proto

import com.orbit.shuttle.BuildConfig
import com.orbit.shuttle.bg.GuardedProcessPool
import com.orbit.shuttle.database.ProxyEntity
import com.orbit.shuttle.fmt.buildConfig
import com.orbit.shuttle.ktx.Logs
import com.orbit.shuttle.ktx.runOnDefaultDispatcher
import com.orbit.shuttle.ktx.tryResume
import com.orbit.shuttle.ktx.tryResumeWithException
import kotlinx.coroutines.delay
import libcore.Libcore
import com.orbit.shuttle.module.net.LocalResolverImpl
import kotlin.coroutines.suspendCoroutine

class TestInstance(profile: ProxyEntity, val link: String, private val timeout: Int) :
    BoxInstance(profile) {

    suspend fun doTest(): Int {
        return suspendCoroutine { c ->
            processes = GuardedProcessPool {
                Logs.w(it)
                c.tryResumeWithException(it)
            }
            runOnDefaultDispatcher {
                use {
                    try {
                        init()
                        launch()
                        if (processes.processCount > 0) {
                            // wait for plugin start
                            delay(500)
                        }
                        c.tryResume(Libcore.urlTest(box, link, timeout))
                    } catch (e: Exception) {
                        c.tryResumeWithException(e)
                    }
                }
            }
        }
    }

    override fun buildConfig() {
        config = buildConfig(profile, true)
    }

    override suspend fun loadConfig() {
        // don't call destroyAllJsi here
        if (BuildConfig.DEBUG) Logs.d(config.config)
        box = Libcore.newSingBoxInstance(config.config, LocalResolverImpl)
    }

}
