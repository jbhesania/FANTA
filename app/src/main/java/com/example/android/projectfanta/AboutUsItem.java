package com.example.android.projectfanta;

/**
 * Created by User on 5/18/2018.
 */

public class AboutUsItem {
    public String header,footer;
    public int image;

    public AboutUsItem() {
    }

    public AboutUsItem(String header, String footer, int image) {
        this.header = header;
        this.footer = footer;
        this.image = image;
    }


    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
