package com.example.thehomechef.recipe;

public class fileinfomodel {
    String filename, fileurl;
    public fileinfomodel() {
    }

    public fileinfomodel(String filename, String fileurl) {
        this.filename = filename;
        this.fileurl = fileurl;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    public String getFileurl() {
        return fileurl;
    }
}