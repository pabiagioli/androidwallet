package org.pampanet.mobile.walletchallenge.helper

import android.support.design.widget.TextInputLayout
import android.support.v4.app.NavUtils
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import org.pampanet.mobile.walletchallenge.PinMaxAttemptsException
import org.pampanet.mobile.walletchallenge.R

/**
 * Created by pablo.biagioli on 11/03/2018.
 */

/**
 * Displays a simple Alert Dialog with an OK button that dismisses itself and makes some optional custom action afterwards
 * @param title: Alert Dialog's Title
 * @param msg: Alert Dialog's Message
 * @param okBtn: Positive Button's Text
 * @param okAction: Custom Action after dismiss (Optional)
 */
fun AppCompatActivity.displayDialog(title:String, msg:String, okBtn: String, okAction:(()->Unit)?){
    val dialogBuilder = AlertDialog.Builder(this)
            .setMessage(msg)
            .setTitle(title)
            .setPositiveButton(okBtn){ di,_->
                di.dismiss()
                okAction?.let { it() }
            }
    dialogBuilder.create().show()
}

/**
 * Displays a simple Alert Dialog with the PIN TextInputLayout and an OK button
 * @param title: Alert Dialog's Title
 * @param msg: Alert Dialog's Message
 * @param okBtn: Positive Button's Text
 * @param validateFn: Custom Action after clicking OK (input validation)
 * @param onSuccess: Custom Action after validateFn returned TRUE
 */
fun AppCompatActivity.displayPinDialog(title:String, msg:String, okBtn: String,
                                       validateFn:(pin:String)->Boolean, onSuccess:()->Unit){

    val til = LayoutInflater.from(this).inflate(R.layout.til_pin, null) as TextInputLayout
    til.setPadding(26,0,26,0)
    val dialogBuilder = AlertDialog.Builder(this)
            .setMessage(msg)
            .setTitle(title)
            .setCancelable(false)
            .setPositiveButton(okBtn,{_,_-> })
            .setNegativeButton("Cancel",{_,_ -> })
            .setView(til)
    val dialog = dialogBuilder.create()
    dialog.show()
    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
        try{
            val pin = til.editText?.text.toString()
            if(!pin.isEmpty() && validateFn(pin)){
                dialog.dismiss()
                onSuccess()
            }else{
                til.error = getString(R.string.pin_incorrect)
            }
        }catch (ex : PinMaxAttemptsException){
            dialog.dismiss()
            displayDialog(getString(R.string.app_name),getString(R.string.too_many_pin_attempts), getString(R.string.ok_btn),{
                //this.finish()
                NavUtils.navigateUpFromSameTask(this)
            })
        }
    }
}