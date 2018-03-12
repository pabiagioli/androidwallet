package org.pampanet.mobile.walletchallenge.di

import dagger.Module
import dagger.Provides
import org.bitcoinj.core.NetworkParameters
import org.pampanet.mobile.walletchallenge.business.PinManager
import org.pampanet.mobile.walletchallenge.business.WalletManager
import javax.inject.Singleton

@Module
class BusinessModule(private val pinMaxAttempts: Int, private val pinMaxLength: Int, private val bitcoinNetwork: String) {

    @Provides
    @Singleton
    fun provideWalletManager(): WalletManager {
        //Network Parameters can return null if the ID is invalid (not our case)
        return WalletManager(NetworkParameters.fromID(bitcoinNetwork))
    }

    @Provides
    @Singleton
    fun providePinManager(): PinManager {
        return PinManager(pinMaxAttempts, pinMaxLength)
    }

}
