package com.example.coolm.realm.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by coolm on 10/17/2016.
 */
public class Book extends RealmObject {

    @PrimaryKey
    private int id;


    private String description;

    private String date;



    // Standard getters & setters generated by your IDE…
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String day){
        this.date = day;
    }

    public String  getDate()
    {
        return date;

    }


}