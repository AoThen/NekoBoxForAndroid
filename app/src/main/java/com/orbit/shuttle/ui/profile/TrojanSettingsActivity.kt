package com.orbit.shuttle.ui.profile

import com.orbit.shuttle.fmt.trojan.TrojanBean

class TrojanSettingsActivity : StandardV2RaySettingsActivity() {

    override fun createEntity() = TrojanBean()

}
