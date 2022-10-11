package com.example.food_waste_management;

public class User {public String name, mail_id, pass, mobile,Address;
    public User(){

    }
    public User(String name,String mail_id,String pass,String mobile,String Address){
        this.name = name;
        this.mail_id = mail_id;
        this.pass =pass;
        this.mobile =mobile;
        this.Address =Address;

    }

    public String getUserName(){
        return name;
    }

    public String getUserEmail(){
        return mail_id;
    }
    public String getUserMobile(){return mobile;}
    public String getUserProfile(){return Address;}

}
