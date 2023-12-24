package com.serpider.service.megastream.model;

public class Result {

    /*Result Server*/
    private boolean status;
    private String message, otp;
    private long cpn_value;

    public Result(boolean status, String message, String otp, long cpn_value) {
        this.status = status;
        this.message = message;
        this.otp = otp;
        this.cpn_value = cpn_value;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public long getCpn_value() {
        return cpn_value;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    /*ENDResult Server*/

}
