package com.spikingacacia.spikyletadelivery.database;

import java.io.Serializable;

public class ServerAccount implements Serializable
{
    private int id;
    private String email;
    private String username;
    private String location;
    private String imageType;
    private double wallet;
    private String dateadded;
    private String datechanged;

    public ServerAccount(){}

    public ServerAccount(int id, String email, String username, String location, String imageType, double wallet, String dateadded, String datechanged) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.location = location;
        this.imageType = imageType;
        this.wallet = wallet;
        this.dateadded = dateadded;
        this.datechanged = datechanged;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImageType()
    {
        return imageType;
    }

    public void setImageType(String imageType)
    {
        this.imageType = imageType;
    }

    public double getWallet()
    {
        return wallet;
    }

    public void setWallet(double wallet)
    {
        this.wallet = wallet;
    }

    public String getDateadded() {
        return dateadded;
    }

    public void setDateadded(String dateadded) {
        this.dateadded = dateadded;
    }

    public String getDatechanged() {
        return datechanged;
    }

    public void setDatechanged(String datechanged) {
        this.datechanged = datechanged;
    }

}
