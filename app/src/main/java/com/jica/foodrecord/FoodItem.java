package com.jica.foodrecord;



public class FoodItem {


  String date;
  String title;
  String contents;
  String location;

    public FoodItem() {
    }

    public FoodItem(String date, String title, String contents, String location) {

        this.date = date;
        this.title = title;
        this.contents = contents;
        this.location = location;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
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
                ", contents='" + contents + '\'' +
                ", location='" + location + '\'' +
                '}';

    }
}
