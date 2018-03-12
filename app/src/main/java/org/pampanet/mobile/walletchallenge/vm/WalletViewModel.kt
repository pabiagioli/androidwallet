package org.pampanet.mobile.walletchallenge.vm

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import org.pampanet.mobile.walletchallenge.business.PinManager
import org.pampanet.mobile.walletchallenge.business.WalletManager
import javax.inject.Inject

class WalletViewModel @Inject constructor(app: Application, val pinManager: PinManager, val walletManager: WalletManager) : AndroidViewModel(app) {

    var walletAddressEmitter: MutableLiveData<String> = MutableLiveData()

    fun vmCreateWalletTask(){
        walletManager.createWallet()
        vmRetrieveWalletAddress()
    }

    fun vmRetrieveWalletAddress(){
        if(walletManager.isCreated) {
            walletAddressEmitter.postValue(walletManager.walletAddress)
        }
    }

    fun vmAuth(pin: String) = pinManager.authenticate(Integer.parseInt(pin))

    fun vmRetrievePrivateKey(): String = walletManager.privateKey

    fun vmRefreshWalletAddress() = walletAddressEmitter.postValue(walletManager.newAddressFromKey)
}