package com.orbit.shuttle.fmt

import com.orbit.shuttle.R
import com.orbit.shuttle.SeeWApp

enum class PluginEntry(
    val pluginId: String,
    val displayName: String,
    val packageName: String, // for play and f-droid page
    val downloadSource: DownloadSource = DownloadSource()
) {
    TrojanGo(
        "trojan-go-plugin",
        SeeWApp.application.getString(R.string.action_trojan_go),
        "com.orbit.shuttle.plugin.trojan_go"
    ),
    MieruProxy(
        "mieru-plugin",
        SeeWApp.application.getString(R.string.action_mieru),
        "com.orbit.shuttle.plugin.mieru",
        DownloadSource(
            playStore = false,
            fdroid = false,
            downloadLink = ""
        )
    ),
    NaiveProxy(
        "naive-plugin",
        SeeWApp.application.getString(R.string.action_naive),
        "com.orbit.shuttle.plugin.naive",
        DownloadSource(
            playStore = false,
            fdroid = false,
            downloadLink = ""
        )
    ),
    Hysteria(
        "hysteria-plugin",
        SeeWApp.application.getString(R.string.action_hysteria),
        "com.orbit.shuttle.plugin.hysteria",
        DownloadSource(
            playStore = false,
            fdroid = false,
            downloadLink = ""
        )
    ),
    ;

    data class DownloadSource(
        val playStore: Boolean = true,
        val fdroid: Boolean = true,
        val downloadLink: String = ""
    )

    companion object {

        fun find(name: String): PluginEntry? {
            for (pluginEntry in enumValues<PluginEntry>()) {
                if (name == pluginEntry.pluginId) {
                    return pluginEntry
                }
            }
            return null
        }

    }

}