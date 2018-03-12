package org.pampanet.mobile.walletchallenge.vm

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import org.pampanet.mobile.walletchallenge.PinMaxAttemptsException
import org.pampanet.mobile.walletchallenge.R
import org.pampanet.mobile.walletchallenge.WalletChallengeApp
import org.pampanet.mobile.walletchallenge.business.PinManager
import org.pampanet.mobile.walletchallenge.helper.PinChangeEnum
import javax.inject.Inject

class MainViewModel @Inject constructor(app: Application, private val pinManager: PinManager) : AndroidViewModel(app) {

    var til2LiveData : MutableLiveData<String> = MutableLiveData()

    fun vmLogin(pin:Int, onCompleted:(Boolean)->Unit, onError:()->Unit) {
        try {
            val result = pinManager.authenticate(pin)
            onCompleted(result)
        }catch (ex: PinMaxAttemptsException){
                onError()
        }
    }

    fun vmChangePIN(pinChangeStr: String){
        val app = getApplication<WalletChallengeApp>()
        if(pinChangeStr.isNotEmpty()) {
            when(pinManager.changePin(pinChangeStr)){
                PinChangeEnum.SUCCESS->{
                    til2LiveData.postValue(null)
                }
                PinChangeEnum.NOT_LONG_ENOUGH->{
                    til2LiveData.postValue(app.getString(R.string.pin_short))
                }
                PinChangeEnum.SAME_AS_PREVIOUS->{
                    til2LiveData.postValue(app.getString(R.string.pin_is_old))
                }
                else-> til2LiveData.postValue("WTF")
            }
        }
    }

}