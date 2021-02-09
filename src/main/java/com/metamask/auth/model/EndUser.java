package com.metamask.auth.model;

import javax.persistence.*;

@Entity
@Table(name = "end_user")
public class EndUser {

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true)
    private String publicAddress;

    @Column
    private Long nonce;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPublicAddress() {
        return publicAddress;
    }

    public void setPublicAddress(String publicAddress) {
        this.publicAddress = publicAddress;
    }

    public Long getNonce() {
        return nonce;
    }

    public void setNonce(Long nonce) {
        this.nonce = nonce;
    }
}
