package com.orbit.shuttle.ui.profile

import com.orbit.shuttle.fmt.http.HttpBean

class HttpSettingsActivity : StandardV2RaySettingsActivity() {

    override fun createEntity() = HttpBean()

}
