package com.example.android.projectfanta;

/**
 * Created by User on 5/18/2018.
 */

public class friendsData {

    //int thumbnail;
    String contactName;

    public friendsData(String contactName) {
        //this.thumbnail = thumbnail;
        this.contactName = contactName;
    }

    public String getContactName(){
        return this.contactName;
    }

    public friendsData() {
    }
}
