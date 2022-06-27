package com.tplinkdns.tadap.mobilecomputingproject.fireBase;

public class DataModelReview {

    public String content = "";
    public String Uid = "";
    public String ContentID = "";
    public String mail = "";

    public DataModelReview(){}

    public DataModelReview(String content, String Uid, String ContentID,String mail) {
        this.content = content;
        this.Uid = Uid;
        this.ContentID = ContentID;
        this.mail = mail;
    }
}
