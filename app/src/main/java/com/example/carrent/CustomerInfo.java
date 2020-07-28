package com.example.carrent;

public class CustomerInfo {
    private String id;
    private String userName;
    private String name;
    private String phone;
    private String email;
    private String password;

    //Empty constructor
    public CustomerInfo(){}

    public CustomerInfo(String id, String userName, String name, String phone, String email, String password) {
        this.id = id;
        this.userName = userName;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
