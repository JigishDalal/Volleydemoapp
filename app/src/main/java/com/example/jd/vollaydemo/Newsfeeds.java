package com.example.jd.vollaydemo;

/**
 * Created by JD on 09-06-2017.
 */

public class Newsfeeds {
    public String ptitle;
    public String pdetails;

    public Newsfeeds(String ptitle, String pdetails) {
        this.ptitle = ptitle;
        this.pdetails = pdetails;
    }

    public String getPdetails() {
        return pdetails;
    }

    public String getPtitle() {
        return ptitle;
    }

    public String toString () {
        String toReturn = ptitle + ", " + pdetails;
        return toReturn;
    }
}
