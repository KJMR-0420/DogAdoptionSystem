package com.example.dogadoptionsystem.Dashboard;

public class model
{

String dogname,dogbreed,doglocation,dogbackground,pimage;

    public model() {
    }


    public model(String dogname, String dogbreed, String doglocation, String dogbackground,String pimage) {
        this.dogname = dogname;
        this.dogbreed = dogbreed;
        this.doglocation = doglocation;
        this.dogbackground = dogbackground;
        this.pimage = pimage;
    }

    public String getDogname() {
        return dogname;
    }

    public void setDogname(String dogname) {
        this.dogname = dogname;
    }

    public String getDogbreed() {
        return dogbreed;
    }

    public void setDogbreed(String dogbreed) {
        this.dogbreed = dogbreed;
    }

    public String getDoglocation() {
        return doglocation;
    }

    public void setDoglocation(String doglocation) {
        this.doglocation = doglocation;
    }

    public String getDogbackground() {
        return dogbackground;
    }

    public void setDogbackground(String dogbackground) {
        this.dogbackground = dogbackground;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage;
    }
}
