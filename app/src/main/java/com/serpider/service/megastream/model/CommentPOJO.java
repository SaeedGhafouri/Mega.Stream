package com.serpider.service.megastream.model;

public class CommentPOJO {

    /*Result Server*/
    private boolean STATUS;
    private String MESSAGE;
    public CommentPOJO(boolean STATUS, String MESSAGE) {
        this.STATUS = STATUS;
        this.MESSAGE = MESSAGE;
    }
    public boolean isSTATUS() {
        return STATUS;
    }
    public String getMESSAGE() {
        return MESSAGE;
    }
    /*ENDResult Server*/

}
