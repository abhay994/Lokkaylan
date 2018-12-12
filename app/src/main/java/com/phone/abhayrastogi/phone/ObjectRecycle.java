package com.phone.abhayrastogi.phone;

/**
 * Created by abhayrastogi on 30/03/18.
 */

public class ObjectRecycle {
    String category,city,name,imageurl,contact;

    public ObjectRecycle() {
    }

    public ObjectRecycle(String category, String city, String name, String imageurl, String contact) {
        this.category = category;
        this.city = city;
        this.name = name;
        this.imageurl = imageurl;
        this.contact = contact;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
