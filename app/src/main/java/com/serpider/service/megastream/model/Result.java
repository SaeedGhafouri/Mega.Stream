package com.serpider.service.megastream.model;

public class Result {

    /*Result Server*/
    private boolean STATUS;
    private String MESSAGE, OTP;
    public Result(boolean STATUS, String MESSAGE, String OTP) {
        this.STATUS = STATUS;
        this.MESSAGE = MESSAGE;
        this.OTP = OTP;
    }
    public boolean isSTATUS() {
        return STATUS;
    }
    public String getMESSAGE() {
        return MESSAGE;
    }
    public String getOTP() {
        return OTP;
    }
    /*ENDResult Server*/

}
