package com.example.web.app.dao.model;

import java.util.Date;

public class User {
    private Integer id;
    private String name;
    private String secondname;
    private Date birthday;
    private String numberPhone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondname() { return secondname; }

    public void setSecondname(String secondname) { this.secondname = secondname; }

    public Date getBirthday() {
        return birthday;
    }

    public Long getBirthdayTime() {
        return birthday.getTime();
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }
}