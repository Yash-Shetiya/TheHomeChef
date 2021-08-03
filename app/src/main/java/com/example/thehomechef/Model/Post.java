package com.example.thehomechef.Model;

public class Post {

    private String postid;
    private String postimage;
    private String description;
    private String publisher;
    private  String titleN;

    public Post(String postid, String postimage, String description, String titleN) {
        this.postid = postid;
        this.postimage = postimage;
        this.description = description;
        this.titleN = titleN;
        this.publisher = publisher;
    }

    public Post(){
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPostimage() {
        return postimage;
    }

    public void setPostimage(String postimage) {
        this.postimage = postimage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitleN() {
        return titleN;
    }

    public void setTitleN(String titleN) {
        this.titleN = titleN;
    }
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
