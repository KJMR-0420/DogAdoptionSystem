package com.example.dogadoptionsystem.HelperClass;

public class AdoptionFormHelper {
    String Fdogname,Fdogbreed,Fdogloc,Fdogaddress,FdogNum,FdogValidPhoto;
    String imageurl1;

    public AdoptionFormHelper() {
    }

    public AdoptionFormHelper(String fdogname, String fdogbreed, String fdogloc, String fdogaddress, String fdogNum, String fdogValidPhoto, String imageurl1) {
        Fdogname = fdogname;
        Fdogbreed = fdogbreed;
        Fdogloc = fdogloc;
        Fdogaddress = fdogaddress;
        FdogNum = fdogNum;
        FdogValidPhoto = fdogValidPhoto;
        this.imageurl1 = imageurl1;
    }

    public String getFdogname() {
        return Fdogname;
    }

    public void setFdogname(String fdogname) {
        Fdogname = fdogname;
    }

    public String getFdogbreed() {
        return Fdogbreed;
    }

    public void setFdogbreed(String fdogbreed) {
        Fdogbreed = fdogbreed;
    }

    public String getFdogloc() {
        return Fdogloc;
    }

    public void setFdogloc(String fdogloc) {
        Fdogloc = fdogloc;
    }

    public String getFdogaddress() {
        return Fdogaddress;
    }

    public void setFdogaddress(String fdogaddress) {
        Fdogaddress = fdogaddress;
    }

    public String getFdogNum() {
        return FdogNum;
    }

    public void setFdogNum(String fdogNum) {
        FdogNum = fdogNum;
    }

    public String getFdogValidPhoto() {
        return FdogValidPhoto;
    }

    public void setFdogValidPhoto(String fdogValidPhoto) {
        FdogValidPhoto = fdogValidPhoto;
    }

    public String getImageurl1() {
        return imageurl1;
    }

    public void setImageurl1(String imageurl1) {
        this.imageurl1 = imageurl1;
    }
}

