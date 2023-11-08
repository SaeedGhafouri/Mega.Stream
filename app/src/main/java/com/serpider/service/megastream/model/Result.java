package com.serpider.service.megastream.model;

public class Result {

    /*Result Server*/
    private boolean status;
    private String message, otp;

    public Result(boolean status, String message, String otp) {
        this.status = status;
        this.message = message;
        this.otp = otp;
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
