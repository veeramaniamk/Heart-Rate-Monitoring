package com.example.heartrate.profile.api;

import com.google.gson.annotations.SerializedName;

public class InnerProfile{
    @SerializedName("username")
    String username;
    @SerializedName("email")

    String email;
    @SerializedName("age")

    String age;
    @SerializedName("height")

    String height;
    @SerializedName("weight")
    String weight;
    @SerializedName("gender")
    String gender;
    @SerializedName("id")
    String id;
//    public InnerProfile(String id,String username,String email,String age,String height,String weight,String gender)
//    {
//        this.id=id;
//        this.username=username;
//        this.email=email;
//        this.age=age;
//        this.height=height;
//        this.weight=weight;
//        this.gender=gender;
//    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}


