package com.metamask.auth.model.dto;

public class LoginRequest {

    private String publicAddress;

    private String signiture;

    public String getPublicAddress() {
        return publicAddress;
    }

    public void setPublicAddress(String publicAddress) {
        this.publicAddress = publicAddress;
    }

    public String getSigniture() {
        return signiture;
    }

    public void setSigniture(String signiture) {
        this.signiture = signiture;
    }
}
