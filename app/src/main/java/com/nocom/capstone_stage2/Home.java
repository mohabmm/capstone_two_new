package com.nocom.capstone_stage2;

/**
 * Created by Moha on 1/25/2018.
 */

class Home {
    private String mweburl;
    private String msnippet;
    private String mimageurl;
    private String marticleheadline;

    public Home(String weburl, String snippet, String articleheadline, String imageurl) {
        mweburl = weburl;
        msnippet = snippet;
        marticleheadline = articleheadline;
        mimageurl = imageurl;

    }

    public String getMsnippet() {
        return msnippet;
    }

    public String getMweburl() {
        return mweburl;
    }

    public String getMimageurl() {
        return mimageurl;
    }

    public String getMarticleheadline() {
        return marticleheadline;
    }
}
