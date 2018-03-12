package org.pampanet.mobile.walletchallenge.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import org.pampanet.mobile.walletchallenge.vm.AppViewModelFactory
import org.pampanet.mobile.walletchallenge.vm.MainViewModel
import org.pampanet.mobile.walletchallenge.vm.WalletViewModel

@Module
internal abstract class ViewModelModule{

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WalletViewModel::class)
    internal abstract fun bindWalletViewModel(walletViewModel: WalletViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}