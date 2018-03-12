package org.pampanet.mobile.walletchallenge

import dagger.android.support.DaggerApplication
import org.bitcoinj.core.NetworkParameters
import org.pampanet.mobile.walletchallenge.di.BusinessModule
import org.pampanet.mobile.walletchallenge.di.DaggerAppComponent

/**
 * Created by pablo.biagioli on 10/03/2018.
 */

class WalletChallengeApp : DaggerApplication() {
    override fun applicationInjector() = DaggerAppComponent.builder()
                .application(this)
                .businessModule(BusinessModule(5, 6, NetworkParameters.ID_TESTNET))
                .build()

    override fun onCreate() {
        super.onCreate()
        applicationInjector().inject(this)
    }
}
