package org.pampanet.mobile.walletchallenge.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_wallet.*
import org.pampanet.mobile.walletchallenge.R
import org.pampanet.mobile.walletchallenge.helper.displayDialog
import org.pampanet.mobile.walletchallenge.helper.displayPinDialog
import org.pampanet.mobile.walletchallenge.vm.WalletViewModel
import javax.inject.Inject

class WalletActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var vm: WalletViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        vm = ViewModelProviders.of(this, viewModelFactory).get(WalletViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()

        vm.walletAddressEmitter.observe(this, Observer<String>{addrStr->
            displayWalletAddress(addrStr)
        })

        create_wallet_btn.setOnClickListener {
            vm.vmCreateWalletTask()
        }

        wallet_private_key_btn.setOnClickListener {
            displayPinDialog(getString(R.string.app_name), getString(R.string.enter_pin), getString(R.string.ok_btn), {
                pin-> vm.vmAuth(pin)
            }, {
                displayDialog(getString(R.string.app_name), vm.vmRetrievePrivateKey(), getString(R.string.ok_btn), null)
            })
        }

        wallet_addr_change_btn.setOnClickListener {
            vm.vmRefreshWalletAddress()
        }

        vm.vmRetrieveWalletAddress()
    }

    override fun onPause() {
        super.onPause()
        vm.walletAddressEmitter.removeObservers(this)
    }

    private fun displayWalletAddress(walletAddress:String?){
        if(walletAddress != null){
            wallet_addr_txt.text = getString(R.string.wallet_address, walletAddress)
            create_wallet_btn.visibility = View.GONE
            wallet_addr_change_btn.visibility = View.VISIBLE
            wallet_private_key_btn.visibility = View.VISIBLE
        }else{
            wallet_addr_txt.text = getString(R.string.wallet_address, "Not Created or Not Available")
            create_wallet_btn.visibility = View.VISIBLE
            wallet_addr_change_btn.visibility = View.GONE
            wallet_private_key_btn.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let {
            return when(it.itemId){
                android.R.id.home->{
                    NavUtils.navigateUpFromSameTask(this)
                    true
                }
                else-> super.onOptionsItemSelected(item)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
