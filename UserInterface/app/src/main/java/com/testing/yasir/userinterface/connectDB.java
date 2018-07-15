package com.testing.yasir.userinterface;

/**
 * Created by Yasir on 3/29/2018.
 */

public class connectDB {

    String name, password, location;


    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

    public void setLocation(String location){
        this.location= location;
    }
    public String getLocation(){
        return this.location;
    }

    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return this.password;
    }
}
