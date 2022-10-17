package com.example.dogadoptionsystem.HelperClass;

public class DogprofileHelper {

    String Dogname,Dogbreed,Doglocation,Dogbackground,pimage;

    public DogprofileHelper(String dogname, String dogbreed, String doglocation, String dogbackground, String pimage) {
        Dogname = dogname;
        Dogbreed = dogbreed;
        Doglocation = doglocation;
        Dogbackground = dogbackground;
        this.pimage = pimage;
    }

    public DogprofileHelper(String toString, String toString1, String toString2, String toString3) {
    }

    public String getDogname() {
        return Dogname;
    }

    public void setDogname(String dogname) {
        Dogname = dogname;
    }

    public String getDogbreed() {
        return Dogbreed;
    }

    public void setDogbreed(String dogbreed) {
        Dogbreed = dogbreed;
    }

    public String getDoglocation() {
        return Doglocation;
    }

    public void setDoglocation(String doglocation) {
        Doglocation = doglocation;
    }

    public String getDogbackground() {
        return Dogbackground;
    }

    public void setDogbackground(String dogbackground) {
        Dogbackground = dogbackground;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage;
    }
}
