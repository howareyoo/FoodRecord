package com.jica.foodrecord;



public class FoodItem {

    int _id;
    String date;
    String title;
    String picture;
    float ratingbar;
    String time;
    String personnel;
    String contents;
    String location;



    public FoodItem() {
    }


    public FoodItem(int _id, String date, String title, String picture, float ratingbar, String time, String personnel,String contents, String location) {
        this._id = _id;
        this.date = date;
        this.title = title;
        this.picture = picture;
        this.ratingbar = ratingbar;
        this.time = time;
        this.personnel = personnel;

        this.contents = contents;
        this.location = location;

    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getPersonnel() {
        return personnel;
    }

    public void setPersonnel(String personnel) {
        this.personnel = personnel;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public float getRatingbar() {
        return ratingbar;
    }

    public void setRatingbar(float ratingbar) {
        this.ratingbar = ratingbar;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "FoodItem{" +
                "date='" + date + '\'' +
                ", title='" + title + '\'' +
                ", picture='" + picture + '\'' +
                ", ratingbar ='" + ratingbar + '\'' +
                ", time ='" + time + '\'' +
                ", personnel ='" + personnel + '\'' +
                ", contents='" + contents + '\'' +
                ", location='" + location + '\'' +
                '}';

    }
}