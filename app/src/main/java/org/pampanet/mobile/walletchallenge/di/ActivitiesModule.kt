package org.pampanet.mobile.walletchallenge.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import org.pampanet.mobile.walletchallenge.activity.MainActivity
import org.pampanet.mobile.walletchallenge.activity.WalletActivity

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector
    internal abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun contributeWalletActivity(): WalletActivity
}
