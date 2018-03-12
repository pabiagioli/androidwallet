package org.pampanet.mobile.walletchallenge.business;

import android.util.Log;

import org.pampanet.mobile.walletchallenge.PinMaxAttemptsException;
import org.pampanet.mobile.walletchallenge.helper.PinChangeEnum;

public class PinManager {

    private Integer correctPin = 123456;
    private final Integer maxAttemps;
    private final Integer pinMaxLength;

    private Integer WRONG_INTENTS = 0;

    public PinManager(Integer maxAttemps, Integer pinMaxLength){
        this.maxAttemps = maxAttemps;
        this.pinMaxLength = pinMaxLength;
    }

    public Integer getCorrectPin() {
        return correctPin;
    }

    public void setCorrectPin(Integer correctPin) {
        this.correctPin = correctPin;
    }

    public Integer getMaxAttemps() {
        return maxAttemps;
    }

    public boolean authenticate(Integer pin) throws PinMaxAttemptsException {
        boolean isCorrect = pin.equals(getCorrectPin());
        if(!isCorrect)
            WRONG_INTENTS++;
        else
            WRONG_INTENTS = 0;
        if(WRONG_INTENTS.equals(getMaxAttemps())) {
            WRONG_INTENTS = 0;
            throw new PinMaxAttemptsException();
        }
        return isCorrect;
    }

    public PinChangeEnum changePin(String newPin){
        if(pinMaxLength.equals(newPin.length())) {
            int newIntPin = Integer.parseInt(newPin);

            if(newIntPin == getCorrectPin())
                return PinChangeEnum.SAME_AS_PREVIOUS;

            setCorrectPin(Integer.parseInt(newPin));
            return PinChangeEnum.SUCCESS;
        } else if (pinMaxLength > newPin.length()){
            return PinChangeEnum.NOT_LONG_ENOUGH;
        }
        Log.wtf(this.getClass().getName(),"Change Pin");
        return null;
    }

}
