package org.pampanet.mobile.walletchallenge.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.til_pin.*
import org.pampanet.mobile.walletchallenge.R
import org.pampanet.mobile.walletchallenge.helper.displayDialog
import org.pampanet.mobile.walletchallenge.vm.MainViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var vm: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vm = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()

        vm.til2LiveData.observe(this, Observer<String> { til2Error ->
            til2.error = til2Error
            if(til2Error == null)
                onSuccessfulLogin()
        })

        loginBtn.setOnClickListener {
            wallet_pin.text?.let { pinStr->
                if(pinStr.isNotEmpty()) {
                    val pin = Integer.parseInt(pinStr.toString())
                    vm.vmLogin(pin, { authSuccess->
                        if(authSuccess){
                            til1.error = null
                            if(til2.visibility == View.VISIBLE){
                                val pinChangeStr = pin_change_txt.text.toString()
                                vm.vmChangePIN(pinChangeStr)
                            }else{
                                onSuccessfulLogin()
                            }
                        } else {
                            til1.error = getString(R.string.pin_incorrect)
                        }
                    } , {
                            displayDialog(getString(R.string.app_name),
                                    getString(R.string.too_many_pin_attempts),
                                    getString(R.string.ok_btn), { this.finish() })
                    })
                }
            }
        }

        pin_change_btn.setOnClickListener {
            if(til2.visibility == View.GONE)
                til2.visibility = View.VISIBLE
            else
                til2.visibility = View.GONE
        }
    }

    override fun onPause() {
        super.onPause()
        vm.til2LiveData.removeObservers(this)
    }

    private fun onSuccessfulLogin(){
        til2.visibility = View.GONE
        til1.error = null
        wallet_pin.text.clear()
        pin_change_txt.text.clear()
        startActivity(Intent(this, WalletActivity::class.java))
    }

}
