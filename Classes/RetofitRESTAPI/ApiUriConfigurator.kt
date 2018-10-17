package com.lykkex.LykkeWallet.gui.utils

import com.lykkex.LykkeWallet.BuildConfig
import com.lykkex.LykkeWallet.gui.models.ApiUriConfig

/**
 * @author Created by i.statkevich on 26.3.18.
 */
interface ApiUriConfigurator {
    companion object {
        val DEFAULT_SERVER_ID = 0
    }

    fun createConfig(idServer: Int): ApiUriConfig

    class DefaultUriConfigurator : ApiUriConfigurator {
        override fun createConfig(idServer: Int): ApiUriConfig {
            if (!BuildConfig.CHANGE_SERVER) {
                return createProductionVersion()
            }

            return when (idServer) {
                0 -> createDevVersion()
                1 -> createTestVersion()
                2 -> createStagingVersion()
                else -> throw IllegalArgumentException("Unsupported configuration")
            }

            // You can implements hooks/hacks after creation a default configuration
        }

        private fun createProductionVersion(): ApiUriConfig
                = ApiUriConfig(
                "https://api.lykke.com",
                "https://apiv2.lykke.com",
                "wss://wamp.lykke.com/ws/",
                "https://auth.lykke.com/")

        private fun createDevVersion(): ApiUriConfig
                = ApiUriConfig(
                "https://api-dev.lykkex.net/",
                "https://apiv2-dev.lykkex.net/",
                "wss://wamp-dev.lykkex.net/ws/",
                "https://auth-dev.lykkex.net/")

        private fun createTestVersion(): ApiUriConfig
                = ApiUriConfig(
                "https://api-test.lykkex.net/",
                "https://apiv2-test.lykkex.net/",
                "wss://wamp-test.lykkex.net/ws/",
                "https://auth-test.lykkex.net/")

        private fun createStagingVersion(): ApiUriConfig
                = ApiUriConfig(
                "https://api-staging.lykke.com",
                "https://apiv2-staging.lykke.com/",
                "wss://wamp-staging.lykke.com/ws/",
                "https://auth-staging.lykke.com/")

    }
}