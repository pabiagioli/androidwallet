package org.pampanet.mobile.walletchallenge.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import org.pampanet.mobile.walletchallenge.WalletChallengeApp
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, BusinessModule::class, ViewModelModule::class, ActivitiesModule::class])
interface AppComponent : AndroidInjector<WalletChallengeApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun businessModule(businessModule: BusinessModule): Builder
        fun build(): AppComponent

    }

    override fun inject(instance: WalletChallengeApp)
}
